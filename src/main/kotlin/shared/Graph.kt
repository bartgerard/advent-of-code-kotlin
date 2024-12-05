package shared

class Dijkstra {
    companion object {
        fun <T> findShortestPath(
            start: T,
            costFunction: (String, String) -> Long? = { _, _ -> 1 }
        ): Long {
            return 0
        }
    }


}

fun main() {
    val cost: Map<String, Map<String, Long>> = mapOf("a" to mapOf("b" to 1L))

    val result = Dijkstra.findShortestPath("a") { from: String, to: String -> cost[from]?.get(to) }
}
