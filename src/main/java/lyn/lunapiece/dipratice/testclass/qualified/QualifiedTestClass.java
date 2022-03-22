package lyn.lunapiece.dipratice.testclass.qualified;


import lombok.Getter;
import lyn.lunapiece.dipratice.engine.annotation.LynAutowired;
import lyn.lunapiece.dipratice.engine.annotation.LynComponent;
import lyn.lunapiece.dipratice.engine.annotation.LynQualified;

@LynComponent
@Getter
public class QualifiedTestClass {

    @LynAutowired
    @LynQualified(beanName = "q1")
    private QualifiedTargetBaseClass qualifiedTargetBaseClass1;

    @LynAutowired
    @LynQualified(beanName = "q2")
    private QualifiedTargetBaseClass qualifiedTargetBaseClass2;
}
