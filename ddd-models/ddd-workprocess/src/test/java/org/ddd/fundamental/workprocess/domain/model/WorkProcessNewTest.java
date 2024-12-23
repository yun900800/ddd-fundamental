package org.ddd.fundamental.workprocess.domain.model;

import org.ddd.fundamental.changeable.ChangeableInfo;

import org.ddd.fundamental.workprocess.creator.WorkProcessTemplateAddable;
import org.ddd.fundamental.workprocess.value.WorkProcessBeat;
import org.junit.Test;

public class WorkProcessNewTest {

    @Test
    public void testCreateWorkProcessTemplate(){

        WorkProcessTemplate workProcessTemplate = new WorkProcessTemplate(
                ChangeableInfo.create("主板加工工序","这是用来加工新能源车的主板的工序"),
//                create(),
                WorkProcessBeat.create(1000,15),
//                WorkProcessTemplateAddable.createWorkProcessTemplateControl(),
                WorkProcessTemplateAddable.createWorkProcessTemplateQuantity()
        );
        System.out.println(workProcessTemplate);
    }
}

