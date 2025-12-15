package shared.range

fun IntRange.length(): Int = last - first + 1

fun IntRange.containsRange(other: IntRange) = this.first <= other.first && other.last <= this.last
fun IntRange.overlaps(other: IntRange) = this.first <= other.last && other.first <= this.last

fun IntRange.innerRange(): IntRange = first + 1..<last

fun IntRange.toLongRange() = first.toLong()..last.toLong()