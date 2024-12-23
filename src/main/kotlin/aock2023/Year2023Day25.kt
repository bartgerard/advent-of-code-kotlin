package aock2023

import shared.ContractableGraph
import shared.Edge
import shared.sanitize

data class Year2023Day25(
    private val edges: List<Edge<String>>
) {
    // https://csacademy.com/app/graph_editor/
    constructor(input: String) : this(
        input.sanitize().lines()
            .map { it.split(": ") }
            .flatMap({ line ->
                line[1].split(" ")
                    .map { Edge(line[0], it) }
            })
    )

    fun partOne(groups: Int): Long {
        val split = findSplit(groups)
        return split.map { it.size }.fold(1L, Long::times)
    }

    private fun findSplit(groups: Int): Set<Set<String>> {
        repeat(100) {
            val graph = ContractableGraph(edges)
            graph.kargerMinimumCut()

            if (graph.edges.size == groups) {
                return graph.vertices.values.map { it.vertices }.toSet()
            }
        }

        return emptySet()
    }
}