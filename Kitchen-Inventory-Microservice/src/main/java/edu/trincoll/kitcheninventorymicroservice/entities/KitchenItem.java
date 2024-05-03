package edu.trincoll.kitcheninventorymicroservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class KitchenItem
{
    @Id
    @NotBlank(message = "KitchenItems must be named")
    String name;
    String unit;
    @NotNull(message = "KitchenItems must be quantified")
    Float amount;

    public KitchenItem()
    {

    }

    public KitchenItem(Float amount, String unit, String name)
    {
        this.name = name;
        this.unit = unit;
        this.amount = amount;
    }

    public KitchenItem(Float amount, String name)
    {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KitchenItem that = (KitchenItem) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(amount, that.amount); //&&
                //Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, unit, amount/*, id*/);
    }

    @Override
    public String toString() {
        if(unit != null) {
            return "KitchenItem{" +
                    "amount=" + amount +
                    ", unit='" + unit + '\'' +
                    ", name='" + name + '\'' +
                    /*", id=" + id +*/
                    '}';
        } else {
            return "KitchenItem{" +
                    "amount=" + amount +
                    ", name='" + name + '\'' +
                    /*", id=" + id +*/
                    '}';
        }
    }
}
