package aock2015

data class Year2015Day09(
    private val destinations: Destinations
) {
    constructor(input: String) : this(Destinations.parse(input))

    fun partOne(): Long = destinations.findShortestRoute(destinations.cities.toList())

    fun partTwo(): Long = destinations.findLongestRoute(destinations.cities.toList())
}

data class Destinations(
    val cities: Set<String>,
    val distances: Map<Pair<String, String>, Long>
) {
    companion object {
        fun parse(input: String): Destinations {
            val cities = mutableSetOf<String>()
            val distances = mutableMapOf<Pair<String, String>, Long>()

            "(\\w+) to (\\w+) = (\\d+)".toRegex()
                .findAll(input)
                .map { it.destructured }
                .forEach { (city1, city2, distance) ->
                    cities += city1
                    cities += city2
                    distances += city1 to city2 to distance.toLong()
                    distances += city2 to city1 to distance.toLong()
                    //distances.getOrPut(city1) { mutableMapOf() } += (city2 to distance.toLong())
                    //distances.getOrPut(city2) { mutableMapOf() } += (city1 to distance.toLong())
                }

            return Destinations(cities, distances)
        }
    }

    fun findShortestRoute(cities: List<String>) = cities.minOf { findShortestRoute(it, cities - it) }

    fun findShortestRoute(previousCity: String, cities: List<String>): Long = if (cities.isEmpty()) {
        0L
    } else {
        cities.map { nextCity -> previousCity to nextCity }
            .filter { distances.contains(it) }
            .minOf { distances[it]!! + findShortestRoute(it.second, cities - it.second) }
    }

    fun findLongestRoute(cities: List<String>) = cities.maxOf { findLongestRoute(it, cities - it) }

    fun findLongestRoute(previousCity: String, cities: List<String>): Long = if (cities.isEmpty()) {
        0L
    } else {
        cities.map { nextCity -> previousCity to nextCity }
            .filter { distances.contains(it) }
            .maxOf { distances[it]!! + findLongestRoute(it.second, cities - it.second) }
    }

}
