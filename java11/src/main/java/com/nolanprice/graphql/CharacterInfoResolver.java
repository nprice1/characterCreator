package com.nolanprice.graphql;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nolanprice.CharacterInfoFactory;
import com.nolanprice.ChoiceUtils;
import com.nolanprice.dnd.Alignment;
import com.nolanprice.dnd.Background;
import com.nolanprice.dnd.CharacterClass;
import com.nolanprice.dnd.Race;
import com.nolanprice.model.AbilityScore;
import com.nolanprice.model.CharacterInfo;
import com.nolanprice.model.Equipment;
import com.nolanprice.model.Feature;
import com.nolanprice.sprite.SpriteBuilder;

import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class CharacterInfoResolver implements GraphQLResolver<CharacterInfo> {

    private final CharacterInfoFactory characterInfoFactory;
    private final SpriteBuilder spriteBuilder;

    @Autowired
    public CharacterInfoResolver(CharacterInfoFactory characterInfoFactory, SpriteBuilder spriteBuilder) {
        this.characterInfoFactory = characterInfoFactory;
        this.spriteBuilder = spriteBuilder;
    }

    public CompletableFuture<String> getRace(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        return loadRace(dataFetchingEnvironment).thenApply(Race::getName);
    }

    public CompletableFuture<String> getName(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        return loadRace(dataFetchingEnvironment).thenCompose(characterInfoFactory::getName);
    }

    public CompletableFuture<String> getCharacterClass(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        return loadCharacterClass(dataFetchingEnvironment).thenApply(CharacterClass::getName);
    }

    public CompletableFuture<String> getBackground(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        return loadBackground(dataFetchingEnvironment).thenApply(Background::getName);
    }

    public CompletableFuture<String> getAlignment(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        return loadAlignment(dataFetchingEnvironment).thenApply(Alignment::getName);
    }

    public CompletableFuture<String> getSpeed(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        return loadRace(dataFetchingEnvironment).thenApply(characterInfoFactory::getSpeed);
    }

    public CompletableFuture<Integer> getHitDice(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        return loadCharacterClass(dataFetchingEnvironment).thenApply(characterInfoFactory::getHitDice);
    }

    public CompletableFuture<Set<String>> getLanguages(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        CompletableFuture<Race> raceFuture = loadRace(dataFetchingEnvironment);
        CompletableFuture<Background> backgroundFuture = loadBackground(dataFetchingEnvironment);
        return CompletableFuture.allOf(raceFuture,
                                       backgroundFuture)
                                .thenApply(unused -> characterInfoFactory.getLanguages(raceFuture.join(), backgroundFuture.join()));
    }

    public CompletableFuture<Feature> getFeature(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        return loadBackground(dataFetchingEnvironment).thenApply(characterInfoFactory::getFeature);
    }

    public CompletableFuture<List<String>> getIdeals(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        return loadBackground(dataFetchingEnvironment).thenApply(characterInfoFactory::getIdeals);
    }

    public CompletableFuture<List<String>> getTraits(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        return loadBackground(dataFetchingEnvironment).thenApply(background -> ChoiceUtils.makeRandomChoices(background.getPersonalityTraits()));
    }

    public CompletableFuture<List<String>> getBonds(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        return loadBackground(dataFetchingEnvironment).thenApply(background -> ChoiceUtils.makeRandomChoices(background.getBonds()));
    }

    public CompletableFuture<List<String>> getFlaws(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        return loadBackground(dataFetchingEnvironment).thenApply(background -> ChoiceUtils.makeRandomChoices(background.getFlaws()));
    }

    public CompletableFuture<Set<String>> getSkills(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        CompletableFuture<Race> raceFuture = loadRace(dataFetchingEnvironment);
        CompletableFuture<CharacterClass> classFuture = loadCharacterClass(dataFetchingEnvironment);
        CompletableFuture<Background> backgroundFuture = loadBackground(dataFetchingEnvironment);
        return CompletableFuture.allOf(raceFuture,
                                       classFuture,
                                       backgroundFuture)
                                .thenApply(unused -> characterInfoFactory.getSkillsAndProficiencies(raceFuture.join(),
                                                                                              classFuture.join(),
                                                                                              backgroundFuture.join())
                                                                         .getFirst());
    }

    public CompletableFuture<Set<String>> getProficiencies(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        CompletableFuture<Race> raceFuture = loadRace(dataFetchingEnvironment);
        CompletableFuture<CharacterClass> classFuture = loadCharacterClass(dataFetchingEnvironment);
        CompletableFuture<Background> backgroundFuture = loadBackground(dataFetchingEnvironment);
        return CompletableFuture.allOf(raceFuture,
                                       classFuture,
                                       backgroundFuture)
                                .thenApply(unused -> characterInfoFactory.getSkillsAndProficiencies(raceFuture.join(),
                                                                                              classFuture.join(),
                                                                                              backgroundFuture.join())
                                                                         .getSecond());
    }

    public CompletableFuture<AbilityScore> getStrength(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        return getAbilityScore(dataFetchingEnvironment, "STR", 0);
    }

    public CompletableFuture<AbilityScore> getDexterity(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        return getAbilityScore(dataFetchingEnvironment, "DEX", 1);
    }

    public CompletableFuture<AbilityScore> getIntelligence(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        return getAbilityScore(dataFetchingEnvironment, "INT", 2);
    }

    public CompletableFuture<AbilityScore> getWisdom(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        return getAbilityScore(dataFetchingEnvironment, "WIS", 3);
    }

    public CompletableFuture<AbilityScore> getConstitution(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        return getAbilityScore(dataFetchingEnvironment, "CON", 4);
    }

    public CompletableFuture<AbilityScore> getCharisma(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        return getAbilityScore(dataFetchingEnvironment, "CHA", 5);
    }

    public CompletableFuture<List<Equipment>> getEquipment(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        return loadEquipment(dataFetchingEnvironment);
    }

    public CompletableFuture<String> getSpriteSheet(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        CompletableFuture<Race> raceFuture = loadRace(dataFetchingEnvironment);
        CompletableFuture<List<Equipment>> equipmentFuture = loadEquipment(dataFetchingEnvironment);
        return CompletableFuture.allOf(raceFuture,
                                       equipmentFuture)
                                .thenApply(unused -> {
                                    try {
                                        File spriteFile =  spriteBuilder.buildSpriteSheet(equipmentFuture.join()
                                                                                                         .stream()
                                                                                                         .map(Equipment::getName)
                                                                                                         .collect(Collectors.toList()),
                                                                                          raceFuture.join()
                                                                                                    .getName());
                                        return "/rest/character-builder/v1/sprite/" + spriteFile.getName();
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                });
    }

    public CompletableFuture<Integer> getProficiencyModifier(CharacterInfo characterInfo) {
        return CompletableFuture.completedFuture(2);
    }

    private CompletableFuture<AbilityScore> getAbilityScore(DataFetchingEnvironment dataFetchingEnvironment,
                                                            String statAbbreviation,
                                                            int statIndex) {
        CompletableFuture<Race> raceFuture = loadRace(dataFetchingEnvironment);
        CompletableFuture<CharacterClass> classFuture = loadCharacterClass(dataFetchingEnvironment);
        CompletableFuture<List<Integer>> statRollFuture = loadStatAllotments(dataFetchingEnvironment);
        return CompletableFuture.allOf(raceFuture,
                                       classFuture,
                                       statRollFuture)
                                .thenApply(unused -> {
                                    try {
                                        return characterInfoFactory.generateAbilityScores(statAbbreviation,
                                                                                          statRollFuture.get()
                                                                                                        .get(statIndex),
                                                                                          raceFuture.get(),
                                                                                          classFuture.get());
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                });
    }

    private CompletableFuture<Race> loadRace(DataFetchingEnvironment dataFetchingEnvironment) {
        return getTypesafeLoader(dataFetchingEnvironment, GraphQLContextBuilder.DataLoaders.RACE);
    }

    private CompletableFuture<CharacterClass> loadCharacterClass(DataFetchingEnvironment dataFetchingEnvironment) {
        return getTypesafeLoader(dataFetchingEnvironment, GraphQLContextBuilder.DataLoaders.CHARACTER_CLASS);
    }

    private CompletableFuture<Background> loadBackground(DataFetchingEnvironment dataFetchingEnvironment) {
        return getTypesafeLoader(dataFetchingEnvironment, GraphQLContextBuilder.DataLoaders.BACKGROUND);
    }

    private CompletableFuture<Alignment> loadAlignment(DataFetchingEnvironment dataFetchingEnvironment) {
        return getTypesafeLoader(dataFetchingEnvironment, GraphQLContextBuilder.DataLoaders.ALIGNMENT);
    }

    private CompletableFuture<List<Integer>> loadStatAllotments(DataFetchingEnvironment dataFetchingEnvironment) {
        return getTypesafeLoader(dataFetchingEnvironment, GraphQLContextBuilder.DataLoaders.STAT_ALLOTMENTS);
    }

    private CompletableFuture<List<Equipment>> loadEquipment(DataFetchingEnvironment dataFetchingEnvironment) {
        DataLoader<Pair<CompletableFuture<CharacterClass>, CompletableFuture<Background>>, List<Equipment>> dataLoader = dataFetchingEnvironment.getDataLoader(GraphQLContextBuilder.DataLoaders.EQUIPMENT);
        return dataLoader.load(Pair.of(loadCharacterClass(dataFetchingEnvironment),
                                       loadBackground(dataFetchingEnvironment)));
    }

    private <T> CompletableFuture<T> getTypesafeLoader(DataFetchingEnvironment dataFetchingEnvironment,
                                                       String loaderName) {
        DataLoader<String, T> dataLoader = dataFetchingEnvironment.getDataLoader(loaderName);
        return dataLoader.load(dataFetchingEnvironment.getExecutionId().toString());
    }

}
