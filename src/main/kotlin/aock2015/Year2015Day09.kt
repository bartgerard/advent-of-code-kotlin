package aock2015

data class Year2015Day09(
    private val input: String
) {
    fun partOne(): Int {
        Regex("(\\w+) to (\\w+) = (\\d+)").findAll(input)
            .map { it.destructured }
            .map { (city1, city2, distance) -> distance.toInt() }
        listOf("a", "b")
        return 0
    }

    fun partTwo(): Int = 0
}