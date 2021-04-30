package com.nolanprice;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Choice<T> {

    private Integer choose;
    private String type;
    private List<T> from;

    public Choice() {
    }

    public Choice(Integer choose, String type, List<T> from) {
        this.choose = choose;
        this.type = type;
        this.from = from;
    }

    public Integer getChoose() {
        return choose;
    }

    public String getType() {
        return type;
    }

    public List<T> getFrom() {
        return from;
    }

}
