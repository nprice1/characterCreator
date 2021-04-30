package com.nolanprice.dnd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Skill {

    private String name;
    private AbilityScore abilityScore;

    public String getName() {
        return name;
    }

    public AbilityScore getAbilityScore() {
        return abilityScore;
    }
}
