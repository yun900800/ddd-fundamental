package org.ddd.fundamental.generics.why;


class Automobile{}
public class Holders1 {
    private Automobile autoMobile;
    public Holders1(Automobile autoMobile){
        this.autoMobile = autoMobile;
    }

    public Automobile get(){
        return autoMobile;
    }
}
