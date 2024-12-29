package org.ddd.fundamental.bom.application.async;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.bom.application.query.BomQueryService;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class AsyncService {

    private final ListeningExecutorService lExecService;

    private final BomQueryService queryService;


    @Autowired(required = false)
    public AsyncService(BomQueryService queryService) {
        this.lExecService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(4));
        this.queryService = queryService;
    }

    @Autowired(required = false)
    public AsyncService(ListeningExecutorService lExecService,
                        BomQueryService queryService) {
        this.lExecService = lExecService;
        this.queryService = queryService;
    }

    /**
     *
     * @param materialType
     * @return
     */
    public ListenableFuture<List<MaterialDTO>> fetchMaterialByType(MaterialType materialType) {
        return lExecService.submit(() -> queryService.materialsByMaterialType(materialType));
    }

    public ListenableFuture<List<MaterialDTO>> fetchMaterialByIds(List<String> ids){
        return lExecService.submit(() -> queryService.materialsByIds(ids));
    }

    @PreDestroy
    public void destroy(){
        lExecService.shutdown();
    }


}
