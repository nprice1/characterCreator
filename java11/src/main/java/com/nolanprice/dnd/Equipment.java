package com.nolanprice.dnd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Equipment {

    private ApiReference equipment;
    private Integer quantity;
    @JsonProperty("equipment_option")
    private EquipmentOption equipment_option;
    @JsonProperty("equipment_category")
    private ApiReference equipment_category;

    public Equipment() {
    }

    public Equipment(ApiReference equipment, Integer quantity) {
        this.equipment = equipment;
        this.quantity = quantity;
    }

    public ApiReference getEquipment() {
        return equipment;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public EquipmentOption getEquipmentOption() {
        return equipment_option;
    }

    public ApiReference getEquipmentCategory() {
        return equipment_category;
    }
}
