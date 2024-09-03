package org.ddd.fundamental.visa.visaApplication.infrastructure.rest;

import org.ddd.fundamental.common.exception.DomainException;
import org.ddd.fundamental.visa.visaApplication.application.VisaApplicationService;
import org.ddd.fundamental.visa.visaApplication.infrastructure.rest.command.VisaApplicationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visa")
public class VisaApplicationController {


    private final VisaApplicationService visaApplicationService;

    public VisaApplicationController(VisaApplicationService visaApplicationService) {
        this.visaApplicationService = visaApplicationService;
    }

    @PostMapping("/apply")
    @ResponseStatus(HttpStatus.CREATED)
    public void apply(@RequestBody VisaApplicationRequest visaApplicationRequest) throws DomainException {

        visaApplicationService.processVisaApplication(visaApplicationRequest);

    }


}
