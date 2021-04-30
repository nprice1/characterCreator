package com.nolanprice.dnd;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceList {

    private List<ApiReference> results;
    private Integer count;

    public List<ApiReference> getResults() {
        return results;
    }

    public Integer getCount() {
        return count;
    }
}
