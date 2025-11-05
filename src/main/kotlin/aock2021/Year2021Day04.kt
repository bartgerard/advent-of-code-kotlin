package aock2021

import shared.BingoGrid
import shared.BingoVerifier
import shared.sanitize
import shared.splitByEmptyLine
import shared.toIntegers
import java.util.Objects.nonNull

data class Year2021Day04(
    private val numbers: List<Int>,
    private val boards: List<BingoGrid>
) {
    constructor(input: String) : this(input.sanitize().splitByEmptyLine())
    constructor(input: List<String>) : this(
        input[0].toIntegers(),
        input.drop(1).map { BingoGrid(it) }
    )

    fun partOne() = score(completionSequence().first())

    fun partTwo() = score(completionSequence().last())

    private fun score(board: BingoGrid) = board.lastMarked * board.points()
        .filter { !board.marked.contains(it) }
        .sumOf { board.at(it) }

    private fun completionSequence(): Sequence<BingoGrid> = sequence {
        val verifier = BingoVerifier.forDimension(boards.first().dimension())
        val finished = mutableSetOf<BingoGrid>()

        numbers.forEach { number ->
            boards.filter { !finished.contains(it) }.forEach { board ->
                val marked = board.mark(number)

                if (nonNull(marked) && verifier.containsBingo(board)) {
                    finished += board
                    yield(board)
                }
            }
        }
    }
}