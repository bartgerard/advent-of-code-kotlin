package aock2015

data class Year2015Day08(
    private val lines: List<String>
) {
    constructor(input: String) : this(input.split("\r\n"))

    fun partOne(): Int = lines.sumOf { it.length - representationLength(it) }

    fun partTwo(): Int {
        return 0
    }

    private fun representationLength(s: String): Int {
        return s
            .replace("\\\\", "?")
            .replace("\\\"", "?")
            .replace("""\\x..""".toRegex(), "?")
            .substring(1, s.length - 1)
            .length
    }

}