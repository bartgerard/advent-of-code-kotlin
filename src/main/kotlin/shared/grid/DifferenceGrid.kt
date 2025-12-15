package shared.grid

data class DifferenceGrid(
    val grid: List<MutableList<Long>>
) : Grid<Long> {
    constructor(size: Int) : this(MutableList(size) { MutableList(size) { 0L } })

    override fun grid(): List<List<Long>> = grid
}