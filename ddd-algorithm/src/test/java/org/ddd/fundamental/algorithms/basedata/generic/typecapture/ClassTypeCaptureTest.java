package org.ddd.fundamental.algorithms.basedata.generic.typecapture;

import org.ddd.fundamental.algorithm.basedata.generic.typecapture.ClassTypeCapture;
import org.junit.Test;

public class ClassTypeCaptureTest {

    @Test
    public void testTypeCheck() {
        ClassTypeCapture<Building> ctt1 = new ClassTypeCapture<>(Building.class);
        System.out.println(ctt1.typeCheck(new Building()));
        System.out.println(ctt1.typeCheck(new House()));
        ClassTypeCapture<House> ctt2 = new ClassTypeCapture<>(House.class);
        System.out.println(ctt2.typeCheck(new Building()));
        System.out.println(ctt2.typeCheck(new House()));
    }
}

class Building {}
class House extends Building {}
