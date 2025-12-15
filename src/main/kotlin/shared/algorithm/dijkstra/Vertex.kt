package shared.algorithm.dijkstra

data class Vertex<E>(
    val destination: E,
    val cost: Long
)