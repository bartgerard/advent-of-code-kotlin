package shared

import java.math.BigInteger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.nio.charset.Charset.defaultCharset
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.createParentDirectories

fun readFile(
    year: Int,
    day: Int
): String {
    return readFile(year, day, "input")
}

fun readFile(
    year: Int,
    day: Int,
    filename: String = "input"
): String {
    return readFile("$year/day$day/$filename.txt")
}

fun readFile(filename: String = "input"): String {
    val resource = Thread.currentThread().contextClassLoader.getResource(filename)
    return resource!!.readText(defaultCharset()).trimEnd()
}

fun downloadInputFile(
    year: Int,
    day: Int
) {
    val session = readFile("session.private")
    val request = HttpRequest.newBuilder(URI("https://adventofcode.com/$year/day/$day/input"))
        .header("Cookie", "session=$session")
        .header("accept", "text/plain")
        .GET()
        .build()

    val destination = Path("src/test/resources/$year/day$day/input.txt")
    destination.createParentDirectories()

    HttpClient.newHttpClient()
        //.send(request, BodyHandlers.ofString()).body()
        .send(request, BodyHandlers.ofFile(destination))
}

fun String.sanitize(): String = this.replace("\r", "")

fun String.splitByEmptyLine(): List<String> = this.split("\n\n")

fun String.table(separator: String): List<List<String>> = this.lines().map { it.split(separator) }

/*
fun String.split(count: Int): List<String> {
    return (0..count)
        .asSequence()
        .map { i -> this.filterIndexed { it, _ -> it % count == i } }
        .toList()
}
 */

val NUMBER_PATTERN = "-?\\d+".toRegex()

fun String.toIntegers(): List<Int> = NUMBER_PATTERN.findAll(this)
    .map { it.value.toInt() }
    .toList()

fun String.toLongs(): List<Long> = NUMBER_PATTERN.findAll(this)
    .map { it.value.toLong() }
    .toList()

fun List<Long>.toBox(): Box {
    return Box(this[0], this[1], this[2])
}

fun parseInt(s: String): Int = when (s) {
    "one" -> 1
    "two" -> 2
    "three" -> 3
    "four" -> 4
    "five" -> 5
    "six" -> 6
    "seven" -> 7
    "eight" -> 8
    "nine" -> 9
    else -> s.toInt()
}

fun parseLong(s: String): Long = when (s) {
    "one" -> 1
    "two" -> 2
    "three" -> 3
    "four" -> 4
    "five" -> 5
    "six" -> 6
    "seven" -> 7
    "eight" -> 8
    "nine" -> 9
    else -> s.toLong()
}

operator fun String.times(n: Int) = repeat(n)

/**
 * Converts string to md5 hash.
 * From the Kotlin template https://github.com/kotlin-hands-on/advent-of-code-kotlin-template/blob/main/src/Utils.kt
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')