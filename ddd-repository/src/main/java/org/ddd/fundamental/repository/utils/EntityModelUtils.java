package org.ddd.fundamental.repository.utils;

import org.ddd.fundamental.repository.core.EntityModel;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class EntityModelUtils {

    private static <T extends EntityModel<?>, V extends Comparable<V>> Function<T,T> markUpdateDirty(V dirtyKeyToValue){
        return markDirty(dirtyKeyToValue,"update");
    }

    private static <T extends EntityModel<?>, V extends Comparable<V>> Function<T,T> markDeleteDirty(V dirtyKeyToValue){
        return markDirty(dirtyKeyToValue,"delete");
    }

    private static <T extends EntityModel<?>, V extends Comparable<V>> Function<T,T> markDirty(V dirtyKeyToValue, String type){
        Predicate<String> predicate = v ->v.equals(dirtyKeyToValue);
        Function<T,T> markDirtyFunc = v ->{
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

    private static <T extends EntityModel<Id>, Id extends Comparable<Id>> Function<T,T> markUpdateDirtyById(Id id){
        return markDirtyById(id,"update");
    }

    private static <T extends EntityModel<Id>, Id extends Comparable<Id>> Function<T,T> markDeleteDirtyById(Id id){
        return markDirtyById(id,"delete");
    }

    private static <T extends  EntityModel<Id>, Id extends Comparable<Id>> Function<T,T> markDirtyById(Id id, String type){
        Predicate<Id> predicate = v ->v.equals(id);
        Function<T,T> markDirtyFunc = v ->{
            if (predicate.test(v.getId())) {
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

    public static <T extends EntityModel<?>, V extends Comparable<V>> List<T> markDirtyLists(List<T> dataLists, V dirtyKeyToValue, String type){
        return dataLists.stream().map(markDirty(dirtyKeyToValue, type)).collect(Collectors.toList());
    }

    public static <T extends EntityModel<?>, V extends Comparable<V>> List<T> markUpdateDirtyLists(List<T> dataLists, V dirtyKeyToValue){
        return dataLists.stream().map(markUpdateDirty(dirtyKeyToValue)).collect(Collectors.toList());
    }

    public static <T extends EntityModel<?>, V extends Comparable<V>> List<T> markDeleteDirtyLists(List<T> dataLists, V dirtyKeyToValue){
        return dataLists.stream().map(markDeleteDirty(dirtyKeyToValue)).collect(Collectors.toList());
    }

    public static <T extends EntityModel<Id>, Id extends Comparable<Id>> List<T> markUpdateDirtyListsById(List<T> dataLists, Id id){
        return dataLists.stream().map(markUpdateDirtyById(id)).collect(Collectors.toList());
    }

    public static <T extends EntityModel<Id>, Id extends Comparable<Id>> List<T> markDeleteDirtyListsById(List<T> dataLists, Id id){
        return dataLists.stream().map(markDeleteDirtyById(id)).collect(Collectors.toList());
    }

    public static <T extends EntityModel<Id>, Id extends Comparable<Id>> List<T> markUpdateDirtyListsByIds(List<T> dataLists, Set<Id> ids){
        return markDirtyByIds(dataLists, ids, "update");
    }

    private static <T extends EntityModel<Id>, Id extends Comparable<Id>> List<T> handleList(Function<List<T>, List<T>> func, List<T> dataLists){
        return func.apply(dataLists);
    }

    public static <T extends EntityModel<Id>, Id extends Comparable<Id>> List<T> markDeleteDirtyListsByIds(List<T> dataLists, Set<Id> ids){
        return markDirtyByIds(dataLists, ids, "delete");
    }

    private static <T extends EntityModel<Id>, Id extends Comparable<Id>> List<T> markDirtyByIds(List<T> dataLists, Set<Id> ids, String type){
        for (T entity: dataLists) {
            Id entityId = entity.getId();
            if (ids.contains(entityId)){
                if (type.equals("update")){
                    entity.updateDirty();
                } else {
                    entity.deleteDirty();
                }
            }
        }
        return dataLists;
    }

}
