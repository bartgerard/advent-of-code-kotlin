package shared.algorithm.dijkstra

data class Step<E>(
    val source: E?,
    val cost: Long
)