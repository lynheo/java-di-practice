package lyn.lunapiece.dipratice;

import lyn.lunapiece.dipratice.engine.DIEngine;
import lyn.lunapiece.dipratice.testclass.SampleDIClass;

public class Main {
    public static void main(String[] args) {
        var diEngine = new DIEngine();
        diEngine.scanComponents("lyn.lunapiece.dipratice");

        var result = diEngine.createInstance(SampleDIClass.class);
        if (result.getSampleDITargetClass() != null) {
            System.out.println("DI Success");
        } else {
            System.out.println("DI Failed");
        }
    }
}
