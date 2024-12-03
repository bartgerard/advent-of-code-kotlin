package aock2024

data class Year2024Day03(
    private val input: String
) {
    companion object {
        val MUL_REGEX = "mul\\((\\d+),(\\d+)\\)".toRegex()
    }

    fun partOne(): Int {
        return parseMul(input)
    }

    fun partTwo(): Int {
        return input.split("do()")
            .map { it.split("don't()")[0] } // first part hasn't been disabled
            .sumOf { parseMul(it) }
    }

    private fun parseMul(input: String): Int {
        return MUL_REGEX.findAll(input)
            .sumOf { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
    }
}