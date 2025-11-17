package shared


import spock.lang.Specification

class GridSpecification extends Specification {

    def "outer points in direction"() {
        given:
        final dimension = new Dimension(width, height)
        final expectedPoints = expectedResult.stream()
                .map { new Point2d(it) }
                .toList()

        when:
        final points = dimension.outerPointsInDirection(direction)

        then:
        points == expectedPoints

        where:
        width | height | direction            || expectedResult   | comment
        2     | 2      | Direction.NORTH      || [[0, 0], [1, 0]] | ""
        2     | 2      | Direction.EAST       || [[1, 0], [1, 1]] | ""
        2     | 2      | Direction.SOUTH      || [[0, 1], [1, 1]] | ""
        2     | 2      | Direction.WEST       || [[0, 0], [0, 1]] | ""
        2     | 2      | Direction.NORTH_EAST || [[1, 0]]         | ""
        2     | 2      | Direction.SOUTH_EAST || [[1, 1]]         | ""
        2     | 2      | Direction.SOUTH_WEST || [[0, 1]]         | ""
        2     | 2      | Direction.NORTH_WEST || [[0, 0]]         | ""
    }

    def "all outer points in direction"() {
        given:
        final dimension = new Dimension(width, height)
        final expectedPoints = expectedResult.stream()
                .map { new Point2d(it) }
                .toList()

        when:
        final points = dimension.allOuterPointsInDirection(direction)

        then:
        points == expectedPoints

        where:
        width | height | direction            || expectedResult                           | comment
        2     | 2      | Direction.NORTH      || [[0, 0], [1, 0]]                         | ""
        2     | 2      | Direction.EAST       || [[1, 0], [1, 1]]                         | ""
        2     | 2      | Direction.SOUTH      || [[0, 1], [1, 1]]                         | ""
        2     | 2      | Direction.WEST       || [[0, 0], [0, 1]]                         | ""
        2     | 2      | Direction.NORTH_EAST || [[0, 0], [1, 0], [1, 1]]                 | ""
        2     | 2      | Direction.SOUTH_EAST || [[0, 1], [1, 1], [1, 0]]                 | ""
        2     | 2      | Direction.SOUTH_WEST || [[0, 0], [0, 1], [1, 1]]                 | ""
        2     | 2      | Direction.NORTH_WEST || [[0, 1], [0, 0], [1, 0]]                 | ""

        3     | 3      | Direction.NORTH      || [[0, 0], [1, 0], [2, 0]]                 | ""
        3     | 3      | Direction.EAST       || [[2, 0], [2, 1], [2, 2]]                 | ""
        3     | 3      | Direction.SOUTH      || [[0, 2], [1, 2], [2, 2]]                 | ""
        3     | 3      | Direction.WEST       || [[0, 0], [0, 1], [0, 2]]                 | ""
        3     | 3      | Direction.NORTH_EAST || [[0, 0], [1, 0], [2, 0], [2, 1], [2, 2]] | ""
        3     | 3      | Direction.SOUTH_EAST || [[0, 2], [1, 2], [2, 2], [2, 1], [2, 0]] | ""
        3     | 3      | Direction.SOUTH_WEST || [[0, 0], [0, 1], [0, 2], [1, 2], [2, 2]] | ""
        3     | 3      | Direction.NORTH_WEST || [[0, 2], [0, 1], [0, 0], [1, 0], [2, 0]] | ""
    }

    def "traverse in direction"() {
        given:
        final dimension = new Dimension(width, height)
        final expectedPoints = expectedResult.stream()
                .map { points ->
                    points.stream()
                            .map { new Point2d(it) }
                            .toList()
                }
                .toList()

        when:
        final points = dimension.pointsInDirection(direction)

        then:
        points == expectedPoints

        where:
        width | height | direction            || expectedResult                         | comment
        2     | 2      | Direction.NORTH      || [[[0, 1], [0, 0]], [[1, 1], [1, 0]]]   | ""
        2     | 2      | Direction.EAST       || [[[0, 0], [1, 0]], [[0, 1], [1, 1]]]   | ""
        2     | 2      | Direction.SOUTH      || [[[0, 0], [0, 1]], [[1, 0], [1, 1]]]   | ""
        2     | 2      | Direction.WEST       || [[[1, 0], [0, 0]], [[1, 1], [0, 1]]]   | ""
        2     | 2      | Direction.NORTH_EAST || [[[0, 0]], [[0, 1], [1, 0]], [[1, 1]]] | ""
        2     | 2      | Direction.SOUTH_EAST || [[[0, 1]], [[0, 0], [1, 1]], [[1, 0]]] | ""
        2     | 2      | Direction.SOUTH_WEST || [[[0, 0]], [[1, 0], [0, 1]], [[1, 1]]] | ""
        2     | 2      | Direction.NORTH_WEST || [[[0, 1]], [[1, 1], [0, 0]], [[1, 0]]] | ""
    }

}
