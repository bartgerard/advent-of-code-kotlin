package aock2024

data class Year2024Day03(
    private val input: String
) {
    companion object {
        val MULTIPLICATION_REGEX = "mul\\((\\d{1,3}),(\\d{1,3})\\)".toRegex()
    }

    fun partOne(): Int = parseMultiplications(input)

    fun partTwo(): Int = input.split("do()")
        .map { it.splitToSequence("don't()").first() } // first segment hasn't been disabled
        .sumOf { parseMultiplications(it) }

    private fun parseMultiplications(input: String): Int = MULTIPLICATION_REGEX.findAll(input)
        .map { it.destructured }
        .sumOf { (x, y) -> x.toInt() * y.toInt() }

}