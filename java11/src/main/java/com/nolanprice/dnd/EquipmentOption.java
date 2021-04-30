package com.nolanprice.dnd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EquipmentOption {

    private Integer choose;
    private EquipmentCategory from;

    public Integer getChoose() {
        return choose;
    }

    public EquipmentCategory getFrom() {
        return from;
    }
}
