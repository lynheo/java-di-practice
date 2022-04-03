package lyn.lunapiece.dipratice.testclass.constructor;

import lyn.lunapiece.dipratice.engine.annotation.LynAutowired;
import lyn.lunapiece.dipratice.engine.annotation.LynComponent;
import lyn.lunapiece.dipratice.testclass.simpletest.SampleDITargetClass;

@LynComponent(beanName = "c1")
public class ConstructorTargetOne extends ConstructorTargetBaseClass {
    @LynAutowired
    private SampleDITargetClass sampleDITargetClass;
}
