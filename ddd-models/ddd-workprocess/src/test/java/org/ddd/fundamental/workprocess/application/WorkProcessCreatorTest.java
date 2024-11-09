package org.ddd.fundamental.workprocess.application;

import lombok.extern.slf4j.Slf4j;
import org.ddd.fundamental.changeable.ChangeableInfo;
import org.ddd.fundamental.workprocess.value.AuxiliaryWorkTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

@Slf4j
public class WorkProcessCreatorTest {

    @Test
    public void testCreateAuxiliaryWorkTimes(){
        List<AuxiliaryWorkTime> auxiliaryWorkTimeList = WorkProcessCreator.createAuxiliaryWorkTimes();
        log.info(auxiliaryWorkTimeList.toString());
        Assert.assertEquals(auxiliaryWorkTimeList.size(),16);
    }

    @Test
    public void testCreateWorkProcessInfo(){
        List<ChangeableInfo> processInfo = WorkProcessCreator.createWorkProcessInfo();
        log.info(processInfo.toString());
        Assert.assertEquals(processInfo.size(),14);
    }
}
