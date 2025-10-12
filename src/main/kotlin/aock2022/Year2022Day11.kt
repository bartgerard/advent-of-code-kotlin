package aock2022

import shared.sanitize
import shared.splitByEmptyLine
import shared.toLongs

data class Year2022Day11(
    private val monkeys: Monkeys
) {
    constructor(input: String) : this(
        input.sanitize().splitByEmptyLine()
            .map { Monkey.parse(it.lines()) }
            .let { Monkeys(it) }
    )

    fun partOne(): Long {
        repeat(20) {
            monkeys.round()
        }
        return monkeys.levelOfMonkeyBusiness()
    }

    fun partTwo(): Long {
        val reliefMethod: (Long) -> Long = { it % monkeys.reliefDivisor() }
        repeat(10_000) {
            monkeys.round(reliefMethod)
        }
        return monkeys.levelOfMonkeyBusiness()
    }
}

data class Monkeys(
    private val monkeys: List<Monkey>,
    private val monkeyBusiness: MutableMap<Int, Long> = mutableMapOf()
) {
    fun round(reliefMethod: (Long) -> Long = { it / 3L }) {
        monkeys.forEach { round(it, reliefMethod) }
    }

    fun round(
        monkey: Monkey,
        reliefMethod: (Long) -> Long
    ) {
        val items = monkey.items.toList()
        monkey.items.clear()
        monkeyBusiness[monkey.id] = (monkeyBusiness[monkey.id] ?: 0L) + items.size

        for (oldWorryLevel in items) {
            val worryLevel = monkey.operation(oldWorryLevel)
            val newWorryLevel = reliefMethod(worryLevel)
            val testResult = newWorryLevel % monkey.testDivisor == 0L

            val nextMonkeyId = monkey.nextMonkey(testResult)
            monkeys[nextMonkeyId].items.add(newWorryLevel)
        }
    }

    fun levelOfMonkeyBusiness() = monkeyBusiness.values
        .sortedDescending()
        .take(2)
        .fold(1L, Long::times)

    fun reliefDivisor() = monkeys.map { it.testDivisor }
        .fold(1, Long::times)
}

data class Monkey(
    val id: Int,
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val testDivisor: Long,
    val nextMonkey: (Boolean) -> Int
) {
    companion object {
        fun parse(lines: List<String>): Monkey {
            val id = lines[0].substringAfter("Monkey ").substringBefore(":").trim().toInt()
            val startingItems = lines[1].toLongs()
            val (operator, value) = lines[2].substringAfter("Operation: new = old ").split(" ")
            val operand = if (value == "old") null else value.toLong()

            val operation: (Long) -> Long = when (operator) {
                "*" -> { old -> old * (operand ?: old) }
                else -> { old -> old + (operand ?: old) }
            }

            val testDivisor = lines[3].substringAfter("Test: divisible by ").toLong()
            val nextMonkeyIfTrue = lines[4].substringAfter("If true: throw to monkey ").toInt()
            val nextMonkeyIfFalse = lines[5].substringAfter("If false: throw to monkey ").toInt()
            val nextMonkey: (Boolean) -> Int = { if (it) nextMonkeyIfTrue else nextMonkeyIfFalse }

            return Monkey(id, startingItems.toMutableList(), operation, testDivisor, nextMonkey)
        }
    }
}