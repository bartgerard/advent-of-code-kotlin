package aock2015

import shared.cyclic

data class Year2015Day11(
    private val generator: PasswordGenerator
) {
    constructor(input: String) : this(PasswordGenerator.parse(input))

    fun partOne(): String {
        generator.next()
        println(generator.get())

        return ""
    }

    fun partTwo(): Long = 0
}

data class PasswordGenerator(
    var seed: Int
) {
    companion object {
        val UNSAFE_CHARACTERS = listOf('a', 'o', 'l')
        val CHARACTERS = ('a'..'z').toList().cyclic()

        fun parse(input: String): PasswordGenerator = input.toList()
            .reversed()
            .mapIndexed { i, character -> CHARACTERS.indexOf(character) + i * CHARACTERS.size }
            .sum()
            .let { PasswordGenerator(it) }
    }

    fun next() {
        seed++
    }

    fun get(): String {
        val length = seed / CHARACTERS.size

        return (1..length).map { CHARACTERS[seed % (it * 26)] }.reversed().joinToString("")
    }
}