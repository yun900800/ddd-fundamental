package org.ddd.fundamental.workprocess.client;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.material.MaterialMaster;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.material.value.MaterialType;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MaterialClientFallback implements MaterialClient {

    /**
     * 异常
     */
    private final Throwable cause;
    public MaterialClientFallback(Throwable cause){
        this.cause = cause;
    }

    @Override
    public List<MaterialDTO> materialsByMaterialType(MaterialType materialType) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            log.error("404 error took place when getAlbums was called with userId: "
                    + materialType +  ". Error message: "
                    + cause.getLocalizedMessage());
        } else if (cause instanceof RetryableException && ((FeignException) cause).status() == -1) {

            log.error("status is {}",((RetryableException) cause).status() );
            log.error("服务不可用");

        } else if (cause instanceof HystrixTimeoutException) {
            log.error("服务不可用");

        } else {
            log.error("Other error took place: " + cause.getLocalizedMessage());
        }
        log.info("MaterialClientFallback is execute");
        MaterialDTO materialDTO = new MaterialDTO(
                MaterialMaster.create("default-code","默认产品","default-spec","个"),
                MaterialId.randomId(MaterialId.class),
                materialType
        );
        List<MaterialDTO> materialDTOS = new ArrayList<>();
        materialDTOS.add(materialDTO);
        return materialDTOS;
    }
}
