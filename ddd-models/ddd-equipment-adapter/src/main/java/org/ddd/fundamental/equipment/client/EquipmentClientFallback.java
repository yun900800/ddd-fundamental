package org.ddd.fundamental.equipment.client;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.equipment.value.EquipmentMaster;
import org.ddd.fundamental.equipment.value.EquipmentSize;
import org.ddd.fundamental.equipment.value.MaintainStandard;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.shared.api.equipment.ConfigureMaterialDTO;
import org.ddd.fundamental.shared.api.equipment.EquipmentDTO;
import org.ddd.fundamental.shared.api.equipment.EquipmentResourceDTO;
import org.ddd.fundamental.shared.api.equipment.ToolingDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
public class EquipmentClientFallback implements EquipmentClient {

    /**
     * 异常
     */
    private final Throwable cause;
    public EquipmentClientFallback(Throwable cause){
        this.cause = cause;
    }

    @Override
    public List<EquipmentDTO> equipments() {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            log.error("404 error took place when equipments was called: "
                    + ". Error message: "
                    + cause.getLocalizedMessage());
        } else if (cause instanceof RetryableException && ((FeignException) cause).status() == -1) {

            log.error("status is {}",((RetryableException) cause).status() );
            log.error("服务不可用");

        } else if (cause instanceof HystrixTimeoutException) {
            log.error("服务不可用");

        } else {
            log.error("Other error took place: " + cause.getLocalizedMessage());
        }
        log.info("EquipmentClientFallback is execute");
        log.error("error is:{}",cause);
        EquipmentDTO equipmentDTO = EquipmentDTO.create(
                EquipmentId.randomId(EquipmentId.class),
                EquipmentMaster.newBuilder()
                        .assetNo("系统默认设备编号")
                        .info(ChangeableInfo.create("系统默认设备","系统默认描述"))
                        .size(EquipmentSize.create(200.5,300.8,100.2))
                        .standard(MaintainStandard.create("",new Date()))
                        .noPersonInfoWithQuality()
                        .noQualityInfo()
                        .build()
        );
        List<EquipmentDTO> result = new ArrayList<>();
        result.add(equipmentDTO);
        return result;
    }

    @Override
    public List<ToolingDTO> toolingList() {
        log.error("error is:{}",cause);
        return new ArrayList<>();
    }

    @Override
    public List<EquipmentResourceDTO> queryResourcesByInputAndOutputIds(ConfigureMaterialDTO configureMaterialDTO) {
        log.error("error is:{}",cause);
        return new ArrayList<>();
    }
}
