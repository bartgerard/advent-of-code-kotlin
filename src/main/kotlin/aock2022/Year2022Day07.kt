package aock2022

import shared.sanitize

data class Year2022Day07(
    private val terminalOutput: List<String>,
    private val fileSystem: FileSystem = FileSystem()
) {
    constructor(input: String) : this(input.sanitize().lines())

    fun partOne(): Long {
        fileSystem.navigate(terminalOutput)
        return fileSystem.root.allDirectories()
            .map { it.size() }
            .filter { it <= 100000 }
            .sum()
    }

    fun partTwo(): Long {
        fileSystem.navigate(terminalOutput)

        val sizeDiskSpace = 70000000
        val sizeUpdate = 30000000
        val sizeInUse = fileSystem.root.size()
        val sizeUnused = sizeDiskSpace - sizeInUse
        val sizeToFreeUp = sizeUpdate - sizeUnused

        return fileSystem.root.allDirectories()
            .map { it.size() }
            .filter { it >= sizeToFreeUp }
            .min()
    }
}

data class FileSystem(
    val root: Directory = Directory(null, "/")
) {
    fun navigate(terminalOutput: List<String>) {
        var currentDirectory = root

        for (output in terminalOutput) {
            when {
                output.startsWith("$ cd ") -> {
                    val path = output.substring(5)
                    currentDirectory = when (path) {
                        "/" -> root
                        ".." -> currentDirectory.up()
                        else -> currentDirectory.addDirectory(path)
                    }
                }

                output.startsWith("$ ls") -> {
                    // no-op
                }

                output.startsWith("dir") -> currentDirectory.addDirectory(output.substring(4))

                else -> {
                    val (size, filename) = output.split(' ')
                    currentDirectory.addFile(filename, size.toLong())
                }
            }
        }
    }
}

data class Directory(
    val parent: Directory?,
    val path: String,
    val subDirectories: MutableMap<String, Directory> = mutableMapOf(),
    val files: MutableMap<String, Long> = mutableMapOf(),
) {
    fun up() = parent ?: this
    fun addDirectory(path: String) = subDirectories.computeIfAbsent(path) { key -> Directory(this, key) }
    fun addFile(filename: String, size: Long) = files.putIfAbsent(filename, size)
    fun size(): Long = files.values.sum() + subDirectories.values.sumOf { it.size() }

    fun allDirectories(): List<Directory> = subDirectories.values.flatMap { it.allDirectories() } + this

    override fun toString() = path
}