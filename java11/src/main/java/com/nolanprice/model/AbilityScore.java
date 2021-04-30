package com.nolanprice.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AbilityScore
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-04-27T17:24:38.963625Z[Etc/UTC]")
public class AbilityScore   {
    @JsonProperty("base")
    private Integer base;

    @JsonProperty("modifier")
    private Integer modifier;

    @JsonProperty("proficient")
    private Boolean proficient;

    public AbilityScore base(Integer base) {
        this.base = base;
        return this;
    }

    /**
     * The base stat
     * @return base
     */
    @ApiModelProperty(value = "The base stat")


    public Integer getBase() {
        return base;
    }

    public void setBase(Integer base) {
        this.base = base;
    }

    public AbilityScore modifier(Integer modifier) {
        this.modifier = modifier;
        return this;
    }

    /**
     * The modifier for the stat
     * @return modifier
     */
    @ApiModelProperty(value = "The modifier for the stat")


    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    public AbilityScore proficient(Boolean proficient) {
        this.proficient = proficient;
        return this;
    }

    /**
     * Whether or not the character is proficient with this ability for saving throws
     * @return proficient
     */
    @ApiModelProperty(value = "Whether or not the character is proficient with this ability for saving throws")


    public Boolean getProficient() {
        return proficient;
    }

    public void setProficient(Boolean proficient) {
        this.proficient = proficient;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbilityScore abilityScore = (AbilityScore) o;
        return Objects.equals(this.base, abilityScore.base) &&
               Objects.equals(this.modifier, abilityScore.modifier) &&
               Objects.equals(this.proficient, abilityScore.proficient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, modifier, proficient);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AbilityScore {\n");

        sb.append("    base: ").append(toIndentedString(base)).append("\n");
        sb.append("    modifier: ").append(toIndentedString(modifier)).append("\n");
        sb.append("    proficient: ").append(toIndentedString(proficient)).append("\n");
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

