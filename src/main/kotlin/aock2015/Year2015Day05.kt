package aock2015

import shared.sanitize

data class Year2015Day05(
    private val strings: List<String>
) {
    companion object {
        private const val VOWELS = "aeiou"
        private val DOUBLE_LETTER_REGEX = "(\\w)\\1".toRegex()
        private val NAUGHTY_SEQUENCES = listOf("ab", "cd", "pq", "xy")
        private val REPEATING_PAIR_REGEX = "(\\w\\w).*\\1".toRegex()
        private val REPEATING_LETTER_WITH_ONE_LETTER_BETWEEN_REGEX = "(\\w)\\w\\1".toRegex()
    }

    constructor(input: String) : this(input.sanitize().lines())

    fun partOne() = strings.count { isNice(it) }

    fun partTwo() = strings.count { isNiceAccordingToNewRules(it) }

    private fun isNice(text: String) = containsAtLeastThreeVowels(text)
            && containsDoubleLetter(text)
            && NAUGHTY_SEQUENCES.none { text.contains(it) }

    private fun containsAtLeastThreeVowels(text: String) = text.count { VOWELS.contains(it) } >= 3

    private fun containsDoubleLetter(text: String) = text.contains(DOUBLE_LETTER_REGEX)

    private fun isNiceAccordingToNewRules(text: String) = text.contains(REPEATING_PAIR_REGEX)
            && text.contains(REPEATING_LETTER_WITH_ONE_LETTER_BETWEEN_REGEX)

}