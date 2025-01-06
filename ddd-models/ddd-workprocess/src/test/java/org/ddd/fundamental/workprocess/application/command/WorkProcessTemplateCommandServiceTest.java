package org.ddd.fundamental.workprocess.application.command;

import org.ddd.fundamental.shared.api.optemplate.WorkProcessTemplateDTO;
import org.ddd.fundamental.utils.CollectionUtils;
import org.ddd.fundamental.workprocess.WorkProcessAppTest;
import org.ddd.fundamental.workprocess.application.query.WorkProcessTemplateQueryService;
import org.ddd.fundamental.workprocess.domain.model.WorkProcessTemplate;
import org.ddd.fundamental.workprocess.value.WorkProcessBeat;
import org.ddd.fundamental.workprocess.value.WorkProcessTemplateId;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class WorkProcessTemplateCommandServiceTest extends WorkProcessAppTest {

    @Autowired
    private WorkProcessTemplateCommandService service;

    @Autowired
    private WorkProcessTemplateQueryService application;

    private List<WorkProcessTemplateDTO> templateDTOList;

    @Before
    public void init(){
        this.templateDTOList = application.workProcessTemplates();
    }

    @Test
    public void testChangeWorkProcessBeat(){
        WorkProcessTemplateId id = CollectionUtils.random(this.templateDTOList).id();
        WorkProcessBeat beat = WorkProcessBeat.create(10000,40);
        service.changeWorkProcessBeat(beat,id);
        WorkProcessTemplate queryTemplate = application.get(id);
        Assert.assertEquals(beat,queryTemplate.getWorkProcessBeat());
    }

    @After
    public void destroy(){
        this.templateDTOList = null;
    }
}
