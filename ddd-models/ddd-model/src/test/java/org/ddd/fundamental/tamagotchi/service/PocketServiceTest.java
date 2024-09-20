package org.ddd.fundamental.tamagotchi.service;

import com.mysql.cj.jdbc.MysqlXADataSource;
import org.ddd.fundamental.tamagotchi.domain.Pocket;
import org.ddd.fundamental.tamagotchi.dto.PocketDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import javax.persistence.EntityManager;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PocketServiceTest {

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private EntityManager em;
    @Autowired
    private PocketService pocketService;



    @Test
    public void shouldCreateNewPocket() {
        UUID pocketId = pocketService.createPocket("New pocket");
        PocketDto pocketDto = transactionTemplate.execute(
                s -> em.find(Pocket.class, pocketId).toDto()
        );
        assertEquals("New pocket",pocketDto.getName());
        assertEquals("Default",pocketDto.getTamagotchis().get(0).getName());
    }



}
