package aock2023

import shared.sanitize

data class Year2023Day25(
    private val graph: Graph
) {
    // https://csacademy.com/app/graph_editor/
    constructor(input: String) : this(Graph.parse(input))

    fun partOne(): Int {
        println(graph.toInputString())
        return 0
    }
}

data class Graph(
    val edges: List<Edge>,
    val vertices: MutableMap<String, Vertex>
) {
    companion object {
        fun parse(input: String): Graph = parse(
            input.sanitize().lines()
                .map { it.split(": ") }
                .flatMap({ line ->
                    line[1].split(" ")
                        .map { Edge(line[0], it) }
                })
        )

        private fun parse(edges: List<Edge>): Graph {
            val vertices = edges.asSequence()
                .flatMap { it.vertices() }
                .distinct()
                .associateWith { Vertex(it) }
                .toMutableMap()

            edges.forEach { edge ->
                vertices[edge.v1]!!.edges.add(edge)
                vertices[edge.v2]!!.edges.add(edge)
            }

            return Graph(edges, vertices).also { it.toInputString() }
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
    val labels: MutableSet<String>,
    val edges: MutableList<Edge>
) {
    constructor(label: String) : this(mutableSetOf(label), mutableListOf())
}

data class Edge(
    val v1: String,
    val v2: String
) {
    fun vertices() = sequenceOf(v1, v2)

    override fun toString() = "$v1, $v2"
}