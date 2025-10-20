package shared

class Alphabet {
    companion object {

        private const val FIRST_LETTER_LOWER_CASE = 'a'
        private const val FIRST_LETTER_UPPER_CASE = 'A'

        val LOWER_CASE = (FIRST_LETTER_LOWER_CASE..'z').toList()
        val UPPER_CASE = (FIRST_LETTER_UPPER_CASE..'Z').toList()

        fun positionOf(c: Char) = c.code - firstLetterByCase(c).code + 1

        fun firstLetterByCase(c: Char) = if (c.isLowerCase()) FIRST_LETTER_LOWER_CASE else FIRST_LETTER_UPPER_CASE
    }
}