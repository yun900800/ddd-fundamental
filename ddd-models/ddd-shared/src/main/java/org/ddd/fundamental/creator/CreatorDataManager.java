package org.ddd.fundamental.creator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@Slf4j
public class CreatorDataManager{

    @Autowired(required = false)
    private List<DataRemovable> dataRemovables;

    @Autowired(required = false)
    private List<DataAddable> dataAddables;

    public void execute(){
        if (!CollectionUtils.isEmpty(dataRemovables)) {
            for (DataRemovable dataRemovable:dataRemovables) {
                dataRemovable.execute();
            }
        }
        if (!CollectionUtils.isEmpty(dataAddables)) {
            for (DataAddable dataAddable:dataAddables) {
                dataAddable.execute();
            }
        }
    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        log.info("dataRemovables is {}",dataRemovables);
//        log.info("dataAddables is {}",dataAddables);
//        execute();
//    }
}
