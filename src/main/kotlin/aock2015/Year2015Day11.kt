package aock2015

import shared.Alphabet

data class Year2015Day11(
    private val password: String
) {
    fun partOne(): String = PasswordGenerator.generate(password).first()
    fun partTwo(): String = PasswordGenerator.generate(password).drop(1).first()
}

class PasswordGenerator {
    companion object {
        val UNSAFE_CHARACTERS = listOf('i', 'o', 'l')
        val SAFE_CHARACTERS = Alphabet.LOWER_CASE - UNSAFE_CHARACTERS
        val DOUBLES = SAFE_CHARACTERS.map { it.toString().repeat(2) }.toSet()
        val STRAIGHT_2 = Alphabet.LOWER_CASE.zipWithNext { a, b -> "$a$b" }
        val STRAIGHT_3 = STRAIGHT_2.zip(Alphabet.LOWER_CASE - listOf('a', 'b')) { a, b -> "$a$b" }.toSet()

        fun generate(password: String) = generateSequence(password) { next(it) }
            .filter { isValid(it) }

        fun next(s: String): String = when (val next = next(s.last())) {
            'a' -> next(s.dropLast(1)) + next
            else -> s.dropLast(1) + next
        }

        fun next(c: Char): Char = when (c) {
            'z' -> 'a'
            else -> c + 1
        }

        fun isValid(s: String) = s.none { it in UNSAFE_CHARACTERS }
                && s.windowed(2).filter { it in DOUBLES }.toSet().size >= 2
                && s.windowed(3).any { STRAIGHT_3.contains(it) }
    }
}