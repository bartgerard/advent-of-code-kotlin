package shared

fun String.splitIn(n: Int) = (this.length / n).let { if (it > 0) this.chunked(it) else listOf(this) }

fun String.zipWithRemainder(): Sequence<Pair<Char, String>> = this
    .mapIndexed { index, character -> character to this.substring(index + 1) }
    .asSequence()

fun List<String>.makeSameLength(): List<String> {
    val maxLength = maxOf { it.length }
    return map { it.padEnd(maxLength, ' ') }
}

fun List<String>.splitByVerticalDelimiter(delimiter: Char): List<List<String>> {
    require(all { it.length == first().length }) { "use List<String>.makeSameLength()" }

    val minLength = this.minOf { it.length }
    val delimiterIndices = (0..<minLength).filter { index -> this.all { it[index] == delimiter } }

    return map { it.splitInColumns(delimiterIndices) }
        .transpose()
}

fun String.splitInColumns(
    delimiterIndices: List<Int>
): List<String> = buildList {
    add(-1)
    addAll(delimiterIndices)
    add(length)
}
    .zipWithNext { i, j -> this.substring(i + 1, j) }