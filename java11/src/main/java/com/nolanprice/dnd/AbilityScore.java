package com.nolanprice.dnd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AbilityScore {

    private Integer bonus;
    @JsonProperty("ability_score")
    private ApiReference ability_score;

    public Integer getBonus() {
        return bonus;
    }

    public ApiReference getAbilityScore() {
        return ability_score;
    }
}
