package com.nolanprice.graphql;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

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

import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class CharacterInfoResolver implements GraphQLResolver<CharacterInfo> {

    private final CharacterInfoFactory characterInfoFactory;

    @Autowired
    public CharacterInfoResolver(CharacterInfoFactory characterInfoFactory) {
        this.characterInfoFactory = characterInfoFactory;
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
                                .thenApply(unused -> {
                                    try {
                                        return characterInfoFactory.getLanguages(raceFuture.get(), backgroundFuture.get());
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                });
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
                                .thenApply(unused -> {
                                    try {
                                        return characterInfoFactory.getSkillsAndProficiencies(raceFuture.get(),
                                                                                              classFuture.get(),
                                                                                              backgroundFuture.get())
                                                                   .getFirst();
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                });
    }

    public CompletableFuture<Set<String>> getProficiencies(CharacterInfo characterInfo, DataFetchingEnvironment dataFetchingEnvironment) {
        CompletableFuture<Race> raceFuture = loadRace(dataFetchingEnvironment);
        CompletableFuture<CharacterClass> classFuture = loadCharacterClass(dataFetchingEnvironment);
        CompletableFuture<Background> backgroundFuture = loadBackground(dataFetchingEnvironment);
        return CompletableFuture.allOf(raceFuture,
                                       classFuture,
                                       backgroundFuture)
                                .thenApply(unused -> {
                                    try {
                                        return characterInfoFactory.getSkillsAndProficiencies(raceFuture.get(),
                                                                                              classFuture.get(),
                                                                                              backgroundFuture.get())
                                                                   .getSecond();
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                });
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
        CompletableFuture<Background> backgroundFuture = loadBackground(dataFetchingEnvironment);
        CompletableFuture<CharacterClass> classFuture = loadCharacterClass(dataFetchingEnvironment);
        return CompletableFuture.allOf(backgroundFuture,
                                       classFuture)
                                .thenCompose(unused -> {
                                    try {
                                        return characterInfoFactory.getEquipment(classFuture.get(), backgroundFuture.get());
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                });
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

    private <T> CompletableFuture<T> getTypesafeLoader(DataFetchingEnvironment dataFetchingEnvironment,
                                                       String loaderName) {
        DataLoader<String, T> dataLoader = dataFetchingEnvironment.getDataLoader(loaderName);
        return dataLoader.load(dataFetchingEnvironment.getExecutionId().toString());
    }

}
