package org.ddd.fundamental.workprocess;

public interface IWorkProcessHandler {

    void input(ProcessInput input);

    default ProcessOutput handle(ProcessInput input){
        input(input);
        return output();
    }

    ProcessOutput output();
}
