package shared


import spock.lang.Specification

import static java.util.Objects.isNull
import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.Assertions.withPrecision

class Geometry2dSpecification extends Specification {

    def "intersect lines"() {
        given:
        final LineSegment2d l1 = new LineSegment2d(new Point2d(l1p1), new Point2d(l1p2))
        final LineSegment2d l2 = new LineSegment2d(new Point2d(l2p1), new Point2d(l2p2))

        when:
        final Point2d result = l1.intersect(l2)

        then:
        final Point2d expectedPoint = isNull(expectedResult) ? null : new Point2d(expectedResult)
        result == expectedPoint

        where:
        l1p1    | l1p2   | l2p1   | l2p2    || expectedResult | comment
        [0, 0]  | [1, 0] | [0, 1] | [1, 1]  || null           | "parallel"
        [0, 0]  | [1, 0] | [0, 0] | [1, 0]  || null           | "coinciding"

        [0, 0]  | [1, 0] | [0, 1] | [1, 0]  || [1, 0]         | ""
        [0, 0]  | [2, 2] | [0, 2] | [2, 0]  || [1, 1]         | ""
        [0, 0]  | [1, 1] | [0, 1] | [1, -1] || [0, 0]         | ""
        [-1, 0] | [1, 0] | [0, 1] | [0, -1] || [0, 0]         | ""

        [1, 0]  | [1, 1] | [0, 1] | [1, 1]  || [1, 1]         | ""
        [0, 0]  | [1, 1] | [0, 1] | [1, 1]  || [1, 1]         | ""

        [2, 0]  | [2, 1] | [0, 2] | [1, 2]  || [2, 2]         | ""
        [0, 0]  | [1, 1] | [0, 2] | [1, 2]  || [2, 2]         | ""
    }

    def "intersect lines within segment"() {
        given:
        final LineSegment2d l1 = new LineSegment2d(new Point2d(l1p1), new Point2d(l1p2))
        final LineSegment2d l2 = new LineSegment2d(new Point2d(l2p1), new Point2d(l2p2))

        when:
        final Point2d result = l1.intersectWithinSegment(l2)

        then:
        final Point2d expectedPoint = isNull(expectedResult) ? null : new Point2d(expectedResult)
        result == expectedPoint

        where:
        l1p1    | l1p2   | l2p1   | l2p2    || expectedResult | comment
        [0, 0]  | [1, 0] | [0, 1] | [1, 1]  || null           | "parallel"
        [0, 0]  | [1, 0] | [0, 0] | [1, 0]  || null           | "coinciding"

        [0, 0]  | [1, 0] | [0, 1] | [1, 0]  || [1, 0]         | ""
        [0, 0]  | [2, 2] | [0, 2] | [2, 0]  || [1, 1]         | ""
        [0, 0]  | [1, 1] | [0, 1] | [1, -1] || [0, 0]         | ""
        [-1, 0] | [1, 0] | [0, 1] | [0, -1] || [0, 0]         | ""

        [1, 0]  | [1, 1] | [0, 1] | [1, 1]  || [1, 1]         | ""
        [0, 0]  | [1, 1] | [0, 1] | [1, 1]  || [1, 1]         | ""

        [2, 0]  | [2, 1] | [0, 2] | [1, 2]  || null           | ""
        [0, 0]  | [1, 1] | [0, 2] | [1, 2]  || null           | ""
    }

    def "angle to"() {
        when:
        final Angle angle = new Vector2d(v1).angleTo(new Vector2d(v2))

        then:
        assertThat(angle.radians).isCloseTo(expectedRadians as Double, withPrecision(0.0000001d))
        assertThat(angle.degrees).isCloseTo(expectedDegrees, withPrecision(0.1d))

        where:
        v1     | v2      || expectedRadians | expectedDegrees | comment
        [0, 1] | [0, 1]  || 0.0d            | 0.0d            | ""
        [0, 1] | [1, 0]  || Math.PI / 2     | 90.0d           | ""
        [0, 1] | [-1, 0] || Math.PI / 2     | 90.0d           | ""
        [0, 1] | [0, -1] || Math.PI         | 180.0d          | ""

        [1, 0] | [0, 1]  || Math.PI / 2     | 90.0d           | ""

        [0, 1] | [1, 1]  || Math.PI / 4     | 45.0d           | ""
        [0, 1] | [1, -1] || Math.PI * 3 / 4 | 135.0d          | ""
    }

    def "signed angle to"() {
        when:
        final Angle angle = new Vector2d(v1).signedAnleTo(new Vector2d(v2))

        then:
        assertThat(angle.radians).isCloseTo(expectedRadians as Double, withPrecision(0.0000001d))
        assertThat(angle.degrees).isCloseTo(expectedDegrees, withPrecision(0.1d))

        where:
        v1     | v2      || expectedRadians  | expectedDegrees | comment
        [0, 1] | [0, 1]  || 0.0d             | 0.0d            | ""
        [0, 1] | [1, 0]  || -Math.PI / 2     | -90.0d          | ""
        [0, 1] | [-1, 0] || Math.PI / 2      | 90.0d           | ""
        [0, 1] | [0, -1] || Math.PI          | 180.0d          | ""

        [1, 0] | [0, 1]  || Math.PI / 2      | 90.0d           | ""

        [0, 1] | [1, 1]  || -Math.PI / 4     | -45.0d          | ""
        [0, 1] | [1, -1] || -Math.PI * 3 / 4 | -135.0d         | ""
    }

    def "to degrees 360"() {
        when:
        final Angle angle = new Angle(radians)

        then:
        assertThat(angle.toDegrees360(turn)).isCloseTo(expectedResult, withPrecision(0.01d))

        where:
        radians         | turn                   || expectedResult | comment
        Math.PI / 4     | Turn.COUNTER_CLOCKWISE || 45.0d          | ""
        Math.PI / 4     | Turn.CLOCKWISE         || 315.0d         | ""
        Math.PI / 2     | Turn.COUNTER_CLOCKWISE || 90.0d          | ""
        Math.PI / 2     | Turn.CLOCKWISE         || 270.0d         | ""
        Math.PI * 3 / 4 | Turn.COUNTER_CLOCKWISE || 135.0d         | ""
        Math.PI * 3 / 4 | Turn.CLOCKWISE         || 225.0d         | ""
        Math.PI         | Turn.COUNTER_CLOCKWISE || 180.0d         | ""
        Math.PI         | Turn.CLOCKWISE         || 180.0d         | ""
        2 * Math.PI     | Turn.COUNTER_CLOCKWISE || 0.0d           | ""
        2 * Math.PI     | Turn.CLOCKWISE         || 0.0d           | ""
        3 * Math.PI     | Turn.COUNTER_CLOCKWISE || 180.0d         | ""
        3 * Math.PI     | Turn.CLOCKWISE         || 180.0d         | ""
        4 * Math.PI     | Turn.COUNTER_CLOCKWISE || 0.0d           | ""
        4 * Math.PI     | Turn.CLOCKWISE         || 0.0d           | ""
    }

}
