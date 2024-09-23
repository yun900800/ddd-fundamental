package org.ddd.fundamental.tamagotchi.repository;

import org.ddd.fundamental.configuration.DataSourceConfiguration;
import org.ddd.fundamental.tamagotchi.domain.Pocket;
import org.ddd.fundamental.tamagotchi.dto.PocketDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@DataJpaTest(
        properties = {
                "spring.datasource.url=jdbc:h2:mem:test5;MODE=Oracle;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
                "spring.jpa.hibernate.ddl-auto=create-drop"
        }
)
@AutoConfigureTestDatabase(replace = NONE)
@RunWith(SpringRunner.class)
@Transactional
public class PocketRepositoryTest {

    @Autowired
    private PocketRepository pocketRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testSavePocket() {
        Pocket pocket = Pocket.newPocket("myCat");
        System.out.println("pocket id:"+pocket.toDto().getId().getId());
        System.out.println("Tamagotchi id:"+pocket.toDto().getTamagotchis().get(0).getId());
        //这里因为存在外键,所以查询子表的时候会报实体不存在，因此需要在主表中增加一个忽略异常的注解
        pocketRepository.save(pocket);
        System.out.println("save success");
        Pocket.ID id = pocket.getId();
        //这里查询出来的类表会有一个bag的数据是因为Pocket包的cascade = PERSIST, 修改成ALL以后就好了
        Pocket pocket1 = pocketRepository.findById(id).get();
        PocketDto pocketDto = pocket1.toDto();
        Assert.assertEquals(pocketDto.getName(),"myCat");
    }

}
