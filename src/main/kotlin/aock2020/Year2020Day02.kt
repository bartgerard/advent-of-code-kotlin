package aock2020

import shared.sanitize

data class Year2020Day02(
    private val input: List<Pair<String, PasswordPolicy>>
) {
    constructor(input: String) : this(
        input.sanitize().lines()
            .map { it.split("-", ": ", " ") }
            .map { (min, max, char, password) ->
                password to PasswordPolicy(
                    min.toInt()..max.toInt(),
                    char.first()
                )
            }
    )

    fun partOne() = input.count { it.second.validateOld(it.first) }
    fun partTwo() = input.count { it.second.validateNew(it.first) }


}

data class PasswordPolicy(
    private val range: IntRange,
    private val character: Char
) {
    fun validateOld(password: String) = password.count { it == character } in range
    fun validateNew(password: String) = sequenceOf(range.first, range.last)
        .map { password[it - 1] }
        .filter { it == character }
        .count() == 1
}