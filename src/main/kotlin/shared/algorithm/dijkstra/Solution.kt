package shared.algorithm.dijkstra

data class Solution<E>(
    val source: E,
    val destination: E?,
    val path: Path<E>
) {
    fun cost() = path.costTo(destination)

    fun fullPath() = path.fullPathTo(destination)
}