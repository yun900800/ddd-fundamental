package org.ddd.fundamental.material;


import org.ddd.fundamental.material.creator.MaterialAddable;
import org.ddd.fundamental.redis.config.RedisStoreManager;
import org.ddd.fundamental.shared.api.material.MaterialDTO;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Unit test for simple App.
 */
@SpringBootTest(
        classes = MaterialApp.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
//一下这个注解是测试的时候自动载入h2数据库
@AutoConfigureTestDatabase
@RunWith(SpringRunner.class)
public class MaterialAppTest {

    @MockBean
    @Qualifier(value = "newRedisTemplate")
    private RedisTemplate<String,Object> newRedisTemplate;

    @MockBean
    private RedisStoreManager manager;
//    @MockBean
//    private MaterialAddable creator;

    @BeforeEach
    public void init(){
        when(manager.queryAllData(MaterialDTO.class)).thenReturn(new ArrayList<>());
//        when(creator.getMaterialList()).thenReturn(new ArrayList<>());
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }
}
