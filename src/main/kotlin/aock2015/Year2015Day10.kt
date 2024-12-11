package aock2015

data class Year2015Day10(
    private val input: String
) {
    companion object {
        val NUMBER_GROUP = "(\\d)\\1*".toRegex()
    }

    fun lookAndSay(repetitions: Int): Int {
        var result = input
        repeat(repetitions) { result = lookAndSay(result) }
        return result.length
    }

    fun lookAndSay(text: String) = NUMBER_GROUP.findAll(text)
        .map { it.groupValues[0].length to it.groupValues[1] }
        .map { (length, value) -> "$length$value" }
        .joinToString("")
}