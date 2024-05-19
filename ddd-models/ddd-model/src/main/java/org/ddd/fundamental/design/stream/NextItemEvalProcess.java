package org.ddd.fundamental.design.stream;

public class NextItemEvalProcess {
    /**
     * 求值方法
     * */
    private EvalFunction evalFunction;

    public NextItemEvalProcess(EvalFunction evalFunction) {
        this.evalFunction = evalFunction;
    }

    MyStream eval(){
        return evalFunction.apply();
    }
}
