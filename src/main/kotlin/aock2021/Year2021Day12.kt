package aock2021

import shared.Graph
import shared.sanitize

data class Year2021Day12(
    private val graph: Graph<String>
) {
    constructor(input: String) : this(
        Graph(
            input.sanitize()
                .lines()
                .map { line ->
                    line.split("-")
                        .let { it[0] to it[1] }
                }
        )
    )

    fun partOne() = graph.findAllPaths("start", "end") { cave, path ->
        cave.isBig() || cave !in path
    }
        .count()

    fun partTwo() = graph.findAllPaths("start", "end") { cave, path ->
        when {
            cave == "start" -> false
            cave.isBig() -> true
            cave !in path -> true
            else -> !path.containsDoubleSmallCave()
        }
    }
        .count()
}

private fun String.isBig() = all { it.isUpperCase() }

fun List<String>.containsDoubleSmallCave(): Boolean {
    val seen = mutableSetOf<String>()
    return filter { it.isBig().not() }.any { !seen.add(it) }
}