package org.ddd.fundamental.material.rest;

import org.ddd.fundamental.material.MaterialAppTest;
import org.ddd.fundamental.material.creator.MaterialAddable;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class MaterialRestTest extends MaterialAppTest {



    @Autowired
    private MockMvc mvc;

    @Test
    public void testMaterials() throws Exception {
        mvc.perform(get("/material/materials")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(11))));
//                .andExpect(jsonPath("$[0].machineShopValue.machineShop.name", is("电路板三车间")));
    }
}
