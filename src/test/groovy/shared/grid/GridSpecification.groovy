package shared.grid

import shared.geometry2d.Dimension2d
import shared.geometry2d.Point2dInt
import shared.spatial.Direction
import spock.lang.Specification

class GridSpecification extends Specification {

    def "outer points in direction"() {
        given:
        final dimension = new Dimension2d(width, height)
        final expectedPoints = expectedResult.stream()
                .map { new Point2dInt(it) }
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
        final dimension = new Dimension2d(width, height)
        final expectedPoints = expectedResult.stream()
                .map { new Point2dInt(it) }
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
        final dimension = new Dimension2d(width, height)
        final expectedPoints = expectedResult.stream()
                .map { points ->
                    points.stream()
                            .map { new Point2dInt(it) }
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


    def "matching grids with offset"() {
        given:
        def grid1 = new CharGrid(grid1AsText)
        def grid2 = new CharGrid(grid2AsText)
        def offset = new Point2dInt(offsetAsList)

        when:
        final result = grid1.matches(grid2, offset)

        then:
        result == expectedResult

        where:
        grid1AsText              | grid2AsText | offsetAsList || expectedResult | comment
        "...\n.#.\n..."          | "#"         | [0, 0]       || false          | ""
        "...\n.#.\n..."          | "#"         | [1, 1]       || true           | ""
        "....\n.##.\n.##.\n...." | "##\n##"    | [1, 1]       || true           | ""
        "....\n.##.\n.##.\n...." | "##\n##"    | [2, 2]       || false          | ""
    }

}
