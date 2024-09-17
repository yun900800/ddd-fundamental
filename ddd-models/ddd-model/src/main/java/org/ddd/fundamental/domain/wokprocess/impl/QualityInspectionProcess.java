package org.ddd.fundamental.domain.wokprocess.impl;

import org.ddd.fundamental.domain.wokprocess.WorkProcess;

public class QualityInspectionProcess extends WorkProcess<Boolean,Boolean> {
    @Override
    public Boolean process(Boolean aBoolean) {
        return null;
    }

    @Override
    public String getName() {
        return QualityInspectionProcess.class.getSimpleName();
    }
}
