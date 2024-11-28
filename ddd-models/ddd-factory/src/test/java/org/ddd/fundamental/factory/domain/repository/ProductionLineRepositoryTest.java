package org.ddd.fundamental.factory.domain.repository;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.factory.EquipmentId;
import org.ddd.fundamental.factory.FactoryAppTest;
import org.ddd.fundamental.factory.ProductionLineId;
import org.ddd.fundamental.factory.domain.model.ProductionLine;
import org.ddd.fundamental.factory.domain.model.WorkStation;
import org.ddd.fundamental.factory.value.ProductionLineValue;
import org.ddd.fundamental.factory.value.WorkStationValueObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class ProductionLineRepositoryTest extends FactoryAppTest {

    @Autowired
    private ProductionLineRepository repository;

//    Hibernate:
//    select
//    production0_.id as id1_0_1_,
//    production0_.product_line_desc as product_2_0_1_,
//    production0_.product_line_is_use as product_3_0_1_,
//    production0_.product_line_name as product_4_0_1_,
//    workstatio1_.line_id as line_id5_1_3_,
//    workstatio1_.id as id1_1_3_,
//    workstatio1_.id as id1_1_0_,
//    workstatio1_.work_station_desc as work_sta2_1_0_,
//    workstatio1_.work_station_is_use as work_sta3_1_0_,
//    workstatio1_.work_station_name as work_sta4_1_0_
//            from
//    f_production_line production0_
//    left outer join
//    f_work_station workstatio1_
//    on production0_.id=workstatio1_.line_id
//            where
//    production0_.id=?
//    Hibernate:
//    select
//    workstatio0_.id as id1_1_0_,
//    workstatio0_.work_station_desc as work_sta2_1_0_,
//    workstatio0_.work_station_is_use as work_sta3_1_0_,
//    workstatio0_.work_station_name as work_sta4_1_0_
//            from
//    f_work_station workstatio0_
//    where
//    workstatio0_.id=?
//    Hibernate:
//    select
//    workstatio0_.id as id1_1_0_,
//    workstatio0_.work_station_desc as work_sta2_1_0_,
//    workstatio0_.work_station_is_use as work_sta3_1_0_,
//    workstatio0_.work_station_name as work_sta4_1_0_
//            from
//    f_work_station workstatio0_
//    where
//    workstatio0_.id=?
//    Hibernate:
//    select
//    workstatio0_.id as id1_1_0_,
//    workstatio0_.work_station_desc as work_sta2_1_0_,
//    workstatio0_.work_station_is_use as work_sta3_1_0_,
//    workstatio0_.work_station_name as work_sta4_1_0_
//            from
//    f_work_station workstatio0_
//    where
//    workstatio0_.id=?
//    Hibernate:
//    select
//    workstatio0_.id as id1_1_0_,
//    workstatio0_.work_station_desc as work_sta2_1_0_,
//    workstatio0_.work_station_is_use as work_sta3_1_0_,
//    workstatio0_.work_station_name as work_sta4_1_0_
//            from
//    f_work_station workstatio0_
//    where
//    workstatio0_.id=?
//    Hibernate:
//    insert
//            into
//    f_production_line
//            (product_line_desc, product_line_is_use, product_line_name, id)
//    values
//            (?, ?, ?, ?)
//    Hibernate:
//    insert
//            into
//    f_work_station
//            (work_station_desc, work_station_is_use, work_station_name, line_id, id)
//    values
//            (?, ?, ?, ?, ?)
//    Hibernate:
//    insert
//            into
//    f_work_station
//            (work_station_desc, work_station_is_use, work_station_name, line_id, id)
//    values
//            (?, ?, ?, ?, ?)
//    Hibernate:
//    insert
//            into
//    f_work_station
//            (work_station_desc, work_station_is_use, work_station_name, line_id, id)
//    values
//            (?, ?, ?, ?, ?)
//    Hibernate:
//    insert
//            into
//    f_work_station
//            (work_station_desc, work_station_is_use, work_station_name, line_id, id)
//    values
//            (?, ?, ?, ?, ?)
//    Hibernate:
//    update
//            f_work_station
//    set
//    line_id=?
//    where
//    id=?
//    Hibernate:
//    update
//            f_work_station
//    set
//    line_id=?
//    where
//    id=?
//    Hibernate:
//    update
//            f_work_station
//    set
//    line_id=?
//    where
//    id=?
//    Hibernate:
//    update
//            f_work_station
//    set
//    line_id=?
//    where
//    id=?

    private ProductionLine initProductLine(){
        ProductionLine productionLine = new ProductionLine(new ProductionLineValue(
                ChangeableInfo.create("电路板产线1", "这是一个生产电路板的产线")
        ));

        productionLine.addWorkStation(new WorkStation(
                new WorkStationValueObject(ChangeableInfo.create(
                        "电路板产线1工位1", "工位1需要一点点技术哈"
                ))
        )).addWorkStation(
                new WorkStation(
                        new WorkStationValueObject(ChangeableInfo.create(
                                "电路板产线1工位2", "工位2需要一点点耐心"
                        ))
                )
        ).addWorkStation(
                new WorkStation(
                        new WorkStationValueObject(ChangeableInfo.create(
                                "电路板产线1工位3", "工位3需要一点点责任心"
                        ))
                )
        ).addWorkStation(
                new WorkStation(
                        new WorkStationValueObject(ChangeableInfo.create(
                                "电路板产线1工位4", "工位4需要一点点细心"
                        ))
                )
        );

        return productionLine;
    }
    //以上方式不是效率最高的一种方式, 因为数据量不大,所以可以使用
    // 这里提出一个例子, 使用批量数据来测试一下高效与低效之间的差别
    @Test
    public void createProductLine(){

        ProductionLine productionLine = initProductLine();
        log.info("start save productLine");
        repository.save(productionLine);
        log.info("end save productLine");
        log.info("start execute  a query");
        ProductionLineId id = productionLine.id();
        ProductionLine line = repository.findById(id).get();
        log.info("finish execute  a query");
        line.addWorkStation(new WorkStation(
                new WorkStationValueObject(ChangeableInfo.create(
                        "电路板产线1工位5", "工位5需要一点点细心"
                ))
        ));
        repository.save(line);
        Assert.assertEquals(line.getLine().name(),"电路板产线1");
        //注意，如果不查询子side,则不会执行原来的查询
        //Assert.assertEquals(line.getWorkStations().get(0).getWorkStation().name(),"电路板产线1工位3");

    }

//    select
//    production0_.id as id1_0_0_,
//    production0_.product_line_desc as product_2_0_0_,
//    production0_.product_line_is_use as product_3_0_0_,
//    production0_.product_line_name as product_4_0_0_,
//    workstatio1_.line_id as line_id5_1_1_,
//    workstatio1_.id as id1_1_1_,
//    workstatio1_.id as id1_1_2_,
//    workstatio1_.work_station_desc as work_sta2_1_2_,
//    workstatio1_.work_station_is_use as work_sta3_1_2_,
//    workstatio1_.work_station_name as work_sta4_1_2_
//            from
//    f_production_line production0_
//    left outer join
//    f_work_station workstatio1_
//    on production0_.id=workstatio1_.line_id
//            where
//    production0_.id=?
    @Test
    public void queryProductLine() {
        ProductionLineId id = new ProductionLineId("2e82f7bd-8fbf-481b-ab1f-7318ffd9e4ba");
        ProductionLine line = repository.findById(id).get();
        Assert.assertEquals(line.getLine().name(),"电路板产线1");
        Assert.assertEquals(line.getLine().desc(),"这是一个生产电路板的产线");

        Assert.assertEquals(line.getWorkStations().size(),4);
    }

    @Test
    public void testAddEquipmentIds(){
        ProductionLineId id = new ProductionLineId("2e82f7bd-8fbf-481b-ab1f-7318ffd9e4ba");
        ProductionLine line = repository.findById(id).get();
        line.addEquipment(EquipmentId.randomId(EquipmentId.class));
        line.addEquipment(EquipmentId.randomId(EquipmentId.class));
        repository.save(line);
    }

    /**
     * 注意比较这个例子和下一个例子的区别
     */
    @Test
    public void changeLineInfo() {
        ProductionLine line = initProductLine();
        repository.save(line);
        ProductionLineId id = line.id();
        ProductionLine queryLine = repository.findById(id).orElse(null);
        queryLine.changeName("修改测试产线");
        queryLine.changeDesc("修改描述");
        queryLine.enableLine();
        repository.save(queryLine);
        //这里存在延迟加载所以不会更新工位信息
        Assert.assertEquals(queryLine.getLine().name(),"修改测试产线");
        Assert.assertEquals(queryLine.getLine().desc(),"修改描述");
        Assert.assertEquals(queryLine.getLine().isUse(),true);
    }

    @Test
    public void changeLineInfoNew() {
        ProductionLine line = initProductLine();
        repository.save(line);
        ProductionLineId id = line.id();
        line.changeName("修改测试产线");
        line.changeDesc("修改描述");
        line.enableLine();
        //line.setNew(false);
        repository.save(line);
        //注意这里实际上是更新原来插入的四条工位信息
        ProductionLine queryLine = repository.findById(id).orElse(null);
        Assert.assertEquals(queryLine.getLine().name(),"修改测试产线");
        Assert.assertEquals(queryLine.getLine().desc(),"修改描述");
        Assert.assertEquals(queryLine.getLine().isUse(),true);
    }
}
