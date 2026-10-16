package shared.algorithm.dijkstra

import spock.lang.Specification

class DijkstraSpecification extends Specification {

    private static Solution<String> shortestPath(Map<String, Map<String, Long>> edges, String start, String end) {
        Dijkstra.findShortestPath(
                start,
                { it == end },
                { path, current -> (edges[current] ?: [:]).keySet().toList() },
                { current, next -> edges[current][next] }
        )
    }

    private static Solutions<String> shortestPaths(Map<String, Map<String, Long>> edges, String start, String end) {
        Dijkstra.findShortestPaths(
                start,
                { it == end },
                { current -> (edges[current] ?: [:]).keySet().toList() },
                { current, next -> edges[current][next] }
        )
    }

    def "find shortest path"() {
        when:
        final Solution<String> solution = shortestPath(edges, "A", end)

        then:
        solution.cost() == expectedCost
        solution.fullPath() == expectedPath

        where:
        edges                                                    | end || expectedCost | expectedPath         | comment
        [A: [B: 1L, C: 5L], B: [C: 1L]]                          | "C" || 2L           | ["A", "B", "C"]      | "cheaper path discovered later"
        [A: [B: 4L, C: 1L], C: [D: 1L], D: [B: 1L]]              | "B" || 3L           | ["A", "C", "D", "B"] | "longer path with lower cost"
        [A: [B: 1L], B: [C: 1L]]                                 | "C" || 2L           | ["A", "B", "C"]      | ""
        [A: [A: 0L, B: 1L]]                                      | "B" || 1L           | ["A", "B"]           | "self loop"
        [A: [B: null, C: 3L], C: [B: 1L]]                        | "B" || 4L           | ["A", "C", "B"]      | "null cost is not traversable"
        [A: [B: 7L, C: 1L], C: [D: 1L], D: [E: 1L], E: [B: 10L]] | "B" || 7L           | ["A", "B"]           | "direct edge is cheapest"
    }

    def "find shortest path when end is unreachable"() {
        when:
        final Solution<String> solution = shortestPath(edges, "A", "Z")

        then:
        solution.destination == null
        solution.cost() == Long.MAX_VALUE
        solution.fullPath() == []

        where:
        edges          | comment
        [A: [B: 1L]]   | "no path"
        [A: [Z: null]] | "only edge is not traversable"
    }

    def "find all shortest paths"() {
        when:
        final Solutions<String> solutions = shortestPaths(edges, "A", "D")

        then:
        solutions.cost() == expectedCost
        solutions.vertices() == expectedVertices as Set

        where:
        edges                                              || expectedCost | expectedVertices     | comment
        [A: [B: 1L, C: 1L, D: 5L], B: [D: 1L], C: [D: 1L]] || 2L           | ["A", "B", "C", "D"] | "two equal paths"
        [A: [B: 1L, C: 1L], B: [D: 1L], C: [D: 2L]]        || 2L           | ["A", "B", "D"]      | "one cheaper path"
        [A: [B: 5L, C: 1L], C: [B: 1L], B: [D: 1L]]        || 3L           | ["A", "C", "B", "D"] | "cheaper path discovered later"
        [A: [B: null, C: 3L], B: [D: 1L], C: [D: 1L]]      || 4L           | ["A", "C", "D"]      | "null cost is not traversable"
    }

    def "find all shortest paths when end is unreachable"() {
        when:
        final Solutions<String> solutions = shortestPaths([A: [B: 1L, D: null]], "A", "D")

        then:
        solutions.destination == null
        solutions.cost() == Long.MAX_VALUE
        solutions.vertices() == [] as Set
    }

}
