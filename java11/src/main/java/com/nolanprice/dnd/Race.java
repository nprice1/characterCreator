package com.nolanprice.dnd;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nolanprice.Choice;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Race {

    private String name;
    private Integer speed;
    @JsonProperty("ability_bonuses")
    private List<AbilityScore> ability_bonuses;
    private String alignment;
    @JsonProperty("starting_proficiencies")
    private List<ApiReference> starting_proficiencies;
    @JsonProperty("starting_proficiency_options")
    private Choice<ApiReference> starting_proficiency_options;
    private List<ApiReference> languages;
    private List<ApiReference> traits;

    public String getName() {
        return name;
    }

    public Integer getSpeed() {
        return speed;
    }

    public List<AbilityScore> getAbilityBonuses() {
        return ability_bonuses;
    }

    public String getAlignment() {
        return alignment;
    }

    public List<ApiReference> getStartingProficiencies() {
        return starting_proficiencies;
    }

    public Choice<ApiReference> getStartingProficiencyOptions() {
        return starting_proficiency_options;
    }

    public List<ApiReference> getLanguages() {
        return languages;
    }

    public List<ApiReference> getTraits() {
        return traits;
    }
}
