package aock2020

import shared.sanitize
import shared.splitByEmptyLine

data class Year2020Day04(
    private val input: List<Map<String, String>>
) {

    companion object {
        val FIELDS: Map<String, (String) -> Boolean> = mapOf(
            Pair("byr", { it.length == 4 && it.toIntOrNull() in 1920..2002 }), // (Birth Year)
            Pair("iyr", { it.length == 4 && it.toIntOrNull() in 2010..2020 }), // (Issue Year)
            Pair("eyr", { it.length == 4 && it.toIntOrNull() in 2020..2030 }), // (Expiration Year)
            Pair("hgt", {
                when (it.takeLast(2)) {
                    "cm" -> it.removeSuffix("cm").toIntOrNull() in 150..193
                    "in" -> it.removeSuffix("in").toIntOrNull() in 59..76
                    else -> false
                }
            }), // (Height)
            Pair("hcl", { it matches "#[0-9a-f]{6}".toRegex() }), // (Hair Color)
            Pair("ecl", { it in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") }), // (Eye Color)
            Pair("pid", { it.length == 9 && it.all(Char::isDigit) }), // (Passport ID)
            //"cid", // (Country ID)
        )
    }

    constructor(input: String) : this(
        input.sanitize()
            .splitByEmptyLine()
            .map { line ->
                line.split(" ", "\n").associate {
                    val (key, value) = it.split(":")
                    key to value
                }
            }
    )

    fun partOne() = input.count { it.keys.containsAll(FIELDS.keys) }
    fun partTwo() = input.count {
        it.keys.containsAll(FIELDS.keys)
                && it.filter { (key, _) -> FIELDS.containsKey(key) }
            .all { (key, value) -> FIELDS[key]!!.invoke(value) }
    }
}