package org.ddd.fundamental.bom.application.command;

import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.bom.application.async.AsyncService;
import org.ddd.fundamental.bom.application.query.BomQueryService;
import org.ddd.fundamental.bom.domain.model.ProductStructureData;
import org.ddd.fundamental.bom.domain.repository.ProductStructureDataRepository;
import org.ddd.fundamental.bom.helper.BomHelper;
import org.ddd.fundamental.bom.value.MaterialIdNode;
import org.ddd.fundamental.bom.value.ProductStructure;
import org.ddd.fundamental.bom.value.ProductStructureNode;
import org.ddd.fundamental.material.client.MaterialClient;
import org.ddd.fundamental.material.value.MaterialId;
import org.ddd.fundamental.material.value.MaterialType;
import org.ddd.fundamental.shared.api.bom.ProductStructureDTO;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.ddd.fundamental.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class BomCommandService {

    private final ProductStructureDataRepository structureDataRepository;

    private final BomQueryService bomQueryService;

    private final AsyncService asyncService;

    private final ListeningExecutorService listenerExecService;

    @Autowired(required = false)
    public BomCommandService(ProductStructureDataRepository structureDataRepository,
                             BomQueryService bomQueryService,
                             AsyncService asyncService){
        this.structureDataRepository = structureDataRepository;
        this.bomQueryService = bomQueryService;
        this.asyncService = asyncService;
        this.listenerExecService = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
    }

    @PreDestroy
    public void destroy(){
        this.listenerExecService.shutdown();
    }

    /**
     * 添加一个节点到bom树上
     * @param productId
     * @param node
     */
    public void addNodeToBom(MaterialId productId,
                             MaterialIdNode<ProductStructureNode> node){
        ProductStructureDTO dto = bomQueryService.getProductStructure(productId);
        ProductStructure<ProductStructureNode> root = dto.getProductStructure();
        Multimap<MaterialId, MaterialId> multimap = root.productIdToNodeId();

        boolean flag = false;
        while (!flag){
            try {
                MaterialId nodeId = CollectionUtils.random(new ArrayList<>(multimap.values()));
                ProductStructure<ProductStructureNode> structureNode = root.searchById(nodeId);

                structureNode.addStructure(new ProductStructure(node.getCurrent(),node,node.getData().getMaterialType()));
                node.changeParent(structureNode.getId());
                flag = true;
            }catch (Exception e){
                log.error("retry search node");
            }
        }
        List<MaterialIdNode<ProductStructureNode>> list = root.toMaterialIdList();
        List<ProductStructureData> structureDataList = list.stream().map(v->ProductStructureData.create(v)).collect(Collectors.toList());
        ProductStructureData data = ProductStructureData.create(
                node
        );
        log.info("node is {}",node);
        structureDataRepository.persist(data);
    }

    private void createFetchMaterialAsyncTask(String productId, int spareSize, int rawSize) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ListenableFuture<List<MaterialDTO>> productTask = asyncService.fetchMaterialByIds(Arrays.asList(productId));
        ListenableFuture<List<MaterialDTO>> sparesTask = asyncService.fetchMaterialByType(MaterialType.WORKING_IN_PROGRESS);
        ListenableFuture<List<MaterialDTO>> rawMaterialsTask = asyncService.fetchMaterialByType(MaterialType.RAW_MATERIAL);
        ListenableFuture<List<List<MaterialDTO>>> configsTask = Futures.successfulAsList(productTask, sparesTask, rawMaterialsTask);
        Futures.addCallback(configsTask, new FutureCallback<List<List<MaterialDTO>>>() {
            @Override
            public void onSuccess(@Nullable List<List<MaterialDTO>> result) {
                stopWatch.stop();
                System.out.printf("异步执行时长：%d 毫秒.%n", stopWatch.getTotalTimeMillis());
                List<MaterialDTO> products = result.get(0);
                List<MaterialDTO> spares = result.get(1);
                List<MaterialDTO> raw = result.get(2);
                log.info("execute callback");
                ProductStructure<ProductStructureNode> productStructure = BomHelper.createProductStructure(
                        products.get(0), MaterialType.PRODUCTION
                );
                Set<ProductStructure<ProductStructureNode>> spareStructures = BomHelper.createProductStructuresAndMaterialType(spares.size(),
                        spares, MaterialType.WORKING_IN_PROGRESS
                );

                Set<ProductStructure<ProductStructureNode>> rawStructures = BomHelper.createProductStructuresAndMaterialType(raw.size(),
                        raw, MaterialType.RAW_MATERIAL
                );
                for (int i = 0 ; i< spareSize; i++) {
                    ProductStructure<ProductStructureNode> spare = CollectionUtils.random(new ArrayList<>(spareStructures));
                    for (int j = 0 ; j < rawSize; j++) {
                        spare.addStructure(CollectionUtils.random(new ArrayList<>(rawStructures)));
                    }
                    productStructure.addStructure(spare);
                }
                List<MaterialIdNode<ProductStructureNode>> list = productStructure.toMaterialIdList();
                List<ProductStructureData> structureDataList = list.stream().map(v->ProductStructureData.create(v)).collect(Collectors.toList());
                structureDataRepository.persistAll(structureDataList);
                log.info("execute callback finished");
            }

            @Override
            public void onFailure(Throwable t) {
                log.error("throw exception {}",t);
            }
        }, listenerExecService);
    }


    /**
     * 创建一个产品Bom
     * @param productId
     */
    public void createProductBom(MaterialId productId, int spareSize, int rawSize){

        createFetchMaterialAsyncTask(productId.toUUID(), spareSize,rawSize);

        StopWatch stopWatch1 = new StopWatch();
        stopWatch1.start();
        List<MaterialDTO> products = bomQueryService
                .materialsByIds(Arrays.asList(productId.toUUID()));
        log.info("products is {}",products);
        List<MaterialDTO> spares = bomQueryService
                .materialsByMaterialType(MaterialType.WORKING_IN_PROGRESS);
        List<MaterialDTO> rawMaterials = bomQueryService
                .materialsByMaterialType(MaterialType.RAW_MATERIAL);
        stopWatch1.stop();
        System.out.printf("同步执行时长：%d 毫秒.%n", stopWatch1.getTotalTimeMillis());
        ProductStructure<ProductStructureNode> productStructure = BomHelper.createProductStructure(
                products.get(0), MaterialType.PRODUCTION
        );
        Set<ProductStructure<ProductStructureNode>> spareStructures = BomHelper.createProductStructuresAndMaterialType(spares.size(),
                spares, MaterialType.WORKING_IN_PROGRESS
        );

        Set<ProductStructure<ProductStructureNode>> rawStructures = BomHelper.createProductStructuresAndMaterialType(rawMaterials.size(),
                rawMaterials, MaterialType.RAW_MATERIAL
        );
        for (int i = 0 ; i< spareSize; i++) {
            ProductStructure<ProductStructureNode> spare = CollectionUtils.random(new ArrayList<>(spareStructures));
            for (int j = 0 ; j < rawSize; j++) {
                spare.addStructure(CollectionUtils.random(new ArrayList<>(rawStructures)));
            }
            productStructure.addStructure(spare);
        }
        List<MaterialIdNode<ProductStructureNode>> list = productStructure.toMaterialIdList();
        List<ProductStructureData> structureDataList = list.stream().map(v->ProductStructureData.create(v)).collect(Collectors.toList());
        structureDataRepository.persistAll(structureDataList);
    }
}
