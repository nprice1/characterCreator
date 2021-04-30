package com.nolanprice.dnd;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Feature {

    private List<String> desc;
    private String name;

    public List<String> getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }
}
