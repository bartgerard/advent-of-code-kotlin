package aock2024


import spock.lang.Specification

import static shared.InputsKt.readFile

class Year2024Day23Specification extends Specification {

    def "partOne"() {
        when:
        final long result = new Year2024Day23(input).partOne()

        then:
        result == expectedResult

        where:
        input                           | expectedResult | comment
        readFile(2024, 23, "example_1") | 7              | ""

        readFile(2024, 23)              | 1110           | ""
    }

    def "partTwo"() {
        when:
        final String result = new Year2024Day23(input).partTwo()

        then:
        result == expectedResult

        where:
        input                           | expectedResult                           | comment
        readFile(2024, 23, "example_1") | "co,de,ka,ta"                            | ""

        readFile(2024, 23)              | "ej,hm,ks,ms,ns,rb,rq,sc,so,un,vb,vd,wd" | ""
    }

}
