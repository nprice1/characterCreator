package com.nolanprice.dnd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Alignment {

    private String name;

    public String getName() {
        return name;
    }
}
