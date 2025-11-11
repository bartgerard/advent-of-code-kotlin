package aock2021

data class Year2021Day16(
    private val input: String
) {
    companion object {
        val BINARY_MAPPING = mapOf(
            '0' to "0000",
            '1' to "0001",
            '2' to "0010",
            '3' to "0011",
            '4' to "0100",
            '5' to "0101",
            '6' to "0110",
            '7' to "0111",
            '8' to "1000",
            '9' to "1001",
            'A' to "1010",
            'B' to "1011",
            'C' to "1100",
            'D' to "1101",
            'E' to "1110",
            'F' to "1111",
        )
    }

    fun partOne(): Long {
        val binaryInput = input.mapNotNull { BINARY_MAPPING[it] }.joinToString("")

        val versionId = binaryInput.take(3).toInt(2)
        val typeId = binaryInput.substring(3, 6).toInt(2)

        when (typeId) {
            4 -> {
                // literal value
                val a = binaryInput.drop(6)
                    .chunked(5)
                    .takeWhile { it[0] == '1' }
            }

            else -> {
                // operator
            }
        }

        return 0L
    }

    fun partTwo() = 0L
}