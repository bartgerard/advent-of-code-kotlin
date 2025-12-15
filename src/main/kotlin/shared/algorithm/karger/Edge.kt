package shared.algorithm.karger

data class Edge<V>(
    val v1: V,
    val v2: V
) {
    fun contains(vertex: V) = v1 == vertex || v2 == vertex
    fun vertices() = sequenceOf(v1, v2)

    override fun toString() = "$v1, $v2"
}