package com.nolanprice.dnd;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EquipmentList {

    private List<ApiReference> equipment;

    public List<ApiReference> getEquipment() {
        return equipment;
    }
}
