package shared

import kotlin.ranges.LongRange
import spock.lang.Specification

class RangesSpecification extends Specification {

    private static LongRange range(final IntRange range) {
        new LongRange(range.from, range.to)
    }

    def "contains range"() {
        when:
        final containsRange = RangesKt.containsRange(range1, range2)

        then:
        containsRange == expectedResult

        where:
        range1        | range2        || expectedResult | comment
        range(0..0)   | range(0..0)   || true           | ""
        range(0..0)   | range(1..1)   || false          | ""
        range(1..1)   | range(0..0)   || false          | ""
        range(0..0)   | range(-1..-1) || false          | ""
        range(-1..-1) | range(0..0)   || false          | ""
        range(0..1)   | range(0..1)   || true           | ""
        range(0..1)   | range(1..1)   || true           | ""
        range(0..1)   | range(1..2)   || false          | ""
        range(0..1)   | range(2..2)   || false          | ""
        range(0..1)   | range(2..3)   || false          | ""
        range(0..1)   | range(3..3)   || false          | ""
        range(0..1)   | range(3..4)   || false          | ""
        range(0..1)   | range(-1..0)  || false          | ""
        range(0..1)   | range(-1..-1) || false          | ""
        range(0..1)   | range(-2..-1) || false          | ""
        range(0..1)   | range(0..2)   || false          | ""
        range(0..1)   | range(-1..1)  || false          | ""
        range(0..1)   | range(-1..2)  || false          | ""
        range(0..2)   | range(0..1)   || true           | ""
        range(-1..1)  | range(0..1)   || true           | ""
        range(-1..2)  | range(0..1)   || true           | ""
    }

    def "is intersecting with"() {
        when:
        final isIntersecting = RangesKt.overlaps(range1, range2)

        then:
        isIntersecting == expectedResult

        where:
        range1        | range2        || expectedResult | comment
        range(0..0)   | range(0..0)   || true           | ""
        range(0..0)   | range(1..1)   || false          | ""
        range(1..1)   | range(0..0)   || false          | ""
        range(0..0)   | range(-1..-1) || false          | ""
        range(-1..-1) | range(0..0)   || false          | ""
        range(0..1)   | range(0..1)   || true           | ""
        range(0..1)   | range(1..1)   || true           | ""
        range(0..1)   | range(1..2)   || true           | ""
        range(0..1)   | range(2..2)   || false          | ""
        range(0..1)   | range(2..3)   || false          | ""
        range(0..1)   | range(3..3)   || false          | ""
        range(0..1)   | range(3..4)   || false          | ""
        range(0..1)   | range(-1..0)  || true           | ""
        range(0..1)   | range(-1..-1) || false          | ""
        range(0..1)   | range(-2..-1) || false          | ""
        range(0..1)   | range(0..2)   || true           | ""
        range(0..1)   | range(-1..1)  || true           | ""
        range(0..1)   | range(-1..2)  || true           | ""
        range(0..2)   | range(0..1)   || true           | ""
        range(-1..1)  | range(0..1)   || true           | ""
        range(-1..2)  | range(0..1)   || true           | ""
    }

    def "used intersections"() {
        when:
        final usedIntersections = RangesKt.usedIntersections(ranges)

        then:
        usedIntersections == expectedResult

        where:
        ranges                     || expectedResult                          | comment
        []                         || []                                      | ""
        // range1: |
        // result: |
        [range(0..0)]              || [range(0..0)]                           | ""

        [range(0..0), range(0..1)] || [range(0..0), range(1..1)]              | ""

        // range1: |-----|
        // range2:    |-----|
        // result: |--|--|--|
        [range(0..4), range(2..6)] || [range(0..1), range(2..4), range(5..6)] | ""

        // range1: |--------|
        // range2:    |--|
        // result: |--|--|--|
        [range(0..6), range(2..4)] || [range(0..1), range(2..4), range(5..6)] | ""

        // range1:    |--|
        // range2: |--------|
        // result: |--|--|--|
        [range(2..4), range(0..6)] || [range(0..1), range(2..4), range(5..6)] | ""

        // range1: |--|
        // range2:       |--|
        // result: |--|  |--|
        [range(0..2), range(4..6)] || [range(0..2), range(4..6)]              | ""

        // range1:       |--|
        // range2: |--|
        // result: |--|  |--|
        [range(4..6), range(0..2)] || [range(0..2), range(4..6)]              | ""

        // range1: |---|
        // range2:     |----|
        // result: |---|----|
        [range(0..3), range(3..6)] || [range(0..2), range(3..3), range(4..6)] | ""

        // range1:     |----|
        // range2: |---|
        // result: |---|----|
        [range(3..6), range(0..3)] || [range(0..2), range(3..3), range(4..6)] | ""
    }

    def "gaps"() {
        when:
        final gaps = RangesKt.gaps(ranges)

        then:
        gaps == expectedResult

        where:
        ranges                     || expectedResult | comment
        []                         || []             | ""
        // range1: |
        // result: |
        [range(0..0)]              || []             | ""

        [range(0..0), range(0..1)] || []             | ""

        // range1: |-----|
        // range2:    |-----|
        // result:
        [range(0..4), range(2..6)] || []             | ""

        // range1: |--------|
        // range2:    |--|
        // result:
        [range(0..6), range(2..4)] || []             | ""

        // range1:    |--|
        // range2: |--------|
        // result:
        [range(2..4), range(0..6)] || []             | ""

        // range1: |--|
        // range2:       |--|
        // result:     ||
        [range(0..2), range(4..6)] || [range(3..3)]  | ""

        // range1:       |--|
        // range2: |--|
        // result:     ||
        [range(4..6), range(0..2)] || [range(3..3)]  | ""

        // range1: |---|
        // range2:     |----|
        // result:
        [range(0..3), range(3..6)] || []             | ""

        // range1:     |----|
        // range2: |---|
        // result:
        [range(3..6), range(0..3)] || []             | ""
    }

    def "range without range"() {
        when:
        final remaining = RangesKt.without(range, subtrahend)

        then:
        remaining == expectedResult

        where:
        range       | subtrahend    || expectedResult             | comment
        range(0..0) | range(0..0)   || []                         | ""
        range(0..0) | range(1..1)   || [range(0..0)]              | ""
        range(0..5) | range(6..6)   || [range(0..5)]              | ""
        range(0..5) | range(5..5)   || [range(0..4)]              | ""
        range(0..5) | range(4..5)   || [range(0..3)]              | ""
        range(0..5) | range(4..4)   || [range(0..3), range(5..5)] | ""
        range(0..5) | range(3..3)   || [range(0..2), range(4..5)] | ""
        range(0..5) | range(-1..-1) || [range(0..5)]              | ""
        range(0..5) | range(0..0)   || [range(1..5)]              | ""
        range(0..5) | range(0..1)   || [range(2..5)]              | ""
        range(0..5) | range(1..1)   || [range(0..0), range(2..5)] | ""
        range(0..5) | range(2..2)   || [range(0..1), range(3..5)] | ""
    }

    def "range without ranges"() {
        when:
        final remaining = RangesKt.without(range, subtrahends)

        then:
        remaining == expectedResult

        where:
        range       | subtrahends                             || expectedResult                                       | comment
        range(0..0) | [range(0..0)]                           || []                                                   | ""
        range(0..0) | [range(1..1)]                           || [range(0..0)]                                        | ""
        range(0..5) | [range(6..6)]                           || [range(0..5)]                                        | ""
        range(0..5) | [range(5..5)]                           || [range(0..4)]                                        | ""
        range(0..5) | [range(4..5)]                           || [range(0..3)]                                        | ""
        range(0..5) | [range(4..4)]                           || [range(0..3), range(5..5)]                           | ""
        range(0..5) | [range(3..3)]                           || [range(0..2), range(4..5)]                           | ""
        range(0..5) | [range(-1..-1)]                         || [range(0..5)]                                        | ""
        range(0..5) | [range(0..0)]                           || [range(1..5)]                                        | ""
        range(0..5) | [range(0..1)]                           || [range(2..5)]                                        | ""
        range(0..5) | [range(1..1)]                           || [range(0..0), range(2..5)]                           | ""
        range(0..5) | [range(2..2)]                           || [range(0..1), range(3..5)]                           | ""

        range(0..4) | [range(1..1), range(3..3)]              || [range(0..0), range(2..2), range(4..4)]              | ""
        range(0..6) | [range(1..1), range(3..3), range(5..5)] || [range(0..0), range(2..2), range(4..4), range(6..6)] | ""
        range(0..7) | [range(2..2), range(5..5)]              || [range(0..1), range(3..4), range(6..7)]              | ""
        range(0..8) | [range(2..3), range(5..6)]              || [range(0..1), range(4..4), range(7..8)]              | ""
    }

    def "ranges without ranges"() {
        when:
        final remaining = RangesKt.without(ranges, subtrahends)

        then:
        remaining == expectedResult

        where:
        ranges                         | subtrahends                             || expectedResult                                                        | comment
        [range(0..0)]                  | [range(0..0)]                           || []                                                                    | ""
        [range(0..0)]                  | [range(1..1)]                           || [range(0..0)]                                                         | ""
        [range(0..5)]                  | [range(6..6)]                           || [range(0..5)]                                                         | ""
        [range(0..5)]                  | [range(5..5)]                           || [range(0..4)]                                                         | ""
        [range(0..5)]                  | [range(4..5)]                           || [range(0..3)]                                                         | ""
        [range(0..5)]                  | [range(4..4)]                           || [range(0..3), range(5..5)]                                            | ""
        [range(0..5)]                  | [range(3..3)]                           || [range(0..2), range(4..5)]                                            | ""
        [range(0..5)]                  | [range(-1..-1)]                         || [range(0..5)]                                                         | ""
        [range(0..5)]                  | [range(0..0)]                           || [range(1..5)]                                                         | ""
        [range(0..5)]                  | [range(0..1)]                           || [range(2..5)]                                                         | ""
        [range(0..5)]                  | [range(1..1)]                           || [range(0..0), range(2..5)]                                            | ""
        [range(0..5)]                  | [range(2..2)]                           || [range(0..1), range(3..5)]                                            | ""

        [range(-10..-10), range(0..4)] | [range(1..1), range(3..3)]              || [range(-10..-10), range(0..0), range(2..2), range(4..4)]              | ""
        [range(-10..-10), range(0..6)] | [range(1..1), range(3..3), range(5..5)] || [range(-10..-10), range(0..0), range(2..2), range(4..4), range(6..6)] | ""
        [range(-10..-10), range(0..7)] | [range(2..2), range(5..5)]              || [range(-10..-10), range(0..1), range(3..4), range(6..7)]              | ""
        [range(-10..-10), range(0..8)] | [range(2..3), range(5..6)]              || [range(-10..-10), range(0..1), range(4..4), range(7..8)]              | ""
        [range(-10..-10), range(0..0)] | [range(0..0)]                           || [range(-10..-10)]                                                     | ""
        [range(-10..-10), range(0..0)] | [range(1..1)]                           || [range(-10..-10), range(0..0)]                                        | ""
        [range(-10..-10), range(0..5)] | [range(6..6)]                           || [range(-10..-10), range(0..5)]                                        | ""
        [range(-10..-10), range(0..5)] | [range(5..5)]                           || [range(-10..-10), range(0..4)]                                        | ""
        [range(-10..-10), range(0..5)] | [range(4..5)]                           || [range(-10..-10), range(0..3)]                                        | ""
        [range(-10..-10), range(0..5)] | [range(4..4)]                           || [range(-10..-10), range(0..3), range(5..5)]                           | ""
        [range(-10..-10), range(0..5)] | [range(3..3)]                           || [range(-10..-10), range(0..2), range(4..5)]                           | ""
        [range(-10..-10), range(0..5)] | [range(-1..-1)]                         || [range(-10..-10), range(0..5)]                                        | ""
        [range(-10..-10), range(0..5)] | [range(0..0)]                           || [range(-10..-10), range(1..5)]                                        | ""
        [range(-10..-10), range(0..5)] | [range(0..1)]                           || [range(-10..-10), range(2..5)]                                        | ""
        [range(-10..-10), range(0..5)] | [range(1..1)]                           || [range(-10..-10), range(0..0), range(2..5)]                           | ""
        [range(-10..-10), range(0..5)] | [range(2..2)]                           || [range(-10..-10), range(0..1), range(3..5)]                           | ""

        [range(-10..-10), range(0..4)] | [range(1..1), range(3..3)]              || [range(-10..-10), range(0..0), range(2..2), range(4..4)]              | ""
        [range(-10..-10), range(0..6)] | [range(1..1), range(3..3), range(5..5)] || [range(-10..-10), range(0..0), range(2..2), range(4..4), range(6..6)] | ""
        [range(-10..-10), range(0..7)] | [range(2..2), range(5..5)]              || [range(-10..-10), range(0..1), range(3..4), range(6..7)]              | ""
        [range(-10..-10), range(0..8)] | [range(2..3), range(5..6)]              || [range(-10..-10), range(0..1), range(4..4), range(7..8)]              | ""
    }

}
