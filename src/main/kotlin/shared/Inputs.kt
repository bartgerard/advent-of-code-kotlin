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

fun readFile(year: Int, day: Int) = readFile(year, day, "input")

fun readFile(year: Int, day: Int, filename: String = "input") = readFile("$year/day$day/$filename.txt")

fun readFile(filename: String = "input"): String {
    val resource = Thread.currentThread().contextClassLoader.getResource(filename)
    return resource!!.readText(defaultCharset()).trimEnd()
}

fun downloadInputFile(year: Int, day: Int) {
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

fun String.sanitize() = this.replace("\r", "")

fun String.splitByEmptyLine() = this.split("\n\n")

fun String.table(separator: String) = this.lines().map { it.split(separator) }

val NUMBER_PATTERN = "-?\\d+".toRegex()

fun String.toIntegers() = NUMBER_PATTERN.findAll(this)
    .map { it.value.toInt() }
    .toList()

fun String.toLongs() = NUMBER_PATTERN.findAll(this)
    .map { it.value.toLong() }
    .toList()

fun String.toDoubles() = NUMBER_PATTERN.findAll(this)
    .map { it.value.toDouble() }
    .toList()

fun List<Long>.toBox() = Box(this[0], this[1], this[2])

fun String.toRectangle() = toIntegers().let { (x1, y1, x2, y2) -> Point2d(x1, y1) to Point2d(x2, y2) }

fun parseInt(s: String) = when (s) {
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

fun parseLong(s: String) = when (s) {
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