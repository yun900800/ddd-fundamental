package org.ddd.fundamental.algorithm.basedata.random;

@FunctionalInterface
public interface Procedure {
    void run();

    default Procedure andThen(Procedure after){
        return ()->{
            this.run();
            after.run();
        };
    }

    default Procedure compose(Procedure before){
        return ()->{
            before.run();
            this.run();
        };
    }
}
