package aock2016

import shared.sanitize

data class Year2016Day04(
    private val input: List<Room>
) {
    constructor(input: String) : this(input.sanitize().lines().map { Room.parse(it) })

    fun partOne() = input.filter { it.isValid() }.sumOf { it.sectorId }
    fun partTwo() = input.first { it.name().contains("northpole object") }.sectorId
}

data class Room(
    val encryptedName: String,
    val sectorId: Long,
    val checksum: String
) {
    companion object {
        fun parse(input: String): Room {
            val regex = """([a-z-]+)-(\d+)\[([a-z]+)]""".toRegex()
            val (encryptedName, sectorId, checksum) = regex.matchEntire(input)!!.destructured
            return Room(encryptedName, sectorId.toLong(), checksum)
        }
    }

    fun isValid(): Boolean {
        val letterFrequencies = encryptedName.filter { it != '-' }
            .groupingBy { it }
            .eachCount()

        val sortedLetters = letterFrequencies.entries
            .sortedWith(compareByDescending<Map.Entry<Char, Int>> { it.value }.thenBy { it.key })
            .map { it.key }
            .take(5)
            .joinToString("")

        return sortedLetters == checksum
    }

    fun name(): String = encryptedName
        .map { shift(it, sectorId) }
        .joinToString("")

    private fun shift(
        char: Char,
        shift: Long
    ): Char = when (char) {
        '-' -> ' '
        else -> ('a' + ((char - 'a') + (shift % 26)).toInt() % 26)
    }
}