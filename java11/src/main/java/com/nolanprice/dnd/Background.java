package com.nolanprice.dnd;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nolanprice.Choice;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Background {

    private String name;
    @JsonProperty("starting_proficiencies")
    private List<ApiReference> starting_proficiencies;
    @JsonProperty("language_options")
    private Choice<ApiReference> language_options;
    private List<ApiReference> languages;
    @JsonProperty("starting_equipment")
    private List<Equipment> starting_equipment;
    @JsonProperty("starting_equipment_options")
    private List<Choice<Equipment>> starting_equipment_options;
    @JsonProperty("personality_traits")
    private Choice<String> personality_traits;
    private Choice<Ideal> ideals;
    private Choice<String> flaws;
    private Choice<String> bonds;
    private Feature feature;

    public String getName() {
        return name;
    }

    public List<ApiReference> getStartingProficiencies() {
        return starting_proficiencies;
    }

    public List<ApiReference> getLanguages() {
        return languages;
    }

    public Choice<ApiReference> getLanguageOptions() {
        return language_options;
    }

    public List<Equipment> getStartingEquipment() {
        return starting_equipment;
    }

    public List<Choice<Equipment>> getStartingEquipmentOptions() {
        return starting_equipment_options;
    }

    public Choice<String> getPersonalityTraits() {
        return personality_traits;
    }

    public Choice<Ideal> getIdeals() {
        return ideals;
    }

    public Choice<String> getFlaws() {
        return flaws;
    }

    public Choice<String> getBonds() {
        return bonds;
    }

    public Feature getFeature() {
        return feature;
    }
}
