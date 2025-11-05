package aock2016

import shared.md5

data class Year2016Day05(
    private val input: String
) {
    fun partOne() = generateSequence(0) { it + 1 }
        .map { "$input$it" }
        .map { it.md5() }
        .filter { it.startsWith("00000") }
        .take(8)
        .map { it[5] }
        .joinToString("")

    fun partTwo() = generateSequence(0) { it + 1 }
        .map { "$input$it" }
        .map { it.md5() }
        .filter { it.startsWith("00000") }
        .mapNotNull {
            val position = it[5]
            val char = it[6]
            if (position in '0'..'7') {
                position.digitToInt() to char
            } else {
                null
            }
        }
        .take(30) // TODO: find a better way to stop
        .fold(CharArray(8) { '_' }) { acc, (position, char) ->
            if (acc[position] == '_') {
                acc[position] = char
            }
            acc
        }
        .joinToString("")

}