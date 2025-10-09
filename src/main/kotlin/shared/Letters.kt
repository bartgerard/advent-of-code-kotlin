package shared

private const val FIRST_LETTER_LOWER_CASE = 'a'
private const val FIRST_LETTER_UPPER_CASE = 'A'

fun positionInAlphabet(c: Char) = c.code - firstLetterByCase(c).code + 1

fun firstLetterByCase(c: Char) = if (c.isLowerCase()) FIRST_LETTER_LOWER_CASE else FIRST_LETTER_UPPER_CASE