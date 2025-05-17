package aock2024

import shared.groupByIndexRemainder
import shared.length
import shared.merge
import shared.mergeOne
import shared.sanitize
import shared.sum
import kotlin.math.min

data class Year2024Day09(
    private val disk: Disk,
    private val input: List<List<Int>>
) {
    constructor(input: String) : this(
        Disk.parse(input),
        input.sanitize().chunked(1).map { it.toInt() }.groupByIndexRemainder(2)
    )

    fun partOne() = disk.also { it.compact(false) }.checksum()

    fun partTwo() = disk.also { it.compact(true) }.checksum()

}

data class Disk(
    val files: MutableList<File>,
    val freeSpaces: MutableList<LongRange>,
    val formattedFiles: MutableList<File> = mutableListOf(),
) {
    companion object {
        fun parse(input: String): Disk {
            val files = mutableListOf<File>()
            val spaces = mutableListOf<LongRange>()

            val values = input.sanitize().chunked(1)
            var position = 0L

            for (index in values.indices) {
                val length = values[index].toInt()
                val indices = position..<position + length

                when (index % 2) {
                    0 -> files += File(index / 2, indices)
                    1 -> spaces += indices
                }

                position += length
            }

            return Disk(files, spaces.merge().toMutableList())
        }
    }

    fun checksum() = formattedFiles.sumOf { it.checksum() }

    fun moveLastFile(shouldFit: Boolean) {
        val file = files.removeLast()

        val selectedSpace = if (shouldFit) {
            freeSpaces.firstOrNull { file.range.length() <= it.length() }
        } else {
            freeSpaces.firstOrNull()
        }

        if (selectedSpace == null || file.range.last < selectedSpace.start) {
            formattedFiles += file
            return
        } else {
            freeSpaces.remove(selectedSpace)
        }

        val fileLength = file.range.length()
        val freeSpaceLength = selectedSpace.length()

        val min = min(fileLength, freeSpaceLength)
        val difference = fileLength - freeSpaceLength

        when {
            difference > 0 -> files += File(file.fileId, file.range.start..<(file.range.start + difference))
            difference < 0 -> freeSpaces.addFirst((selectedSpace.start + min)..selectedSpace.last)
        }

        formattedFiles += File(file.fileId, selectedSpace.start..<(selectedSpace.start + min))
        freeSpaces.mergeOne((file.range.last - min + 1)..file.range.last)
    }

    fun compact(shouldFit: Boolean) {
        while (!files.isEmpty()) {
            moveLastFile(shouldFit)
        }
    }

    //fun print(): String = formattedFiles.sortedBy { it.range.start }
    //    .joinToString(separator = "") { file -> file.fileId.toString().repeat(file.range.length().toInt()) }
}

data class File(
    val fileId: Int,
    val range: LongRange
) {
    fun checksum() = fileId * sum(range)
}

/*

// (slow part 1)

val groupedByRemainder = input.sanitize().chunked(1).map { it.toInt() }.groupByIndexRemainder(2)

val files = groupedByRemainder[0]
val freeSpace = groupedByRemainder[1]

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
 */