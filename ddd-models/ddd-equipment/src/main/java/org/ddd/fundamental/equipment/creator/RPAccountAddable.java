package org.ddd.fundamental.equipment.creator;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.creator.DataAddable;
import org.ddd.fundamental.equipment.domain.model.RPAccount;
import org.ddd.fundamental.equipment.domain.repository.RPAccountRepository;
import org.ddd.fundamental.equipment.value.RPAccountValue;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.equipment.RPAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RPAccountAddable implements DataAddable {

    private final RPAccountRepository accountRepository;

    private final RedisStoreManager manager;

    public RPAccountAddable(@Autowired RPAccountRepository accountRepository,
                              @Autowired RedisStoreManager manager){
        this.accountRepository = accountRepository;
        this.manager = manager;
    }

    private List<RPAccount> rpAccounts = new ArrayList<>();

    public static List<RPAccount> createRpaAccount(){
        RPAccount account1 = new RPAccount(
                RPAccountValue.create(1,"SUT","二级使用时间")
        );
        RPAccount account2 = new RPAccount(
                RPAccountValue.create(2,"DCI","干扰性中断,也叫作技术性停机")
        );
        RPAccount account3 = new RPAccount(
                RPAccountValue.create(3,"LCI","物流性中断,也叫作组织性停机")
        );
        RPAccount account4 = new RPAccount(
                RPAccountValue.create(4,"SCI","人员性中断")
        );
        RPAccount account5 = new RPAccount(
                RPAccountValue.create(5,"IMN","非计划设备闲置")
        );
        RPAccount account6 = new RPAccount(
                RPAccountValue.create(6,"IMS","计划的设备闲置")
        );
        RPAccount account7 = new RPAccount(
                RPAccountValue.create(7,"SET","调试")
        );
        RPAccount account8 = new RPAccount(
                RPAccountValue.create(8,"STA","启动")
        );
        RPAccount account9 = new RPAccount(
                RPAccountValue.create(9,"U8","可自定义(如试制生产或类似的时间)")
        );
        RPAccount account10 = new RPAccount(
                RPAccountValue.create(10,"U9","可自定义")
        );
        RPAccount account11 = new RPAccount(
                RPAccountValue.create(11,"MUT","主要利用时间,即生产时间")
        );
        RPAccount account12 = new RPAccount(
                RPAccountValue.create(12,"BKS","休息时间,例如停工，休息，即不计入生产开工的其他时间")
        );
        return Arrays.asList(account1,account2,account3,account4
                ,account5,account6,account7,account8,
                account9,account10,account11,account12);
    }

    private static List<RPAccountDTO> entityToDTO(List<RPAccount> entities){
        if (CollectionUtils.isEmpty(entities)){
            return new ArrayList<>();
        } else {
            return entities.stream().map(v->RPAccountDTO.create(v.id(),v.getAccountValue()))
                    .collect(Collectors.toList());
        }
    }

    @Override
    @Transactional
    public void execute() {
        log.info("store all RPAccounts to db start");
        this.rpAccounts = createRpaAccount();
        this.accountRepository.saveAll(this.rpAccounts);
        log.info("store all RPAccounts from db finished");
        List<RPAccountDTO> rpAccountDTOS = entityToDTO(rpAccounts);
        log.info("store all RPAccounts to cache start");
        this.manager.storeDataListToCache(rpAccountDTOS);
        log.info("store all RPAccounts to cache finished");
    }
}
