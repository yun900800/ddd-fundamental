package org.ddd.fundamental.equipment.value.resources;

import org.ddd.fundamental.core.ValueObject;
import org.ddd.fundamental.material.value.MaterialId;

import java.util.List;
import java.util.Objects;

public class InputAndOutputValue implements ValueObject {

    private List<MaterialId> materialInputs;

    private List<MaterialId> materialOutputs;

    @SuppressWarnings("unused")
    public InputAndOutputValue(){}

    private InputAndOutputValue(List<MaterialId> materialInputs,
                                List<MaterialId> materialOutputs){
        this.materialInputs = materialInputs;
        this.materialOutputs = materialOutputs;
    }

    public static InputAndOutputValue create(List<MaterialId> materialInputs,
                                             List<MaterialId> materialOutputs){
        return new InputAndOutputValue(materialInputs,materialOutputs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InputAndOutputValue)) return false;
        InputAndOutputValue that = (InputAndOutputValue) o;
        return Objects.equals(materialInputs, that.materialInputs) && Objects.equals(materialOutputs, that.materialOutputs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(materialInputs, materialOutputs);
    }

    public List<MaterialId> getMaterialInputs() {
        return materialInputs;
    }

    public List<MaterialId> getMaterialOutputs() {
        return materialOutputs;
    }

    @Override
    public String toString() {
        return "InputAndOutputValue{" +
                "materialInputs=" + materialInputs +
                ", materialOutputs=" + materialOutputs +
                '}';
    }
}
