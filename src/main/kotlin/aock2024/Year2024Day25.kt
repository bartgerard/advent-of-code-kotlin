package aock2024

import shared.geometry2d.Dimension2d
import shared.grid.CharGrid
import shared.grid.columns
import shared.sanitize
import shared.splitByEmptyLine

data class Year2024Day25(
    private val dimension: Dimension2d,
    private val locks: List<List<Int>>,
    private val keys: List<List<Int>>
) {
    constructor(input: String) : this(input.sanitize().splitByEmptyLine().map { CharGrid(it) })

    constructor(grids: List<CharGrid>) : this(
        grids[0].dimension(),
        grids.partition { grid -> grid.firstRow().all { it == '#' } }
    )

    constructor(dimension: Dimension2d, grids: Pair<List<CharGrid>, List<CharGrid>>) : this(
        dimension,
        grids.first.map { lock -> lock.columns().map { column -> column.count { it == '#' } - 1 } },
        grids.second.map { lock -> lock.columns().map { column -> column.count { it == '#' } - 1 } }
    )

    fun partOne(): Long {
        val fits = mutableListOf<Pair<List<Int>, List<Int>>>()

        for (key in keys) {
            for (lock in locks) {
                if (key.zip(lock).map { it.first + it.second }.all { it <= dimension.height - 2 }) {
                    fits += key to lock
                }
            }
        }

        return fits.size.toLong()
    }
}