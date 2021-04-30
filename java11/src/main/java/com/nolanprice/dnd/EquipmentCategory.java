package com.nolanprice.dnd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EquipmentCategory {

    @JsonProperty("equipment_category")
    private ApiReference equipment_category;

    public ApiReference getEquipmentCategory() {
        return equipment_category;
    }
}
