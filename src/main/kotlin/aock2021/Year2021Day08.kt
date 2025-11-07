package aock2021

import shared.sanitize

data class Year2021Day08(
    private val input: List<Note>
) {
    constructor(input: String) : this(
        input.sanitize().lines()
            .map { it.split(" | ") }
            .map {
                Note(
                    it[0].split(" "),
                    it[1].split(" ")
                )
            }
    )

    fun partOne() = input.sumOf { note -> note.fourDigitOutputValue.count { it.length in Note.uniqueSegmentLengths } }

    fun partTwo() = input.sumOf { note -> note.decodeOutput() }
}

data class Note(
    val signalPatterns: List<String>,
    val fourDigitOutputValue: List<String>
) {
    companion object {
        val segmentsByNumber = mapOf(
            0 to 6,
            1 to 2,
            2 to 5,
            3 to 5,
            4 to 4,
            5 to 5,
            6 to 6,
            7 to 3,
            8 to 7,
            9 to 6
        )
        val numbersWithUniqueSegmentLength = segmentsByNumber.filterValues { segmentLength ->
            segmentsByNumber.values.count { it == segmentLength } == 1
        }.keys
        val uniqueSegmentLengths = numbersWithUniqueSegmentLength.mapNotNull { segmentsByNumber[it] }.toSet()
    }

    fun decodeOutput(): Long {
        val digitToPattern = numbersWithUniqueSegmentLength.associateWith { number ->
            signalPatterns.first { it.length == segmentsByNumber[number] }.toSet()
        }
            .toMutableMap()

        // Identify patterns with 6 segments (0, 6, 9)
        val sixSegments = signalPatterns.filter { it.length == 6 }.map { it.toSet() }
        // 9 contains all segments of 4
        digitToPattern[9] = sixSegments.first { it.containsAll(digitToPattern[4]!!) }
        // 0 contains all segments of 1 (but not all of 4)
        digitToPattern[0] = sixSegments.first { it != digitToPattern[9] && it.containsAll(digitToPattern[1]!!) }
        // 6 is the remaining one
        digitToPattern[6] = sixSegments.first { it != digitToPattern[9] && it != digitToPattern[0] }

        // Identify patterns with 5 segments (2, 3, 5)
        val fiveSegments = signalPatterns.filter { it.length == 5 }.map { it.toSet() }
        // 3 contains all segments of 1
        digitToPattern[3] = fiveSegments.first { it.containsAll(digitToPattern[1]!!) }
        // 5 is contained in 6
        digitToPattern[5] = fiveSegments.first { it != digitToPattern[3] && digitToPattern[6]!!.containsAll(it) }
        // 2 is the remaining one
        digitToPattern[2] = fiveSegments.first { it != digitToPattern[3] && it != digitToPattern[5] }

        val patternToDigit = digitToPattern.entries.associate { (digit, pattern) -> pattern to digit }

        return fourDigitOutputValue
            .map { patternToDigit[it.toSet()]!! }
            .joinToString("")
            .toLong()
    }
}