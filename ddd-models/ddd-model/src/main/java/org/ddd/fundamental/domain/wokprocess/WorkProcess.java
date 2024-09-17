package org.ddd.fundamental.domain.wokprocess;

public abstract class WorkProcess<Input,OutPut> {

    public abstract String getName();

    public abstract OutPut process(Input input);

}
