package shared

data class WordGrid(
    val lines: List<String>
) {

    fun findAll(c: Char) = lines.flatMapIndexed { row, line ->
        line.indices.filter { line[it] == c }
            .map { column -> Point2d(column, row) }
    }

    fun countOccurrences(word: String, directions: Collection<Vector2d>) = findAll(word[0]).sumOf { point ->
        directions.count { vector ->
            word.indices.map { index -> point + vector * index to word[index] }
                .all { (point, character) -> contains(point) && character == at(point) }
        }
    }

    fun contains(p: Point2d): Boolean = p.y in lines.indices && p.x in 0..<lines[p.y].length

    fun at(p: Point2d): Char = lines[p.y][p.x]

}

data class MutableWordGrid(
    val grid: MutableList<MutableList<Char>>
) {

    fun copy(): MutableWordGrid = MutableWordGrid(grid.map { it.toMutableList() }.toMutableList())

    constructor(input: String) : this(
        input.sanitize()
            .splitByLine()
            .map { it.toCharArray().toMutableList().toList().toMutableList() }.toMutableList()
    )

    fun findAll(c: Char) = grid.flatMapIndexed { row, line ->
        line.indices.filter { line[it] == c }
            .map { column -> Point2d(column, row) }
    }

    fun countOccurrences(word: String, directions: Collection<Vector2d>) = findAll(word[0]).sumOf { point ->
        directions.count { vector ->
            word.indices.map { index -> point + vector * index to word[index] }
                .all { (point, character) -> contains(point) && character == at(point) }
        }
    }

    fun contains(p: Point2d): Boolean = p.y in grid.indices && p.x in 0..<grid[p.y].size

    fun at(p: Point2d): Char = grid[p.y][p.x]

    fun set(p: Point2d, value: Char) = grid[p.y].set(p.x, value)

    override fun toString(): String = grid.joinToString("\n") { it.joinToString("") }

}