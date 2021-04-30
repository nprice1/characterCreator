package com.nolanprice.dnd;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ideal {

    private String desc;
    private List<ApiReference> alignments;

    public String getDesc() {
        return desc;
    }

    public List<ApiReference> getAlignments() {
        return alignments;
    }
}
