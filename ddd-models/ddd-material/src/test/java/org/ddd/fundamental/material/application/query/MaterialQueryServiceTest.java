package org.ddd.fundamental.material.application.query;

import com.blazebit.persistence.PagedList;
import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.material.MaterialAppTest;
import org.ddd.fundamental.material.creator.MaterialAddable;
import org.ddd.fundamental.material.domain.model.Material;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.Assert.assertEquals;

@Slf4j
public class MaterialQueryServiceTest extends MaterialAppTest {

    @Autowired
    private MaterialQueryService queryService;

    private static final int PAGE_SIZE = 8;

    @Test
    public void testKeySetPage(){
        PagedList<Material> topPage = queryService.firstLatestPosts(PAGE_SIZE);
        assertEquals(50, topPage.getTotalSize());

        assertEquals(7, topPage.getTotalPages());

        assertEquals(1, topPage.getPage());

        PagedList<Material> nextPage = queryService.findNextLatestPosts(topPage);

        assertEquals(2, nextPage.getPage());
    }
}
