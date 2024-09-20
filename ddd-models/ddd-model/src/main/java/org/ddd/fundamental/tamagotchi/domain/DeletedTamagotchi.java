package org.ddd.fundamental.tamagotchi.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ddd.fundamental.tamagotchi.domain.converter.PhoneNumberConverter;
import org.ddd.fundamental.tamagotchi.domain.value.PhoneNumber;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.UUID;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
@Setter(PRIVATE)
@Table(name = "deleted_tamagotchi")
public class DeletedTamagotchi {
    @Id
    @Type(type ="uuid-char")
    private UUID id;

    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pocket_id")
    private Pocket pocket;

    @Enumerated(STRING)
    private Status status;

    @Column(name = "phone_number")
    @Convert(converter = PhoneNumberConverter.class)
    @NotNull
    private PhoneNumber phoneNumber;

    public static DeletedTamagotchi newDeletedTamagotchi(Tamagotchi tamagotchi) {
        DeletedTamagotchi deletedTamagotchi = new DeletedTamagotchi();
        deletedTamagotchi.setId(UUID.randomUUID());
        deletedTamagotchi.setName(tamagotchi.getName());
        deletedTamagotchi.setPocket(tamagotchi.getPocket());
        deletedTamagotchi.setStatus(tamagotchi.getStatus());
        deletedTamagotchi.setPhoneNumber(tamagotchi.getPhoneNumber());
        return deletedTamagotchi;
    }
}
