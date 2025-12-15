package shared.algorithm.dijkstra

data class Paths<E>(
    val steps: MutableMap<E, MutableSet<Step<E>>>
) : MutableMap<E, MutableSet<Step<E>>> by steps {
    constructor(start: E) : this(mutableMapOf(start to mutableSetOf(Step(null, 0L))))
}