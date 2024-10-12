package org.ddd.fundamental.generics.bounds;

import java.util.List;

interface SuperPower {}

interface XRayVision extends SuperPower {
    void seeThroughWalls();
}
interface SuperHearing extends SuperPower {
    void hearSubtleNoises();
}
interface SuperSmell extends SuperPower {
    void trackBySmell();
}

/**
 * 在类继承上进行限制泛型参数的范围
 * @param <POWER>
 */
class SuperHero<POWER extends SuperPower> {
    POWER power;
    SuperHero(POWER power) { this.power = power; }
    POWER getPower() { return power; }
}

/**
 * 在类继承上进行限制泛型参数的范围
 * @param <POWER>
 */
class SuperSleuth<POWER extends XRayVision>
        extends SuperHero<POWER> {
    SuperSleuth(POWER power) { super(power); }
    void see() { power.seeThroughWalls(); }
}
class CanineHero<POWER extends SuperHearing & SuperSmell>
        extends SuperHero<POWER> {
    CanineHero(POWER power) { super(power); }
    void hear() { power.hearSubtleNoises(); }
    void smell() { power.trackBySmell(); }
}

/**
 * 上面的是泛型参数的定义,下面的是泛型参数的具体使用
 */
class SuperHearSmell implements SuperHearing, SuperSmell {
    public void hearSubtleNoises() {}
    public void trackBySmell() {}
}
class DogBoy extends CanineHero<SuperHearSmell> {
    DogBoy() { super(new SuperHearSmell()); }
}
public class EpicBattle {
    // Bounds in generic methods:
    // 以下两个方法是在方法上对泛型参数进行限制,
    //第一个参数一定要实现SuperHearing接口
    static <POWER extends SuperHearing>
    void useSuperHearing(SuperHero<POWER> hero) {
        hero.getPower().hearSubtleNoises();
    }
    //第一个参数一定要实现SuperHearing和SuperSmell接口
    static <POWER extends SuperHearing & SuperSmell>
    void superFind(SuperHero<POWER> hero) {
        hero.getPower().hearSubtleNoises();
        hero.getPower().trackBySmell();
    }
    public static void main(String[] args) {
        DogBoy dogBoy = new DogBoy();
        useSuperHearing(dogBoy);
        superFind(dogBoy);
        // You can do this:
        List<? extends SuperHearing> audioBoys;
    }
}
