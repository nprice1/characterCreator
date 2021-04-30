package com.nolanprice.sprite;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nolanprice.Choice;

public class AllowedPaths {

    public enum InfoAttribute {
        @JsonProperty("equipment")
        EQUIPMENT,
        @JsonProperty("race")
        RACE,
        @JsonProperty("gender")
        GENDER;
    }

    private List<Map<InfoAttribute, List<String>>> conditions;
    private List<Choice<String>> choices;

    public List<Map<InfoAttribute, List<String>>> getConditions() {
        return conditions;
    }

    public List<Choice<String>> getChoices() {
        return choices;
    }
}
