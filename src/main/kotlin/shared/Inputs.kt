package shared

import java.nio.charset.Charset.defaultCharset

fun readFileForDay(
    day: Int
): String {
    return readFileForDay(day, "input")
}

fun readFileForDay(
    day: Int,
    filename: String = "input"
): String {
    return readFile("day$day/$filename.txt")
}

fun readFile(filename: String = "input"): String {
    val resource = Thread.currentThread().contextClassLoader.getResource(filename)
    return resource!!.readText(defaultCharset())
}

fun String.byLine(): List<String> {
    return this.split("\n")
}

/*
fun String.split(count: Int): List<String> {
    return (0..count)
        .asSequence()
        .map { i -> this.filterIndexed { it, _ -> it % count == i } }
        .toList()
}
 */

val NUMBER_PATTERN = "-?\\d+".toRegex()

fun String.asLongs(): List<Long> {
    return NUMBER_PATTERN.findAll(this)
        .map { it.value.toLong() }
        .toList()
}

fun List<Long>.asBox(): Box3d {
    return Box3d(this[0], this[1], this[2])
}