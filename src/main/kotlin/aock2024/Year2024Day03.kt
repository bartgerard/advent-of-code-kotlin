package aock2024

data class Year2024Day03(
    private val input: String
) {
    companion object {
        val MULTIPLICATION_REGEX = "mul\\((\\d{1,3}),(\\d{1,3})\\)".toRegex()
    }

    fun partOne() = parseMultiplications(input)

    fun partTwo() = input.split("do()")
        .map { it.splitToSequence("don't()").first() } // first segment hasn't been disabled
        .sumOf { parseMultiplications(it) }

    private fun parseMultiplications(input: String) = MULTIPLICATION_REGEX.findAll(input)
        .map { it.destructured }
        .sumOf { (x, y) -> x.toInt() * y.toInt() }

}