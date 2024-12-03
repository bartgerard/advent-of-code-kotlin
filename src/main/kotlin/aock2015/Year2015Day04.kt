package aock2015

import shared.md5
import shared.times

data class Year2015Day04(
    private val input: String
) {
    fun partOne(): Int = lowestMd5StartingWith("0" * 5)

    fun partTwo(): Int = lowestMd5StartingWith("0" * 6)

    private fun lowestMd5StartingWith(sequence: String) = (1..Int.MAX_VALUE)
        .first { (input + it).md5().startsWith(sequence) }

}