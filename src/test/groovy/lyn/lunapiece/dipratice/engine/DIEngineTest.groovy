package lyn.lunapiece.dipratice.engine

import lyn.lunapiece.dipratice.testclass.constructor.ConstructorTargetOne
import lyn.lunapiece.dipratice.testclass.constructor.ConstructorTargetTwo
import lyn.lunapiece.dipratice.testclass.constructor.ConstructorTestClass
import lyn.lunapiece.dipratice.testclass.exceptioncase.NormalClass
import lyn.lunapiece.dipratice.testclass.qualified.QualifiedTargetOne
import lyn.lunapiece.dipratice.testclass.qualified.QualifiedTargetTwo
import lyn.lunapiece.dipratice.testclass.qualified.QualifiedTestClass
import lyn.lunapiece.dipratice.testclass.simpletest.SampleDIClass
import lyn.lunapiece.dipratice.testclass.simpletest.SampleDITargetClass
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

    def "Qualified With Constructor"() {
        when:
        var result = diEngine.createInstance(ConstructorTestClass.class)

        then:
        result.constructorTargetBaseClass1 instanceof ConstructorTargetOne
        result.constructorTargetBaseClass2 instanceof ConstructorTargetTwo
        result.sampleDITargetClass instanceof SampleDITargetClass

        ((ConstructorTargetOne) result.constructorTargetBaseClass1).sampleDITargetClass instanceof SampleDITargetClass
    }

    def "Can not create without DIEngine component"() {
        when:
        diEngine.createInstance(NormalClass.class)

        then:
        thrown(Exception.class)
    }
}
