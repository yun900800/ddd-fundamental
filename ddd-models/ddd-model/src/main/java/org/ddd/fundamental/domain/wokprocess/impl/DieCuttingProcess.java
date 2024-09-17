package org.ddd.fundamental.domain.wokprocess.impl;

import org.ddd.fundamental.domain.wokprocess.WorkProcess;

public class DieCuttingProcess extends WorkProcess<Integer, Integer> {

    @Override
    public String getName() {
        return DieCuttingProcess.class.getSimpleName();
    }

    public Integer process(Integer data){
        return 0;
    }
}
