package com.nolanprice.dnd;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nolanprice.Choice;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CharacterClass {

    private String name;
    @JsonProperty("hit_die")
    private Integer hit_die;
    @JsonProperty("proficiency_choices")
    private List<Choice<ApiReference>> proficiency_choices;
    private List<ApiReference> proficiencies;
    @JsonProperty("saving_throws")
    private List<ApiReference> saving_throws;
    @JsonProperty("starting_equipment")
    private List<Equipment> starting_equipment;
    @JsonProperty("starting_equipment_options")
    private List<Choice<Equipment>> starting_equipment_options;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHitDie() {
        return hit_die;
    }

    public List<Choice<ApiReference>> getProficiencyChoices() {
        return proficiency_choices;
    }

    public List<ApiReference> getProficiencies() {
        return proficiencies;
    }

    public List<ApiReference> getSavingThrows() {
        return saving_throws;
    }

    public List<Equipment> getStartingEquipment() {
        return starting_equipment;
    }

    public List<Choice<Equipment>> getStartingEquipmentOptions() {
        return starting_equipment_options;
    }
}
