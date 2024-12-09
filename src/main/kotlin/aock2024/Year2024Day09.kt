package aock2024

import shared.groupByIndexRemainder
import shared.sanitize

data class Year2024Day09(
    private val input: List<List<Int>>
) {
    constructor(input: String) : this(input.sanitize().chunked(1).map { it.toInt() }.groupByIndexRemainder(2))

    fun partOne(): Long {
        val files = input[0]
        val freeSpace = input[1]

        val disk = mutableListOf<Int?>()

        for (i in files.indices) {
            repeat(files[i]) { disk += i }

            if (i < freeSpace.size) {
                repeat(freeSpace[i]) { disk += null }
            }
        }

        var i = disk.indexOf(null)
        var j = disk.indexOfLast { it != null }

        while (i < j) {
            disk[i] = disk[j]
            disk[j] = null

            i = disk.indexOf(null)
            j = disk.indexOfLast { it != null }
        }

        //val unwrapped2 = files.mapIndexed { index, file ->
        //    index.toString().repeat(file) + if (index < freeSpace.size) ".".repeat(freeSpace[index]) else ""
        //}
        //    .joinToString(separator = "")

        return checksum(disk)
    }

    fun partTwo(): Int = 0

    private fun checksum(filesystem: MutableList<Int?>) = filesystem.filterNotNull()
        .mapIndexed { index, file -> index.toLong() * file }
        .sum()

}

interface Structure

data class File(
    val index: Int,
    val range: IntRange
) : Structure

data class FreeSpace(
    val index: Int,
    val length: Int
) : Structure
