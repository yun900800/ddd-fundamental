package org.ddd.fundamental.material;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

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

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }
}
