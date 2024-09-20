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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.UUID;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED;


@DataJpaTest(
        properties = {
                "spring.datasource.url=jdbc:h2:mem:testdb",
                "spring.jpa.hibernate.ddl-auto=create-drop"
        },
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = PocketRepository.class)
)
@AutoConfigureTestDatabase(replace = AUTO_CONFIGURED)
@RunWith(SpringRunner.class)
public class PocketRepositoryTest {

    @Autowired
    private PocketRepository pocketRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testSavePocket() {
        Pocket pocket = Pocket.newPocket("myCat");
        pocketRepository.save(pocket);
        UUID id = pocket.getId();
        Pocket pocket1 = entityManager.find(Pocket.class,id);
        PocketDto pocketDto = pocket1.toDto();
        Assert.assertEquals(pocketDto.getName(),"myCat");
    }

}
