package org.ddd.fundamental.shared.api.material;


import org.ddd.fundamental.core.AbstractDTO;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.value.MaterialId;

public class MaterialDTO extends AbstractDTO<MaterialId> {



    @SuppressWarnings("unused")
    private MaterialDTO(){
        super(MaterialId.randomId(MaterialId.class));
    }

    private MaterialMaster materialMaster;


    public MaterialDTO(MaterialMaster materialMaster, MaterialId id) {
        super(id);
        this.materialMaster = materialMaster;
    }

    public static MaterialDTO create(MaterialMaster materialMaster, MaterialId id){
        return new MaterialDTO(materialMaster,id);
    }

    public MaterialMaster getMaterialMaster() {
        if (null == materialMaster){
           return null;
        }
        return materialMaster.clone();
    }

    @Override
    public String toString() {
        return "MaterialDTO{" +
                "materialMaster=" + materialMaster +
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
