package com.nolanprice.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.nolanprice.model.AbilityScore;
import com.nolanprice.model.Equipment;
import com.nolanprice.model.Feature;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * The general details of a character
 */
@ApiModel(description = "The general details of a character")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-04-27T18:37:25.690352Z[Etc/UTC]")
public class CharacterInfo   {
    @JsonProperty("name")
    private String name;

    @JsonProperty("race")
    private String race;

    @JsonProperty("class")
    private String propertyClass;

    @JsonProperty("background")
    private String background;

    @JsonProperty("alignment")
    private String alignment;

    @JsonProperty("strength")
    private AbilityScore strength;

    @JsonProperty("dexterity")
    private AbilityScore dexterity;

    @JsonProperty("intelligence")
    private AbilityScore intelligence;

    @JsonProperty("wisdom")
    private AbilityScore wisdom;

    @JsonProperty("constitution")
    private AbilityScore constitution;

    @JsonProperty("charisma")
    private AbilityScore charisma;

    @JsonProperty("proficiencyModifier")
    private Integer proficiencyModifier;

    @JsonProperty("skills")
    @Valid
    private List<String> skills = null;

    @JsonProperty("proficiencies")
    @Valid
    private List<String> proficiencies = null;

    @JsonProperty("languages")
    @Valid
    private List<String> languages = null;

    @JsonProperty("equipment")
    @Valid
    private List<Equipment> equipment = null;

    @JsonProperty("speed")
    private String speed;

    @JsonProperty("hitDice")
    private Integer hitDice;

    @JsonProperty("feature")
    private Feature feature;

    @JsonProperty("traits")
    @Valid
    private List<String> traits = null;

    @JsonProperty("ideals")
    @Valid
    private List<String> ideals = null;

    @JsonProperty("bonds")
    @Valid
    private List<String> bonds = null;

    @JsonProperty("flaws")
    @Valid
    private List<String> flaws = null;

    public CharacterInfo name(String name) {
        this.name = name;
        return this;
    }

    /**
     * The name of the character
     * @return name
     */
    @ApiModelProperty(value = "The name of the character")


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CharacterInfo race(String race) {
        this.race = race;
        return this;
    }

    /**
     * The race of the character
     * @return race
     */
    @ApiModelProperty(value = "The race of the character")


    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public CharacterInfo propertyClass(String propertyClass) {
        this.propertyClass = propertyClass;
        return this;
    }

    /**
     * The class for the character
     * @return propertyClass
     */
    @ApiModelProperty(value = "The class for the character")


    public String getPropertyClass() {
        return propertyClass;
    }

    public void setPropertyClass(String propertyClass) {
        this.propertyClass = propertyClass;
    }

    public CharacterInfo background(String background) {
        this.background = background;
        return this;
    }

    /**
     * The name of the background for the character
     * @return background
     */
    @ApiModelProperty(value = "The name of the background for the character")


    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public CharacterInfo alignment(String alignment) {
        this.alignment = alignment;
        return this;
    }

    /**
     * The alignment for the character
     * @return alignment
     */
    @ApiModelProperty(value = "The alignment for the character")


    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public CharacterInfo strength(AbilityScore strength) {
        this.strength = strength;
        return this;
    }

    /**
     * Get strength
     * @return strength
     */
    @ApiModelProperty(value = "")

    @Valid

    public AbilityScore getStrength() {
        return strength;
    }

    public void setStrength(AbilityScore strength) {
        this.strength = strength;
    }

    public CharacterInfo dexterity(AbilityScore dexterity) {
        this.dexterity = dexterity;
        return this;
    }

    /**
     * Get dexterity
     * @return dexterity
     */
    @ApiModelProperty(value = "")

    @Valid

    public AbilityScore getDexterity() {
        return dexterity;
    }

    public void setDexterity(AbilityScore dexterity) {
        this.dexterity = dexterity;
    }

    public CharacterInfo intelligence(AbilityScore intelligence) {
        this.intelligence = intelligence;
        return this;
    }

    /**
     * Get intelligence
     * @return intelligence
     */
    @ApiModelProperty(value = "")

    @Valid

    public AbilityScore getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(AbilityScore intelligence) {
        this.intelligence = intelligence;
    }

    public CharacterInfo wisdom(AbilityScore wisdom) {
        this.wisdom = wisdom;
        return this;
    }

    /**
     * Get wisdom
     * @return wisdom
     */
    @ApiModelProperty(value = "")

    @Valid

    public AbilityScore getWisdom() {
        return wisdom;
    }

    public void setWisdom(AbilityScore wisdom) {
        this.wisdom = wisdom;
    }

    public CharacterInfo constitution(AbilityScore constitution) {
        this.constitution = constitution;
        return this;
    }

    /**
     * Get constitution
     * @return constitution
     */
    @ApiModelProperty(value = "")

    @Valid

    public AbilityScore getConstitution() {
        return constitution;
    }

    public void setConstitution(AbilityScore constitution) {
        this.constitution = constitution;
    }

    public CharacterInfo charisma(AbilityScore charisma) {
        this.charisma = charisma;
        return this;
    }

    /**
     * Get charisma
     * @return charisma
     */
    @ApiModelProperty(value = "")

    @Valid

    public AbilityScore getCharisma() {
        return charisma;
    }

    public void setCharisma(AbilityScore charisma) {
        this.charisma = charisma;
    }

    public CharacterInfo proficiencyModifier(Integer proficiencyModifier) {
        this.proficiencyModifier = proficiencyModifier;
        return this;
    }

    /**
     * The proficiency modifier for the character
     * @return proficiencyModifier
     */
    @ApiModelProperty(value = "The proficiency modifier for the character")


    public Integer getProficiencyModifier() {
        return proficiencyModifier;
    }

    public void setProficiencyModifier(Integer proficiencyModifier) {
        this.proficiencyModifier = proficiencyModifier;
    }

    public CharacterInfo skills(List<String> skills) {
        this.skills = skills;
        return this;
    }

    public CharacterInfo addSkillsItem(String skillsItem) {
        if (this.skills == null) {
            this.skills = new ArrayList<>();
        }
        this.skills.add(skillsItem);
        return this;
    }

    /**
     * Get skills
     * @return skills
     */
    @ApiModelProperty(value = "")


    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public CharacterInfo proficiencies(List<String> proficiencies) {
        this.proficiencies = proficiencies;
        return this;
    }

    public CharacterInfo addProficienciesItem(String proficienciesItem) {
        if (this.proficiencies == null) {
            this.proficiencies = new ArrayList<>();
        }
        this.proficiencies.add(proficienciesItem);
        return this;
    }

    /**
     * Get proficiencies
     * @return proficiencies
     */
    @ApiModelProperty(value = "")


    public List<String> getProficiencies() {
        return proficiencies;
    }

    public void setProficiencies(List<String> proficiencies) {
        this.proficiencies = proficiencies;
    }

    public CharacterInfo languages(List<String> languages) {
        this.languages = languages;
        return this;
    }

    public CharacterInfo addLanguagesItem(String languagesItem) {
        if (this.languages == null) {
            this.languages = new ArrayList<>();
        }
        this.languages.add(languagesItem);
        return this;
    }

    /**
     * Get languages
     * @return languages
     */
    @ApiModelProperty(value = "")


    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public CharacterInfo equipment(List<Equipment> equipment) {
        this.equipment = equipment;
        return this;
    }

    public CharacterInfo addEquipmentItem(Equipment equipmentItem) {
        if (this.equipment == null) {
            this.equipment = new ArrayList<>();
        }
        this.equipment.add(equipmentItem);
        return this;
    }

    /**
     * Get equipment
     * @return equipment
     */
    @ApiModelProperty(value = "")

    @Valid

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    public CharacterInfo speed(String speed) {
        this.speed = speed;
        return this;
    }

    /**
     * The movement speed for the character
     * @return speed
     */
    @ApiModelProperty(value = "The movement speed for the character")


    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public CharacterInfo hitDice(Integer hitDice) {
        this.hitDice = hitDice;
        return this;
    }

    /**
     * The hit dice for the character
     * @return hitDice
     */
    @ApiModelProperty(value = "The hit dice for the character")


    public Integer getHitDice() {
        return hitDice;
    }

    public void setHitDice(Integer hitDice) {
        this.hitDice = hitDice;
    }

    public CharacterInfo feature(Feature feature) {
        this.feature = feature;
        return this;
    }

    /**
     * Get feature
     * @return feature
     */
    @ApiModelProperty(value = "")

    @Valid

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public CharacterInfo traits(List<String> traits) {
        this.traits = traits;
        return this;
    }

    public CharacterInfo addTraitsItem(String traitsItem) {
        if (this.traits == null) {
            this.traits = new ArrayList<>();
        }
        this.traits.add(traitsItem);
        return this;
    }

    /**
     * Get traits
     * @return traits
     */
    @ApiModelProperty(value = "")


    public List<String> getTraits() {
        return traits;
    }

    public void setTraits(List<String> traits) {
        this.traits = traits;
    }

    public CharacterInfo ideals(List<String> ideals) {
        this.ideals = ideals;
        return this;
    }

    public CharacterInfo addIdealsItem(String idealsItem) {
        if (this.ideals == null) {
            this.ideals = new ArrayList<>();
        }
        this.ideals.add(idealsItem);
        return this;
    }

    /**
     * Get ideals
     * @return ideals
     */
    @ApiModelProperty(value = "")


    public List<String> getIdeals() {
        return ideals;
    }

    public void setIdeals(List<String> ideals) {
        this.ideals = ideals;
    }

    public CharacterInfo bonds(List<String> bonds) {
        this.bonds = bonds;
        return this;
    }

    public CharacterInfo addBondsItem(String bondsItem) {
        if (this.bonds == null) {
            this.bonds = new ArrayList<>();
        }
        this.bonds.add(bondsItem);
        return this;
    }

    /**
     * Get bonds
     * @return bonds
     */
    @ApiModelProperty(value = "")


    public List<String> getBonds() {
        return bonds;
    }

    public void setBonds(List<String> bonds) {
        this.bonds = bonds;
    }

    public CharacterInfo flaws(List<String> flaws) {
        this.flaws = flaws;
        return this;
    }

    public CharacterInfo addFlawsItem(String flawsItem) {
        if (this.flaws == null) {
            this.flaws = new ArrayList<>();
        }
        this.flaws.add(flawsItem);
        return this;
    }

    /**
     * Get flaws
     * @return flaws
     */
    @ApiModelProperty(value = "")


    public List<String> getFlaws() {
        return flaws;
    }

    public void setFlaws(List<String> flaws) {
        this.flaws = flaws;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CharacterInfo characterInfo = (CharacterInfo) o;
        return Objects.equals(this.name, characterInfo.name) &&
               Objects.equals(this.race, characterInfo.race) &&
               Objects.equals(this.propertyClass, characterInfo.propertyClass) &&
               Objects.equals(this.background, characterInfo.background) &&
               Objects.equals(this.alignment, characterInfo.alignment) &&
               Objects.equals(this.strength, characterInfo.strength) &&
               Objects.equals(this.dexterity, characterInfo.dexterity) &&
               Objects.equals(this.intelligence, characterInfo.intelligence) &&
               Objects.equals(this.wisdom, characterInfo.wisdom) &&
               Objects.equals(this.constitution, characterInfo.constitution) &&
               Objects.equals(this.charisma, characterInfo.charisma) &&
               Objects.equals(this.proficiencyModifier, characterInfo.proficiencyModifier) &&
               Objects.equals(this.skills, characterInfo.skills) &&
               Objects.equals(this.proficiencies, characterInfo.proficiencies) &&
               Objects.equals(this.languages, characterInfo.languages) &&
               Objects.equals(this.equipment, characterInfo.equipment) &&
               Objects.equals(this.speed, characterInfo.speed) &&
               Objects.equals(this.hitDice, characterInfo.hitDice) &&
               Objects.equals(this.feature, characterInfo.feature) &&
               Objects.equals(this.traits, characterInfo.traits) &&
               Objects.equals(this.ideals, characterInfo.ideals) &&
               Objects.equals(this.bonds, characterInfo.bonds) &&
               Objects.equals(this.flaws, characterInfo.flaws);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, race, propertyClass, background, alignment, strength, dexterity, intelligence, wisdom, constitution, charisma, proficiencyModifier, skills, proficiencies, languages, equipment, speed, hitDice, feature, traits, ideals, bonds, flaws);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CharacterInfo {\n");

        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    race: ").append(toIndentedString(race)).append("\n");
        sb.append("    propertyClass: ").append(toIndentedString(propertyClass)).append("\n");
        sb.append("    background: ").append(toIndentedString(background)).append("\n");
        sb.append("    alignment: ").append(toIndentedString(alignment)).append("\n");
        sb.append("    strength: ").append(toIndentedString(strength)).append("\n");
        sb.append("    dexterity: ").append(toIndentedString(dexterity)).append("\n");
        sb.append("    intelligence: ").append(toIndentedString(intelligence)).append("\n");
        sb.append("    wisdom: ").append(toIndentedString(wisdom)).append("\n");
        sb.append("    constitution: ").append(toIndentedString(constitution)).append("\n");
        sb.append("    charisma: ").append(toIndentedString(charisma)).append("\n");
        sb.append("    proficiencyModifier: ").append(toIndentedString(proficiencyModifier)).append("\n");
        sb.append("    skills: ").append(toIndentedString(skills)).append("\n");
        sb.append("    proficiencies: ").append(toIndentedString(proficiencies)).append("\n");
        sb.append("    languages: ").append(toIndentedString(languages)).append("\n");
        sb.append("    equipment: ").append(toIndentedString(equipment)).append("\n");
        sb.append("    speed: ").append(toIndentedString(speed)).append("\n");
        sb.append("    hitDice: ").append(toIndentedString(hitDice)).append("\n");
        sb.append("    feature: ").append(toIndentedString(feature)).append("\n");
        sb.append("    traits: ").append(toIndentedString(traits)).append("\n");
        sb.append("    ideals: ").append(toIndentedString(ideals)).append("\n");
        sb.append("    bonds: ").append(toIndentedString(bonds)).append("\n");
        sb.append("    flaws: ").append(toIndentedString(flaws)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

