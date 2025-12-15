package aock2024

import shared.geometry2d.Dimension2d
import shared.geometry2d.Point2dInt
import shared.geometry2d.Vector2dInt
import shared.grid.CharGrid
import shared.sanitize
import shared.spatial.Direction
import shared.splitByEmptyLine
import java.util.*

data class Year2024Day15(
    private val warehouse: Warehouse,
    private val directions: List<Direction>
) {

    constructor(input: String) : this(input.sanitize().splitByEmptyLine())
    constructor(input: List<String>) : this(
        Warehouse.parse(input[0]),
        input[1].lines().flatMap { line -> line.toList().map { Direction.parse(it) } }
    )

    fun partOne() = warehouse
        .also { warehouse -> directions.forEach { warehouse.move(Vector2dInt.forDirection(it)) } }
        //.also { println(it) }
        .gps()

    fun partTwo() = warehouse
        .expand()
        .also { warehouse -> directions.forEach { warehouse.move(Vector2dInt.forDirection(it)) } }
        //.also { println(it) }
        .gps()
}

data class Warehouse(
    val dimension: Dimension2d,
    val walls: Set<Point2dInt>,
    val boxes: List<Box>,
    var robot: Point2dInt
) {
    companion object {
        const val EMPTY = '.'
        const val WALL = '#'
        const val ROBOT = '@'
        const val BOX = 'O'
        const val BIG_BOX_LEFT = '['
        const val BIG_BOX_RIGHT = ']'

        fun parse(input: String) = parse(CharGrid(input))

        fun parse(grid: CharGrid): Warehouse {
            val walls = grid.findAll(WALL).toSet()
            val boxes = grid.findAll(BOX).map { Box(it) }
            val robot = grid.findAll(ROBOT).first()

            return Warehouse(grid.dimension(), walls, boxes, robot)
        }

        fun expand(p: Point2dInt) = sequenceOf(
            Point2dInt(p.x * 2, p.y),
            Point2dInt(p.x * 2 + 1, p.y)
        )
    }

    fun expand(): Warehouse {
        val expandedDimension = Dimension2d(dimension.width * 2, dimension.height)
        val expandedWalls = walls.flatMap { expand(it) }.toSet()
        val bigBoxes = boxes.map { box -> Box(box.points.flatMap { expand(it) }.toMutableList()) }
        val expandedRobot = expand(robot).first()
        return Warehouse(expandedDimension, expandedWalls, bigBoxes, expandedRobot)
    }

    private fun findBox(p: Point2dInt) = boxes.firstOrNull { it.contains(p) }

    private fun canMove(box: Box, d: Vector2dInt): Boolean {
        val points = box.movement(d)

        if (!Collections.disjoint(points, walls)) {
            return false
        }

        return points.mapNotNull { findBox(it) }
            .distinct()
            .filter { it != box }
            .all { canMove(it, d) }
    }

    private fun impactedBoxes(box: Box, d: Vector2dInt): List<Box> = buildList {
        add(box)
        addAll(
            box.movement(d)
                .mapNotNull { findBox(it) }
                .distinct()
                .filter { it != box }
                .flatMap { impactedBoxes(it, d) }
        )
    }

    fun move(d: Vector2dInt) {
        val newPoint = robot + d

        if (walls.contains(newPoint)) {
            return
        }

        val firstBox = findBox(newPoint)

        if (firstBox != null) {
            if (!canMove(firstBox, d)) {
                return
            } else {
                impactedBoxes(firstBox, d)
                    .distinct()
                    .forEach { it.move(d) }
            }
        }

        robot = newPoint
    }

    fun gps() = boxes.sumOf { it.gps() }

    override fun toString(): String {
        val grid = CharGrid(dimension, EMPTY)
        walls.forEach { grid.set(it, WALL) }
        boxes.forEach {
            if (it.points.size == 1) {
                grid.set(it.points.first(), BOX)
            } else {
                grid.set(it.points.first(), BIG_BOX_LEFT)
                grid.set(it.points.last(), BIG_BOX_RIGHT)
            }
        }
        grid.set(robot, ROBOT)

        return grid.toString()
    }

}

data class Box(
    var points: List<Point2dInt>
) {
    constructor(p: Point2dInt) : this(mutableListOf(p))

    fun contains(p: Point2dInt) = points.contains(p)

    fun move(d: Vector2dInt) {
        points = movement(d)
    }

    fun movement(d: Vector2dInt) = points.map { it + d }

    fun gps() = 100L * points.first().y + points.first().x
}