package aock2023

import shared.sanitize

data class Year2023Day25(
    private val edges: List<Edge>
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
            val graph = Graph(edges)
            graph.kargerMinimumCut()

            if (graph.edges.size == groups) {
                return graph.vertices.values.map { it.labels }.toSet()
            }
        }

        return emptySet()
    }
}

data class Graph(
    val vertices: MutableMap<String, Vertex>,
    val edges: MutableList<Edge>
) {
    constructor(edges: List<Edge>) : this(
        edges.asSequence()
            .flatMap { it.vertices() }
            .distinct()
            .associateWith { Vertex(it) }
            .toMutableMap(),
        edges.toMutableList()
    )

    private fun contract(edge: Edge) {
        val (v1, v2) = edge

        vertices[v1]!!.labels.addAll(vertices[v2]!!.labels)
        vertices -= v2

        val impactedEdges = edges.filter { it.contains(v2) }
        edges.removeAll(impactedEdges)

        val newEdges = impactedEdges.flatMap { it.vertices() }
            .filter { it != v1 && it != v2 }
            .map { Edge(v1, it) }

        edges += newEdges
    }

    // Karger's algorithm
    fun kargerMinimumCut() {
        while (vertices.keys.size > 2) {
            val edge = edges.random()
            contract(edge)
        }
    }

    fun between(v1: String, v2: String) {
        val remaining = mutableListOf(v1)
        val visited = mutableSetOf<Edge>()

        while (remaining.isNotEmpty()) {
            val vi = vertices[remaining.removeFirst()]
            //vertex.edges
        }
    }

    fun toInputString() = edges.joinToString("\n") { it.toString() }
}

data class Vertex(
    val labels: MutableSet<String>
) {
    constructor(label: String) : this(mutableSetOf(label))
}

data class Edge(
    val v1: String,
    val v2: String
) {
    fun contains(label: String) = v1 == label || v2 == label
    fun vertices() = sequenceOf(v1, v2)

    override fun toString() = "$v1, $v2"
}