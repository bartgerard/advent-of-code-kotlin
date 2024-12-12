package aock2024

import shared.MutableGrid
import shared.Point2d
import shared.sanitize

data class Year2024Day12(
    private val grid: MutableGrid
) {
    constructor(input: String) : this(MutableGrid(input.sanitize()))

    fun partOne(): Long {

        var cost = 0L

        val remainingPoints = grid.points().toMutableList()
        val regions = mutableListOf<MutableList<Point2d>>()

        while (remainingPoints.isNotEmpty()) {
            val region = mutableListOf(remainingPoints.removeFirst())
            var perimeter = 0
            var i = 0
            while (i < region.size) {
                val point = region[i]
                val character = grid.at(point)
                val neighbours = point.orthogonalNeighbours().filter { !region.contains(it) }
                val sameRegion = neighbours.filter { grid.contains(it) && grid.at(it) == character }
                remainingPoints.removeAll(sameRegion)
                region.addAll(sameRegion)
                i++
                perimeter += neighbours.size - sameRegion.size
            }
            cost += region.size * perimeter
            regions += region
        }

        return cost
    }

    fun partOneB(): Long = grid.points().map { grid.at(it) }
        .distinct()
        .sumOf { plant -> cost(plant, grid.findAll(plant)).toLong() }

    fun cost(plant: Char, points: List<Point2d>): Int {
        return regions(points)
            .sumOf { region ->
                val area = region.size

                val perimeter = points.sumOf { (it.orthogonalNeighbours() - region).size }

                println(plant + " " + area * perimeter)
                area * perimeter
            }
    }

    fun regions(points: List<Point2d>): List<List<Point2d>> {
        val remaining = points.toMutableList()
        val regions = mutableListOf<MutableList<Point2d>>()

        while (remaining.isNotEmpty()) {
            regions.add(region(remaining.removeFirst(), remaining))
        }

        return regions
    }

    fun region(point: Point2d, remainingPoints: MutableList<Point2d>): MutableList<Point2d> {
        val newPoints = mutableListOf(point)
        var i = 0
        while (i < newPoints.size) {
            val p = newPoints[i]
            for (neighbour in remainingPoints.toList()) {
                if (p.manhattan(neighbour) == 1) {
                    newPoints.add(neighbour)
                    remainingPoints.remove(neighbour)
                }
            }
            i++
        }

        return newPoints
    }

    fun partTwo(): Long = 0
}