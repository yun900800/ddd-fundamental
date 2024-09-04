package org.ddd.fundamental.visa.securityCheck.infrastructure.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ddd.fundamental.visa.securityCheck.domain.model.SecurityStatus;

import javax.persistence.*;

@Data
@Entity
@Table(name = "SECURTY_CHECK")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityCheckEntity {
    @Id
    private Long id;
    private Long visaApplicationRefId;

    @Enumerated(EnumType.STRING)
    private SecurityStatus source1SecurityCheckStatus;

    @Enumerated(EnumType.STRING)
    private SecurityStatus source2SecurityCheckStatus;

    @Enumerated(EnumType.STRING)
    private SecurityStatus source3SecurityCheckStatus;

}
