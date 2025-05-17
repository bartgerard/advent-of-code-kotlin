package aock2024

import shared.CharGrid
import shared.Dimension
import shared.Direction
import shared.Point2d
import shared.Vector2d
import shared.sanitize
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
        .also { warehouse -> directions.forEach { warehouse.move(Vector2d.forDirection(it)) } }
        .also { println(it) }
        .gps()

    fun partTwo() = warehouse
        .expand()
        .also { warehouse -> directions.forEach { warehouse.move(Vector2d.forDirection(it)) } }
        .also { println(it) }
        .gps()
}

data class Warehouse(
    val dimension: Dimension,
    val walls: Set<Point2d>,
    val boxes: List<Box>,
    var robot: Point2d
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

        fun expand(p: Point2d) = sequenceOf(
            Point2d(p.x * 2, p.y),
            Point2d(p.x * 2 + 1, p.y)
        )
    }

    fun expand(): Warehouse {
        val expandedDimension = Dimension(dimension.width * 2, dimension.height)
        val expandedWalls = walls.flatMap { expand(it) }.toSet()
        val bigBoxes = boxes.map { box -> Box(box.points.flatMap { expand(it) }.toMutableList()) }
        val expandedRobot = expand(robot).first()
        return Warehouse(expandedDimension, expandedWalls, bigBoxes, expandedRobot)
    }

    private fun findBox(p: Point2d) = boxes.firstOrNull { it.contains(p) }

    private fun canMove(box: Box, d: Vector2d): Boolean {
        val points = box.movement(d)

        if (!Collections.disjoint(points, walls)) {
            return false
        }

        return points.mapNotNull { findBox(it) }
            .distinct()
            .filter { it != box }
            .all { canMove(it, d) }
    }

    private fun impactedBoxes(box: Box, d: Vector2d): List<Box> = buildList {
        add(box)
        addAll(
            box.movement(d)
                .mapNotNull { findBox(it) }
                .distinct()
                .filter { it != box }
                .flatMap { impactedBoxes(it, d) }
        )
    }

    fun move(d: Vector2d) {
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
    var points: List<Point2d>
) {
    constructor(p: Point2d) : this(mutableListOf(p))

    fun contains(p: Point2d) = points.contains(p)

    fun move(d: Vector2d) {
        points = movement(d)
    }

    fun movement(d: Vector2d) = points.map { it + d }

    fun gps() = 100L * points.first().y + points.first().x
}