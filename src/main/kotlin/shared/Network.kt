package shared

data class Network<T>(
    val connections: Set<Connection<T>>,
    val connectionsByNode: Map<T, Set<T>>
) {
    companion object {
        fun <T> ofBidirectional(connections: Set<Connection<T>>) = of(
            connections.flatMap {
                buildSet {
                    add(it)
                    add(it.inverse())
                }
            }.toSet()
        )

        fun <T> of(connections: Set<Connection<T>>) = connections
            .groupBy({ it.first }, { it.second })
            .mapValues { (_, nodes) -> nodes.toSet() }
            .let { Network(connections, it) }
    }

    fun interconnectedNodes(): Set<Set<T>> = connectionsByNode.entries
        .flatMap { (node0, candidateNodes) ->
            candidateNodes.combinations()
                .filter { (node1, node2) -> connectionsByNode[node1]?.contains(node2) == true }
                .map { (node1, node2) -> setOf(node0, node1, node2) }
        }
        .toSet()

    fun interconnectedGroups() = connectionsByNode.entries
        .map { (node0, candidateNodes) -> interconnectedGroup(node0, candidateNodes) }
        .toSet()

    private fun interconnectedGroup(node0: T, candidateNodes: Set<T>): Set<T> {
        val group = mutableSetOf(node0)

        candidateNodes.forEach { node ->
            if (group.all { it in connectionsByNode[node]!! }) {
                group += node
            }
        }

        return group
    }
}

data class Connection<T>(
    val first: T,
    val second: T
) {
    companion object {
        fun <T> of(first: T, last: T) = Connection(first, last)
        fun <T> ofBidirectional(first: T, last: T) = setOf(
            Connection(first, last),
            Connection(last, first)
        )
    }

    constructor(pair: Pair<T, T>) : this(pair.first, pair.second)

    fun inverse() = Connection(second, first)
}