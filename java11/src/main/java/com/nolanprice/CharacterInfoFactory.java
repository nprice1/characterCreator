package com.nolanprice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.nolanprice.dnd.Alignment;
import com.nolanprice.dnd.ApiReference;
import com.nolanprice.dnd.Background;
import com.nolanprice.dnd.CharacterClass;
import com.nolanprice.dnd.DndApiClient;
import com.nolanprice.dnd.Ideal;
import com.nolanprice.dnd.Race;
import com.nolanprice.model.AbilityScore;
import com.nolanprice.model.CharacterInfo;
import com.nolanprice.model.Equipment;
import com.nolanprice.model.Feature;
import com.nolanprice.name.NameClient;

@Component
public class CharacterInfoFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(CharacterInfoFactory.class);

    private static final List<Integer> STAT_CHOICES = ImmutableList.of(15, 14, 13, 12, 10, 8);

    private final NameClient nameClient;
    private final DndApiClient dndApiClient;

    @Autowired
    public CharacterInfoFactory(NameClient nameClient, DndApiClient dndApiClient) {
        this.nameClient = nameClient;
        this.dndApiClient = dndApiClient;
    }

    public CharacterInfo createCharacterInfo() {
        // We are going to modify this object
        CharacterInfo characterInfo = new CharacterInfo();
        try {
            // Kick off all requests that don't need any data
            CompletableFuture<Race> raceFuture = dndApiClient.getRace();
            CompletableFuture<CharacterClass> characterClassFuture = dndApiClient.getCharacterClass();
            CompletableFuture<Background> backgroundFuture = dndApiClient.getBackground();
            CompletableFuture<Alignment> alignmentFuture = dndApiClient.getAlignment();

            Race race = raceFuture.get();
            CharacterClass characterClass = characterClassFuture.get();
            Background background = backgroundFuture.get();
            Alignment alignment = alignmentFuture.get();
            String name = nameClient.getCharacterName(race.getName())
                                    .get();

            characterInfo.setName(name);
            characterInfo.setRace(race.getName());
            characterInfo.setBackground(background.getName());
            characterInfo.setPropertyClass(characterClass.getName());
            characterInfo.setAlignment(alignment.getName());

            List<Integer> baseStatAllotments = new ArrayList<>(STAT_CHOICES);
            Collections.shuffle(baseStatAllotments);
            characterInfo.setStrength(generateAbilityScores("STR", baseStatAllotments.get(0), race, characterClass));
            characterInfo.setDexterity(generateAbilityScores("DEX", baseStatAllotments.get(1), race, characterClass));
            characterInfo.setIntelligence(generateAbilityScores("INT", baseStatAllotments.get(2), race, characterClass));
            characterInfo.setWisdom(generateAbilityScores("WIS", baseStatAllotments.get(3), race, characterClass));
            characterInfo.setConstitution(generateAbilityScores("CON", baseStatAllotments.get(4), race, characterClass));
            characterInfo.setCharisma(generateAbilityScores("CHA", baseStatAllotments.get(5), race, characterClass));

            Pair<Set<String>, Set<String>> skillsAndProficiencies = getSkillsAndProficiencies(race, characterClass, background);
            characterInfo.setSkills(new ArrayList<>(skillsAndProficiencies.getFirst()));
            characterInfo.setProficiencies(new ArrayList<>(skillsAndProficiencies.getSecond()));

            characterInfo.setLanguages(new ArrayList<>(getLanguages(race, background)));

            characterInfo.setSpeed(String.format("%s feet", race.getSpeed()));
            characterInfo.setHitDice(characterClass.getHitDie());

            Feature feature = new Feature();
            feature.setDescription(background.getFeature().getDesc());
            feature.setName(background.getFeature().getName());
            characterInfo.setFeature(feature);

            characterInfo.setIdeals(getIdeals(background));
            characterInfo.setTraits(ChoiceUtils.makeRandomChoices(background.getPersonalityTraits()));
            characterInfo.setBonds(ChoiceUtils.makeRandomChoices(background.getBonds()));
            characterInfo.setFlaws(ChoiceUtils.makeRandomChoices(background.getFlaws()));

            // For now just assume level 1
            characterInfo.setProficiencyModifier(2);

            characterInfo.setEquipment(getEquipment(characterClass, background));
        } catch (InterruptedException e) {
            Thread.currentThread()
                  .interrupt();
        } catch (ExecutionException e) {
            LOGGER.error("Failed to get character info", e);
        }
        return characterInfo;
    }

    private AbilityScore generateAbilityScores(String statAbbreviation,
                                               Integer baseStat,
                                               Race race,
                                               CharacterClass characterClass) {
        Integer bonus = race.getAbilityBonuses()
                            .stream()
                            .filter(abilityScore -> abilityScore.getAbilityScore().getName().equals(statAbbreviation))
                            .map(abilityScore -> abilityScore.getBonus())
                            .reduce(0, Integer::sum);
        boolean proficient = characterClass.getSavingThrows()
                                           .stream()
                                           .anyMatch(reference -> reference.getName().equals(statAbbreviation));
        int stat = baseStat + bonus;
        int modifier = (stat - 10) / 2;
        return new AbilityScore().base(stat)
                                 .modifier(modifier)
                                 .proficient(proficient);
    }

    private Pair<Set<String>, Set<String>> getSkillsAndProficiencies(Race race, CharacterClass characterClass, Background background) {
        List<ApiReference> allProficiencies = new ArrayList<>();

        // First add all guaranteed proficiencies for the race, character class, and background
        allProficiencies.addAll(race.getStartingProficiencies());
        allProficiencies.addAll(characterClass.getProficiencies());
        allProficiencies.addAll(background.getStartingProficiencies());

        // Now choose proficiencies for race and character class if available
        if (race.getStartingProficiencyOptions() != null) {
            allProficiencies.addAll(ChoiceUtils.makeRandomChoices(race.getStartingProficiencyOptions()));
        }

        if (characterClass.getProficiencyChoices() != null && !characterClass.getProficiencyChoices().isEmpty()) {
            for (Choice<ApiReference> choice : characterClass.getProficiencyChoices()) {
                allProficiencies.addAll(ChoiceUtils.makeRandomChoices(choice));
            }
        }

        // Generate separate lists of skill proficiencies and other proficiencies since the API doesnt differentiate
        Set<String> skills = new HashSet<>();
        Set<String> proficiencies = new HashSet<>();
        for (ApiReference reference : allProficiencies) {
            if (reference.getIndex().startsWith("skill-")) {
                skills.add(reference.getName());
            } else {
                proficiencies.add(reference.getName());
            }
        }
        return Pair.of(skills, proficiencies);
    }

    private Set<String> getLanguages(Race race, Background background) {
        List<ApiReference> allLanguages = new ArrayList<>();
        allLanguages.addAll(race.getLanguages());
        if (background.getLanguages() != null) {
            allLanguages.addAll(background.getLanguages());
        }
        if (background.getLanguageOptions() != null) {
            allLanguages.addAll(ChoiceUtils.makeRandomChoices(background.getLanguageOptions()));
        }
        return allLanguages.stream()
                           .map(ApiReference::getName)
                           .collect(Collectors.toSet());
    }

    private List<Equipment> getEquipment(CharacterClass characterClass, Background background) throws
                                                                                               ExecutionException,
                                                                                               InterruptedException {
        Set<com.nolanprice.dnd.Equipment> allEquipment = new HashSet<>();
        allEquipment.addAll(characterClass.getStartingEquipment());
        allEquipment.addAll(background.getStartingEquipment());
        for (Choice<com.nolanprice.dnd.Equipment> choice : characterClass.getStartingEquipmentOptions()) {
            allEquipment.addAll(makeRandomEquipmentChoices(choice));
        }
        for (Choice<com.nolanprice.dnd.Equipment> choice : background.getStartingEquipmentOptions()) {
            allEquipment.addAll(makeRandomEquipmentChoices(choice));
        }
        return allEquipment.stream()
                           .map(this::mapEquipmentFromExternalModel)
                           .filter(Objects::nonNull)
                           .collect(Collectors.toList());
    }

    private Equipment mapEquipmentFromExternalModel(com.nolanprice.dnd.Equipment dndEquipment) {
        if (dndEquipment.getEquipment() == null) {
            return null;
        }
        return new Equipment().name(dndEquipment.getEquipment()
                                                .getName())
                              .quantity(dndEquipment.getQuantity());
    }

    private List<String> getIdeals(Background background) {
        return ChoiceUtils.makeRandomChoices(background.getIdeals())
                          .stream()
                          .map(Ideal::getDesc)
                          .collect(Collectors.toList());
    }

    private List<com.nolanprice.dnd.Equipment> makeRandomEquipmentChoices(Choice<com.nolanprice.dnd.Equipment> choice) throws
                                                                                                                       ExecutionException,
                                                                                                                       InterruptedException {
        List<com.nolanprice.dnd.Equipment> chosenEquipment = dndApiClient.expandEquipmentChoices(choice)
                                                                         .thenApply(ChoiceUtils::makeRandomChoices)
                                                                         .get();
        List<com.nolanprice.dnd.Equipment> finalEquipment = new ArrayList<>();
        List<CompletableFuture<Void>> expandedChoicesFutures = new ArrayList<>();
        for (com.nolanprice.dnd.Equipment equipment : chosenEquipment) {
            if (equipment.getEquipmentOption() != null) {
                expandedChoicesFutures.add(dndApiClient.expandEquipmentChoices(equipment.getEquipmentOption())
                                                       .thenApply(ChoiceUtils::makeRandomChoices)
                                                       .thenAccept(finalEquipment::addAll));
            } else {
                finalEquipment.add(equipment);
            }
        }
        // Wait for the expanded entries to finish
        CompletableFuture.allOf(expandedChoicesFutures.toArray(new CompletableFuture[] {}))
                         .get();
        return finalEquipment;
    }

}