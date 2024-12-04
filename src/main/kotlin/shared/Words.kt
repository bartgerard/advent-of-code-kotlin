package shared

import aock2024.Year2024Day04.Companion.WORD

data class WordGrid(
    val lines: List<String>
) {

    fun findAll(c: Char) = lines.flatMapIndexed { row, line ->
        line.indices.filter { line[it] == c }
            .map { column -> Point2d(column, row) }
    }

    fun countOccurrences(word: String, directions: Collection<Vector2d>) = findAll(word[0]).sumOf { point ->
        directions.count { vector ->
            WORD.indices.map { index -> point + vector * index to WORD[index] }
                .all { (point, character) -> contains(point) && character == at(point) }
        }
    }

    fun contains(p: Point2d): Boolean = p.y in lines.indices && p.x in 0..<lines[p.y].length

    fun at(p: Point2d): Char = lines[p.y][p.x]

}