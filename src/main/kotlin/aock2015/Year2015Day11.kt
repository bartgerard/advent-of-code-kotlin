package aock2015

data class Year2015Day11(
    private val password: String
) {

    fun partOne(): String {
        val a = PasswordGenerator.generate(password).take(1)
        println(a)
        return ""
    }

    fun partTwo(): Long = 0
}

class PasswordGenerator() {
    companion object {
        val UNSAFE_CHARACTERS = listOf('i', 'o', 'l')
        val ALPHABET = ('a'..'z').toList()
        val SAFE_CHARACTERS = ALPHABET - UNSAFE_CHARACTERS
        val DOUBLES = SAFE_CHARACTERS.map { it.toString().repeat(2) }.toSet()
        val STRAIGHT_2 = (ALPHABET + 'a').zipWithNext { a, b -> "$a$b" }
        val STRAIGHT_3 = STRAIGHT_2.zip(ALPHABET - listOf('a', 'b')) { a, b -> "$a$b" }.toSet()

        fun generate(password: String) = generateSequence(password) { next(it) }

        fun next(s: String): String = when (val next = next(s.last())) {
            'a' -> next(s.dropLast(1)) + next
            else -> s.dropLast(1) + next
        }

        fun next(c: Char) = when (c) {
            'z' -> 'a'
            in UNSAFE_CHARACTERS -> c + 2
            else -> c + 1
        }

        fun isValid(s: String): Boolean = when (s) {
            else -> true
        }
    }
}