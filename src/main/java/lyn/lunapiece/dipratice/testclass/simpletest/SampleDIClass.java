package lyn.lunapiece.dipratice.testclass.simpletest;

import lombok.Getter;
import lyn.lunapiece.dipratice.engine.annotation.LynAutowired;
import lyn.lunapiece.dipratice.engine.annotation.LynComponent;

@LynComponent
@Getter
public class SampleDIClass {
    @LynAutowired
    private SampleDITargetClass sampleDITargetClass;
}
