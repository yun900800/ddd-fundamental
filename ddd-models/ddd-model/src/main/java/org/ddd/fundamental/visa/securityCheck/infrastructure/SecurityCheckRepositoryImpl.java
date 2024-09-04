package org.ddd.fundamental.visa.securityCheck.infrastructure;

import org.ddd.fundamental.common.UniqueIdGenerator;
import org.ddd.fundamental.visa.securityCheck.domain.model.SecurityCheck;
import org.ddd.fundamental.visa.securityCheck.domain.model.SecurityCheckId;
import org.ddd.fundamental.visa.securityCheck.domain.repository.SecurityCheckRepository;
import org.ddd.fundamental.visa.securityCheck.infrastructure.jms.JmsSecurityCheckProducer;
import org.ddd.fundamental.visa.securityCheck.infrastructure.jms.entity.SecurityCheckMessageEntity;
import org.ddd.fundamental.visa.securityCheck.infrastructure.jpa.JpaSecurityCheckRepository;
import org.ddd.fundamental.visa.securityCheck.infrastructure.jpa.entity.SecurityCheckEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityCheckRepositoryImpl implements SecurityCheckRepository {

    private final JmsSecurityCheckProducer jmsSecurityCheckProducer;

    private final JpaSecurityCheckRepository jpaSecurityCheckRepository;
    @Value("${jms.queue.securityCheck.request.source1}")
    String destinationSource1;

    @Value("${jms.queue.securityCheck.request.source2}")
    String destinationSource2;

    @Value("${jms.queue.securityCheck.request.source3}")
    String destinationSource3;

    public SecurityCheckRepositoryImpl(JmsSecurityCheckProducer jmsSecurityCheckProducer, JpaSecurityCheckRepository securityCheckRepository) {
        this.jmsSecurityCheckProducer = jmsSecurityCheckProducer;
        this.jpaSecurityCheckRepository = securityCheckRepository;
    }
    @Override
    public Long generateId() {
        return UniqueIdGenerator.generateUniqueId();
    }

    @Override
    public void save(SecurityCheck securityCheck) {
        SecurityCheckEntity securityCheckEntity = SecurityCheckEntity.builder()
                .id(securityCheck.getId().value())
                .source1SecurityCheckStatus(securityCheck.source1SecurityCheckStatus())
                .source2SecurityCheckStatus(securityCheck.source2SecurityCheckStatus())
                .source3SecurityCheckStatus(securityCheck.source3SecurityCheckStatus())
                .visaApplicationRefId(securityCheck.visaApplication().getId().value())
                .build();

        jpaSecurityCheckRepository.save(securityCheckEntity);
    }

    @Override
    public void sendForSource1SecurityCheck(SecurityCheck securityCheck) {
        jmsSecurityCheckProducer.send(toJmsMessage(securityCheck), destinationSource1);
    }

    private SecurityCheckMessageEntity toJmsMessage(SecurityCheck securityCheck) {

        return SecurityCheckMessageEntity.builder()
                .passportNumber(securityCheck.visaApplication().applicantPassportInfo().passportNumber())
                .phoneNumber(securityCheck.visaApplication().applicantAddress().phoneNumber())
                .city(securityCheck.visaApplication().applicantAddress().city())
                .address(securityCheck.visaApplication().applicantAddress().address())
                .postCode(securityCheck.visaApplication().applicantAddress().postCode())
                .country(securityCheck.visaApplication().applicantAddress().country())
                .build();

    }

    @Override
    public void sendForSource2SecurityCheck(SecurityCheck securityCheck) {
        jmsSecurityCheckProducer.send(toJmsMessage(securityCheck), destinationSource2);
    }

    @Override
    public void sendForSource3SecurityCheck(SecurityCheck securityCheck) {
        jmsSecurityCheckProducer.send(toJmsMessage(securityCheck), destinationSource3);
    }

    @Override
    public Optional<SecurityCheck> retrieveSecurityCheck(SecurityCheckId securityCheckId) {
        return Optional.empty();
    }
}
