package org.ddd.fundamental.repository.core;

import java.io.Serializable;
import java.util.function.Function;


public abstract class EntityModel<Id extends Comparable<Id>> implements Serializable {

    private Id id;

    private boolean isUpdateDirty;

    private boolean isDeleteDirty;

    public EntityModel(Id id) {
        this.id = id;
        this.isUpdateDirty = false;
        this.isDeleteDirty = false;
    }

    public boolean isUpdateDirty() {
        return isUpdateDirty;
    }

    public void updateDirty() {
        this.updateDirty(true);
    }

    public void updateDirty(boolean isUpdateDirty){
        this.isUpdateDirty = isUpdateDirty;
    }

    public boolean isDeleteDirty() {
        return isDeleteDirty;
    }

    public void deleteDirty() {
        this.deleteDirty(true);
    }

    public void deleteDirty(boolean isDeleteDirty){
        this.isDeleteDirty = isDeleteDirty;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String dirtyKey(){
        return id.toString();
    }

    /**
     * 构造标注dirty数据的函数
     * @param id
     * @param type
     * @return
     */
    public Function<EntityModel<?>,EntityModel<?>> markDirty(Id id,String type){
        Function<EntityModel<?>,EntityModel<?>> markDirtyFunc = v ->{
            if (v.getId().equals(id)) {
                if (type.equals("update")){
                    v.updateDirty();
                } else {
                    v.deleteDirty();
                }
            }
            return v;
        };
        return markDirtyFunc;
    }
}
