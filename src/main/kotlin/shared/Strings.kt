package shared

fun String.splitIn(n: Int) = (this.length / n).let { if (it > 0) this.chunked(it) else listOf(this) }

fun String.zipWithRemainder(): Sequence<Pair<Char, String>> = this
    .mapIndexed { index, character -> character to this.substring(index + 1) }
    .asSequence()
