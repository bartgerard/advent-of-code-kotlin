package shared

fun String.splitIn(n: Int) = (this.length / n).let { if (it > 0) this.chunked(it) else listOf(this) }