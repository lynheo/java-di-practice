package lyn.lunapiece.dipratice.testclass.constructor;

import lyn.lunapiece.dipratice.engine.annotation.LynComponent;
import lyn.lunapiece.dipratice.engine.annotation.LynQualified;
import lyn.lunapiece.dipratice.testclass.simpletest.SampleDITargetClass;

@LynComponent
public class ConstructorTestClass {

    private final ConstructorTargetBaseClass constructorTargetBaseClass1;
    private final ConstructorTargetBaseClass constructorTargetBaseClass2;
    private final SampleDITargetClass sampleDITargetClass;

    public ConstructorTestClass(
            @LynQualified(beanName = "c1") ConstructorTargetBaseClass constructorTargetBaseClass1,
            @LynQualified(beanName = "c2") ConstructorTargetBaseClass constructorTargetBaseClass2,
            SampleDITargetClass sampleDITargetClass) {

        this.constructorTargetBaseClass1 = constructorTargetBaseClass1;
        this.constructorTargetBaseClass2 = constructorTargetBaseClass2;
        this.sampleDITargetClass = sampleDITargetClass;
    }
}
