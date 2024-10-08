package org.ddd.fundamental.tamagotchi.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ddd.fundamental.tamagotchi.domain.converter.PhoneNumberConverter;
import org.ddd.fundamental.tamagotchi.domain.exception.TamagotchiNameInvalidException;
import org.ddd.fundamental.tamagotchi.domain.exception.TamagotchiStatusException;
import org.ddd.fundamental.tamagotchi.domain.value.PhoneNumber;
import org.ddd.fundamental.tamagotchi.dto.TamagotchiDto;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.UUID;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.*;


/**
 * 类设计原则:
 * 1、定义出这个类的访问范围
 * 2、定义出属性和方法的访问范围
 * 3、定义出类需要的API
 * 4、将修改和查询的方法一定要分开
 * 5、方法设计中哪些使用值？哪些使用引用
 */
@Entity
@Table
@NoArgsConstructor(access = PROTECTED)
@Setter(PRIVATE)
@Getter(PROTECTED)
public class Tamagotchi {
    @Id
    @Type(type ="uuid-char")
    private UUID id;

    private String name;

    @Column(name = "phone_number")
    @Convert(converter = PhoneNumberConverter.class)
    @NotNull
    private PhoneNumber phoneNumber;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pocket_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @NotFound(action = NotFoundAction.IGNORE)
    private Pocket pocket;

    @Enumerated(STRING)
    private Status status;

    public void changeName(String name){
        if (status.equals(Status.PENDING)) {
            throw new TamagotchiStatusException("Tamagotchi cannot be modified because it's PENDING");
        }
        if (!nameIsValid(name)){
            throw new TamagotchiNameInvalidException("Invalid Tamagotchi name: " + name);
        }
        this.name = name;
    }

    public void changeStatus(Status status) {
        this.status = status;
    }

    public TamagotchiDto toDto() {
        return new TamagotchiDto(id, name, status,phoneNumber);
    }

    private static boolean nameIsValid(String name) {
        return name != null && !name.isBlank();
    }

    public static Tamagotchi newTamagotchi(String name, Status status, Pocket pocket) {
        Tamagotchi tamagotchi = new Tamagotchi();
        tamagotchi.setId(UUID.randomUUID());
        tamagotchi.setName(name);
        tamagotchi.setPocket(pocket);
        tamagotchi.setStatus(status);
        tamagotchi.setPhoneNumber(new PhoneNumber("+8613570863933"));
        return tamagotchi;
    }
}
