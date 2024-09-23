package org.ddd.fundamental.tamagotchi.domain;

import lombok.*;
import org.ddd.fundamental.tamagotchi.domain.command.TamagotchiCreateRequest;
import org.ddd.fundamental.tamagotchi.domain.command.TamagotchiUpdateRequest;
import org.ddd.fundamental.tamagotchi.domain.exception.TamagotchiDeleteException;
import org.ddd.fundamental.tamagotchi.domain.exception.TamagotchiNameInvalidException;
import org.ddd.fundamental.tamagotchi.dto.PocketDto;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.PERSIST;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;
import static org.ddd.fundamental.tamagotchi.domain.DeletedTamagotchi.newDeletedTamagotchi;
import static org.ddd.fundamental.tamagotchi.domain.Status.CREATED;


/**
 * 注意,在这里，因为Tamagotchi的作用域是包级别的
 * 所以这个类的ID不能定义为Tamagotchi.ID, 因为外部的service不能访问这个类,所以尽管能用类型来区别参数
 * 但是似乎用命名更加方便;所以这里存在取舍的问题
 * 但是聚合根是一定可以使用这个方法的; 使用主类.ID的设计的原因如下:
 * https://dev.to/kirekov/spring-boot-power-of-value-objects-1oah
 */
@Entity
@Table
@NoArgsConstructor(access = PROTECTED)
@Setter(PRIVATE)
public class Pocket {

    @EmbeddedId
    @Getter
    private Pocket.ID id;

    private String name;

    @OneToMany(mappedBy = "pocket", cascade = ALL, targetEntity = Tamagotchi.class, orphanRemoval = true)
    @NotFound(action=NotFoundAction.IGNORE)
    private List<Tamagotchi> tamagotchis = new ArrayList<>();

    @OneToMany(mappedBy = "pocket", cascade = ALL, orphanRemoval = true)
    private List<DeletedTamagotchi> deletedTamagotchis = new ArrayList<>();

    public PocketDto toDto() {
        return new PocketDto(
                id,
                name,
                tamagotchis.stream()
                        .map(Tamagotchi::toDto)
                        .collect(Collectors.toList())
        );
    }

    public UUID createTamagotchi(TamagotchiCreateRequest request) {
        Tamagotchi newTamagotchi = Tamagotchi.newTamagotchi(request.name(), request.status(), this);
        tamagotchis.add(newTamagotchi);
        validateTamagotchiNamesUniqueness();
        return newTamagotchi.getId();
    }
    public void updateTamagotchi(UUID tamagotchiId, TamagotchiUpdateRequest request) {
        Tamagotchi tamagotchi = tamagotchiById(tamagotchiId);
        tamagotchi.changeName(request.name());
        tamagotchi.changeStatus(request.status());
        validateTamagotchiNamesUniqueness();
    }

    public void deleteTamagotchi(UUID tamagotchiId) {
        Tamagotchi tamagotchi = tamagotchiById(tamagotchiId);
        if (tamagotchis.size() == 1) {
            throw new TamagotchiDeleteException("Cannot delete Tamagotchi because it's the single one");
        }
        tamagotchis.remove(tamagotchi);
        addDeletedTamagotchi(tamagotchi);
    }

    public UUID restoreTamagotchi(String name) {
        DeletedTamagotchi deletedTamagotchi = deletedTamagotchiByName(name);
        return createTamagotchi(new TamagotchiCreateRequest(
                deletedTamagotchi.getName(),
                deletedTamagotchi.getStatus()
        ));
    }

    private void addDeletedTamagotchi(Tamagotchi tamagotchi) {
        Iterator<DeletedTamagotchi> iterator = deletedTamagotchis.iterator();
        // if Tamagotchi with the same has been deleted,
        // remove information about it
        while (iterator.hasNext()) {
            DeletedTamagotchi deletedTamagotchi = iterator.next();
            if (deletedTamagotchi.getName().equals(tamagotchi.getName())) {
                iterator.remove();
                break;
            }
        }
        deletedTamagotchis.add(
                newDeletedTamagotchi(tamagotchi)
        );
    }

    private Tamagotchi tamagotchiById(UUID tamagotchiId) {
        return tamagotchis.stream()
                .filter(t -> t.getId().equals(tamagotchiId))
                .findFirst()
                .orElseThrow();
    }

    private DeletedTamagotchi deletedTamagotchiByName(String name) {
        return deletedTamagotchis.stream()
                .filter(t -> t.getName().equals(name))
                .findFirst()
                .orElseThrow();
    }

    private void validateTamagotchiNamesUniqueness() {
        Set<String> names = new HashSet<>();
        for (Tamagotchi tamagotchi : tamagotchis) {
            if (!names.add(tamagotchi.getName())) {
                throw new TamagotchiNameInvalidException(
                        "Tamagotchi name is not unique: " + tamagotchi.getName());
            }
        }
    }

    public static Pocket newPocket(String name) {
        Pocket pocket = new Pocket();
        pocket.setId(new ID(UUID.randomUUID()));
        pocket.setName(name);
        pocket.createTamagotchi(new TamagotchiCreateRequest("Default", CREATED));
        return pocket;
    }

    @Data
    @Setter(PRIVATE)
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor(access = PROTECTED)
    public static class ID implements Serializable{
        @Column(updatable = false)
        @NotNull
        @Type(type ="uuid-char")
        private UUID id;
    }
}
