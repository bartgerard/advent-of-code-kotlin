package aock2024

import shared.*
import kotlin.math.min

data class Year2024Day09(
    private val disk: Disk,
    private val input: List<List<Int>>
) {
    constructor(input: String) : this(
        Disk.parse(input),
        input.sanitize().chunked(1).map { it.toInt() }.groupByIndexRemainder(2)
    )
    /*
    00...111...2...333.44.5555.6666.777.888899
    009..111...2...333.44.5555.6666.777.88889.
    0099.111...2...333.44.5555.6666.777.8888..
    00998111...2...333.44.5555.6666.777.888...
    009981118..2...333.44.5555.6666.777.88....
    0099811188.2...333.44.5555.6666.777.8.....
    009981118882...333.44.5555.6666.777.......
    0099811188827..333.44.5555.6666.77........
    00998111888277.333.44.5555.6666.7.........
    009981118882777333.44.5555.6666...........
    009981118882777333644.5555.666............
    00998111888277733364465555.66.............
    0099811188827773336446555566..............
     */

    fun partOne(): Long = disk.also { it.compact(false) }.checksum()

    fun partTwo(): Long = disk.also { it.compact(true) }.checksum()

}

data class Disk(
    val files: MutableList<File>,
    var freeSpaces: MutableList<LongRange>,
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

    fun checksum(): Long = formattedFiles.sumOf { it.checksum() }

    fun move(index: Int, shouldFit: Boolean) {
        val file = files[index]
        files.removeAt(index)

        if (freeSpaces.isEmpty()) {
            formattedFiles += file
            return
        }

        val selectedSpace = if (shouldFit) {
            freeSpaces.firstOrNull { file.range.length() <= it.length() }
        } else {
            freeSpaces.firstOrNull()
        }
        freeSpaces.remove(selectedSpace)

        if (selectedSpace == null || file.range.last < selectedSpace.start) {
            formattedFiles += file
            return
        }

        val fileLength = file.range.length()
        val freeLength = selectedSpace.length()

        val min = min(freeLength, fileLength)
        val difference = fileLength - freeLength

        when {
            difference > 0 -> files += File(file.fileId, file.range.start..<(file.range.start + difference))
            difference < 0 -> freeSpaces.addFirst((selectedSpace.start + min)..selectedSpace.last)
        }

        formattedFiles += File(file.fileId, selectedSpace.start..<(selectedSpace.start + min))

        val oldFileRange = (file.range.last - min + 1)..file.range.last
        freeSpaces = freeSpaces.mergeOne(oldFileRange).toMutableList()
    }

    fun compact(shouldFit: Boolean) {
        while (!files.isEmpty()) {
            move(files.lastIndex, shouldFit)
        }
    }

    fun print(): String = formattedFiles.sortedBy { it.range.start }
        .joinToString(separator = "") { file -> file.fileId.toString().repeat(file.range.length().toInt()) }
}

data class File(
    val fileId: Int,
    val range: LongRange
) {
    fun checksum(): Long = fileId * sum(range)
}

/*

original -> slow

input.sanitize().chunked(1).map { it.toInt() }.groupByIndexRemainder(2)


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
 */