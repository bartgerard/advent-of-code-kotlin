package shared

// Karger
// https://en.wikipedia.org/wiki/Karger%27s_algorithm
data class ContractableGraph(
    val vertices: MutableMap<String, Vertices<String>>,
    val edges: MutableList<Edge<String>>
) {
    constructor(edges: List<Edge<String>>) : this(
        edges.asSequence()
            .flatMap { it.vertices() }
            .distinct()
            .associateWith { Vertices.of(it) }
            .toMutableMap(),
        edges.toMutableList()
    )

    private fun contract(edge: Edge<String>) {
        val (v1, v2) = edge

        vertices[v1]!!.vertices.addAll(vertices[v2]!!.vertices)
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
        mutableSetOf<Edge<String>>()

        while (remaining.isNotEmpty()) {
            vertices[remaining.removeFirst()]
            //vertex.edges
        }
    }

    fun findEdgesFor(vertex: String) = edges.filter { it.contains(vertex) }
    fun findNeighbours(vertex: String) = findEdgesFor(vertex)
        .map { edge -> edge.vertices().filter { it != vertex }.first() }

    fun toInputString() = edges.joinToString("\n") { it.toString() }
}

data class Vertices<V>(
    val vertices: MutableSet<V>
) {
    companion object {
        fun <V> of(vertex: V) = Vertices(mutableSetOf(vertex))
    }
}

data class Edge<V>(
    val v1: V,
    val v2: V
) {
    fun contains(vertex: V) = v1 == vertex || v2 == vertex
    fun vertices() = sequenceOf(v1, v2)

    override fun toString() = "$v1, $v2"
}
