package org.ddd.fundamental.repository.utils;

import org.ddd.fundamental.repository.core.EntityModel;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class EntityModelUtils {

    private static Function<EntityModel<?>,EntityModel<?>> markUpdateDirty(String dirtyKeyToValue){
        return markDirty(dirtyKeyToValue,"update");
    }

    private static Function<EntityModel<?>,EntityModel<?>> markDeleteDirty(String dirtyKeyToValue){
        return markDirty(dirtyKeyToValue,"delete");
    }
    private static Function<EntityModel<?>,EntityModel<?>> markDirty(String dirtyKeyToValue, String type){
        Predicate<String> predicate = v ->v.equals(dirtyKeyToValue);
        Function<EntityModel<?>,EntityModel<?>> markDirtyFunc = v ->{
            if (predicate.test(v.dirtyKey())) {
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


    /**
     * 取得标注数据的list
     * @param dataLists
     * @param dirtyKeyToValue
     * @param type
     * @return
     */
    public static List<? extends EntityModel<?>> markDirtyLists(List<? extends EntityModel<?>> dataLists, String dirtyKeyToValue, String type){
        return dataLists.stream().map(markDirty(dirtyKeyToValue, type)).collect(Collectors.toList());
    }

    public static List<? extends EntityModel<?>> markUpdateDirtyLists(List<? extends EntityModel<?>> dataLists, String dirtyKeyToValue){
        return dataLists.stream().map(markUpdateDirty(dirtyKeyToValue)).collect(Collectors.toList());
    }

    public static List<? extends EntityModel<?>> markDeleteDirtyLists(List<? extends EntityModel<?>> dataLists, String dirtyKeyToValue){
        return dataLists.stream().map(markDeleteDirty(dirtyKeyToValue)).collect(Collectors.toList());
    }
}
