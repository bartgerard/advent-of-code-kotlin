package shared.algorithm.karger

data class Vertices<V>(
    val vertices: MutableSet<V>
) {
    companion object {
        fun <V> of(vertex: V) = Vertices(mutableSetOf(vertex))
    }
}