package org.ddd.fundamental.shared.api.material;


import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.enums.MaterialInputOutputType;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;

public class MaterialDTO extends AbstractDTO<MaterialId> {



    @SuppressWarnings("unused")
    private MaterialDTO(){
        super(MaterialId.randomId(MaterialId.class));
    }

    private MaterialMaster materialMaster;

    private MaterialType materialType;

    private MaterialInputOutputType inputOutputType;


    public MaterialDTO(MaterialMaster materialMaster, MaterialId id,
                       MaterialType materialType,MaterialInputOutputType inputOutputType) {
        super(id);
        this.materialMaster = materialMaster;
        this.materialType = materialType;
        this.inputOutputType = inputOutputType;
    }

    public static MaterialDTO create(MaterialMaster materialMaster, MaterialId id,
                                     MaterialType materialType,
                                     MaterialInputOutputType inputOutputType){
        return new MaterialDTO(materialMaster,id,materialType,inputOutputType);
    }

    public MaterialMaster getMaterialMaster() {
        if (null == materialMaster){
           return null;
        }
        return materialMaster.clone();
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    @Override
    public String toString() {
        return "MaterialDTO{" +
                "materialMaster=" + materialMaster +
                "materialType=" + materialType +
                ", id=" + id() +
                '}';
    }

    @Override
    public MaterialId id() {
        return super.id;
    }

    public String toJson() {
        return super.toJson();
    }
}
