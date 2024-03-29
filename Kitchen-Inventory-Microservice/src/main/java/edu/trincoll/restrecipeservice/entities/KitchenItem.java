package edu.trincoll.restrecipeservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
public class KitchenItem
{
    @NotBlank(message = "Ingredients must be named")
    String name;
    String unit;
    @NotNull(message = "Ingredients must be quantified")
    Float amount;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

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

    public void setName(String name) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KitchenItem that = (KitchenItem) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, unit, amount, id);
    }

    @Override
    public String toString() {
        if(unit != null) {
            return "Ingredient{" +
                    "amount=" + amount +
                    ", unit='" + unit + '\'' +
                    ", name='" + name + '\'' +
                    ", id=" + id +
                    '}';
        } else {
            return "Ingredient{" +
                    "amount=" + amount +
                    ", name='" + name + '\'' +
                    ", id=" + id +
                    '}';
        }
    }
}
