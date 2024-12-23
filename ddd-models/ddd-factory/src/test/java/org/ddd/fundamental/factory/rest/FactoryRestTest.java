package org.ddd.fundamental.factory.rest;

import org.ddd.fundamental.factory.FactoryAppTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class FactoryRestTest extends FactoryAppTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void testMachineShops() throws Exception {
        mvc.perform(get("/factory/machine-shops")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(11))))
                .andExpect(jsonPath("$[0].machineShopValue.machineShop.name", is("电路板三车间")));
    }
}
