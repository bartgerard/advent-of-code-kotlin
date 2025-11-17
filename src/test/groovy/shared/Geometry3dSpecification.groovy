package shared

import spock.lang.Specification

import static java.util.Objects.isNull

class Geometry3dSpecification extends Specification {

    def "intersect rays"() {
        given:
        final Ray3d r1 = new Ray3d(new Point3d(p1), new Vector3d(v1))
        final Ray3d r2 = new Ray3d(new Point3d(p2), new Vector3d(v2))

        when:
        final Point3d result = r1.intersect(r2)

        then:
        final Point3d expectedPoint = isNull(expectedResult) ? null : new Point3d(expectedResult)
        result == expectedPoint

        where:
        p1              | v1              | p2              | v2               || expectedResult  | comment
        [0.0, 0.0, 0.0] | [1.0, 0.0, 0.0] | [0.0, 1.0, 0.0] | [1.0, 0.0, 0.0]  || null            | "parallel"

        [1.0, 0.0, 0.0] | [0.0, 1.0, 0.0] | [0.0, 1.0, 0.0] | [1.0, 0.0, 0.0]  || [1.0, 1.0, 0.0] | ""
        [0.0, 0.0, 0.0] | [1.0, 1.0, 0.0] | [0.0, 1.0, 0.0] | [1.0, 0.0, 0.0]  || [1.0, 1.0, 0.0] | ""

        [0.0, 0.0, 0.0] | [1.0, 1.0, 0.0] | [0.0, 1.0, 0.0] | [1.0, -1.0, 0.0] || [0.5, 0.5, 0.0] | ""
    }

    def "box contains point"() {
        given:
        final Box3d box = new Point3d(b1).to(new Point3d(b2))
        final Point3d point = new Point3d(p1)

        when:
        final boolean result = box.contains(point)

        then:
        result == expectedResult

        where:
        b1              | b2              | p1              || expectedResult | comment
        [0.0, 0.0, 0.0] | [1.0, 1.0, 0.0] | [0.0, 0.0, 0.0] || true           | ""
        [0.0, 0.0, 0.0] | [1.0, 1.0, 0.0] | [0.5, 0.5, 0.0] || true           | ""
        [0.0, 0.0, 0.0] | [1.0, 1.0, 0.0] | [1.0, 1.0, 0.0] || true           | ""

        [0.0, 0.0, 0.0] | [1.0, 1.0, 0.0] | [2.0, 1.0, 0.0] || false          | ""
        [0.0, 0.0, 0.0] | [1.0, 1.0, 0.0] | [1.0, 2.0, 0.0] || false          | ""
        [0.0, 0.0, 0.0] | [1.0, 1.0, 0.0] | [2.0, 2.0, 0.0] || false          | ""
    }

}
