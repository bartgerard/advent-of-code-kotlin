package shared

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.nio.charset.Charset.defaultCharset
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
    val cookie = readFile("cookie.private")
    val request = HttpRequest.newBuilder(URI("https://adventofcode.com/$year/day/$day/input"))
        .header("Cookie", cookie)
        .header("accept", "text/plain")
        .GET()
        .build()

    val destination = Path("src/test/resources/$year/day$day/input.txt")
    destination.createParentDirectories()

    HttpClient.newHttpClient()
        //.send(request, BodyHandlers.ofString())
        .send(request, BodyHandlers.ofFile(destination))
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