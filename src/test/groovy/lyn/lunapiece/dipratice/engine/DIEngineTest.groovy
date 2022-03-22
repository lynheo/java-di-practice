package lyn.lunapiece.dipratice.engine

import lyn.lunapiece.dipratice.testclass.SampleDIClass
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
}
