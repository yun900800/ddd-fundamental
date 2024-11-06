package org.ddd.fundamental.material.application;

import org.ddd.fundamental.material.domain.repository.MaterialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class MaterialApplication {

    private MaterialRepository materialRepository;


}
