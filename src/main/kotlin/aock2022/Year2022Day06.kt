package aock2022

data class Year2022Day06(
    private val input: String
) {
    fun partOne() = findMarker(4)
    fun partTwo() = findMarker(14)
    fun findMarker(size: Int) = input.windowed(size, 1)
        .indexOfFirst { it.toSet().size == size } + size
}