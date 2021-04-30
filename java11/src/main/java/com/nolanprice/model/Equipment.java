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
 * Piece of equipment
 */
@ApiModel(description = "Piece of equipment")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-04-26T23:22:32.273266Z[Etc/UTC]")
public class Equipment   {
    @JsonProperty("name")
    private String name;

    @JsonProperty("quantity")
    private Integer quantity;

    public Equipment name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Name of equipment
     * @return name
     */
    @ApiModelProperty(value = "Name of equipment")


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Equipment quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     * The number of the given piece of equipment available
     * @return quantity
     */
    @ApiModelProperty(value = "The number of the given piece of equipment available")


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Equipment equipment = (Equipment) o;
        return Objects.equals(this.name, equipment.name) &&
               Objects.equals(this.quantity, equipment.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Equipment {\n");

        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
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

