package org.ddd.fundamental.shared.api.material;

import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;

import java.util.Map;
import java.util.Set;

public class MaterialRequest extends MaterialDTO {

    private Map<String,String> characterMap;

    private Map<String,String> requiredMap;

    private Set<String> requiredSets;
    private Set<String> characterSets;

    private MaterialType materialType;

    @SuppressWarnings("unused")
    protected MaterialRequest(){
        super(null,new MaterialId("0"));
    }

    private MaterialRequest(MaterialMaster materialMaster, MaterialId id,
                            MaterialType materialType,
                            Set<String> requiredSets,
                            Set<String> characterSets,
                           Map<String,String> requiredMap,
                           Map<String,String> characterMap) {
        super(materialMaster, id);
        this.characterMap = characterMap;
        this.requiredMap = requiredMap;
        this.materialType = materialType;
        this.requiredSets = requiredSets;
        this.characterSets = characterSets;
    }

    public static MaterialRequest create(MaterialMaster materialMaster, MaterialId id,
                                         MaterialType materialType,
                                         Set<String> requiredSets,
                                         Set<String> characterSets,
                                         Map<String,String> requiredMap,
                                         Map<String,String> characterMap){
        return new MaterialRequest(materialMaster,id,
                materialType, requiredSets, characterSets,
                requiredMap, characterMap);
    }

    public Map<String, String> getCharacterMap() {
        return characterMap;
    }

    public Map<String, String> getRequiredMap() {
        return requiredMap;
    }

    @Override
    public String toString() {
        return "MaterialRequest{" +
                "characterMap=" + characterMap +
                ", requiredMap=" + requiredMap +
                ", requiredSets=" + requiredSets +
                ", characterSets=" + characterSets +
                ", materialType=" + materialType +
                ", id=" + id() +
                '}';
    }

    public Set<String> getRequiredSets() {
        return requiredSets;
    }

    public Set<String> getCharacterSets() {
        return characterSets;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

}
