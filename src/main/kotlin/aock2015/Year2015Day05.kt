package aock2015

import shared.byLine

data class Year2015Day05(
    private val strings: List<String>
) {
    companion object {
        private const val VOWELS = "aeiou"
        private val DOUBLE_LETTER_REGEX = "(\\w)\\1".toRegex()
        private val NAUGHTY_SEQUENCES: List<String> = listOf("ab", "cd", "pq", "xy")
        private val REPEATING_PAIR_REGEX = "(\\w\\w).*\\1".toRegex()
        private val REPEATING_LETTER_WITH_ONE_LETTER_BETWEEN_REGEX = "(\\w)\\w\\1".toRegex()
    }

    constructor(input: String) : this(input.byLine())

    fun partOne(): Int = strings.count { isNice(it) }

    fun partTwo(): Int = strings.count { isNiceAccordingToNewRules(it) }

    private fun isNice(text: String): Boolean = containsAtLeastThreeVowels(text)
            && containsDoubleLetter(text)
            && NAUGHTY_SEQUENCES.none { text.contains(it) }

    private fun containsAtLeastThreeVowels(text: String) = text.count { VOWELS.contains(it) } >= 3

    private fun containsDoubleLetter(text: String) = text.contains(DOUBLE_LETTER_REGEX)

    private fun isNiceAccordingToNewRules(text: String): Boolean = text.contains(REPEATING_PAIR_REGEX)
            && text.contains(REPEATING_LETTER_WITH_ONE_LETTER_BETWEEN_REGEX)

}