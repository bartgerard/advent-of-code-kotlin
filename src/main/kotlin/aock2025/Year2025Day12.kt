package aock2025

import shared.geometry2d.Dimension2d
import shared.grid.CharGrid
import shared.sanitize
import shared.splitByEmptyLine
import shared.toIntegers

// packing problem?

data class Year2025Day12(
    private val shapes: List<PresentShape>,
    private val requirements: List<Region>
) {
    constructor(input: String) : this(input.sanitize().splitByEmptyLine())
    constructor(input: List<String>) : this(
        input.dropLast(1).map { PresentShape.parse(it) },
        input.last().lines().map { Region.parse(it) }
    )

    fun partOne() = requirements.count { it.canFit(shapes) }
}

data class PresentShape(
    val id: Int,
    val shape: CharGrid,
    val size: Int
) {
    companion object {
        const val SHAPE = '#'

        fun parse(input: String): PresentShape = PresentShape(
            input.toIntegers().first(),
            CharGrid(input.lines().drop(1).joinToString(separator = "\n"))
        )
    }

    constructor(
        id: Int,
        shape: CharGrid
    ) : this(
        id,
        shape,
        shape.findAll(SHAPE).size
    )
}

data class Region(
    val dimension: Dimension2d,
    val requirements: List<Int>,
) {

    companion object {
        fun parse(input: String): Region {
            val (region, requirements) = input.split(':')
            return Region(
                Dimension2d.parse(region),
                requirements.toIntegers()
            )
        }
    }

    fun canFit(shapes: List<PresentShape>): Boolean {
        val bool = requirements.zip(shapes)
            .sumOf { (times, shape) -> times * shape.size } <= dimension.area()

        return bool
    }
}