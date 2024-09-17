package org.ddd.fundamental.domain.wokprocess.impl;

import org.ddd.fundamental.domain.wokprocess.WorkProcess;

public class MoldStickingProcess extends WorkProcess<String,String> {
    @Override
    public String process(String s) {
        return null;
    }

    @Override
    public String getName() {
        return MoldStickingProcess.class.getSimpleName();
    }
}
