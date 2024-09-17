package org.ddd.fundamental.domain.wokprocess.impl;

import org.ddd.fundamental.domain.wokprocess.WorkProcess;

public class PilingProcess extends WorkProcess<Double,Double> {
    @Override
    public Double process(Double aDouble) {
        return null;
    }

    @Override
    public String getName() {
        return PilingProcess.class.getSimpleName();
    }
}
