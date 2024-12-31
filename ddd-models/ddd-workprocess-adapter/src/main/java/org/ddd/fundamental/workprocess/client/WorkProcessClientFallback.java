package org.ddd.fundamental.workprocess.client;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.shared.api.optemplate.CraftsmanShipTemplateDTO;
import org.ddd.fundamental.workprocess.value.CraftsmanShipId;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class WorkProcessClientFallback implements WorkProcessClient{

    /**
     * 异常
     */
    private final Throwable cause;
    public WorkProcessClientFallback(Throwable cause){
        this.cause = cause;
    }
    @Override
    public List<CraftsmanShipTemplateDTO> craftsmanShipTemplates() {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            log.error("404 error took place when craftsmanShipTemplates was called with materialType: "
                    +    ". Error message: "
                    + cause.getLocalizedMessage());
        } else if (cause instanceof RetryableException && ((FeignException) cause).status() == -1) {

            log.error("status is {}",((RetryableException) cause).status() );
            log.error("服务不可用");

        } else if (cause instanceof HystrixTimeoutException) {
            log.error("服务不可用");

        } else {
            log.error("Other error took place: " + cause.getLocalizedMessage());
        }
        log.info("WorkProcessClientFallback is execute");
        return Arrays.asList(defaultCraftsmanShipTemplateDTO());
    }

    private static CraftsmanShipTemplateDTO defaultCraftsmanShipTemplateDTO(){
        return CraftsmanShipTemplateDTO.create(CraftsmanShipId.randomId(CraftsmanShipId.class),
                MaterialId.randomId(MaterialId.class), new ArrayList<>());
    }

}
