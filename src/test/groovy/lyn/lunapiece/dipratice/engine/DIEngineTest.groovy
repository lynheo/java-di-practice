package lyn.lunapiece.dipratice.engine

import lyn.lunapiece.dipratice.testclass.qualified.QualifiedTargetOne
import lyn.lunapiece.dipratice.testclass.qualified.QualifiedTargetTwo
import lyn.lunapiece.dipratice.testclass.qualified.QualifiedTestClass
import lyn.lunapiece.dipratice.testclass.simpletest.SampleDIClass
import spock.lang.Specification

class DIEngineTest extends Specification {
    static DIEngine diEngine

    void setupSpec() {
        diEngine = new DIEngine()
        diEngine.scanComponents("lyn.lunapiece.dipratice")
    }

    def "Simple Component Scan & Autowired"() {
        when:
        var result = diEngine.createInstance(SampleDIClass.class)

        then:
        result.sampleDITargetClass != null
    }

    def "Qualified Autowired"() {
        when:
        var result = diEngine.createInstance(QualifiedTestClass.class)

        then:
        result.qualifiedTargetBaseClass1 instanceof QualifiedTargetOne
        result.qualifiedTargetBaseClass2 instanceof QualifiedTargetTwo
    }
}
