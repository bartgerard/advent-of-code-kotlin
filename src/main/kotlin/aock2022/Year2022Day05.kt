package aock2022

import shared.sanitize
import shared.splitByEmptyLine
import shared.toIntegers
import shared.transpose
import java.util.*

data class Year2022Day05(
    private val stacks: SupplyStacks,
    private val procedures: List<RearrangementProcedure>,
) {
    companion object {
        fun parseStacks(input: String): SupplyStacks {
            val stacks = input.lines().last().toIntegers().map { SupplyStack(it) }

            val crates = input.lines()
                .reversed()
                .drop(1)
                .map { line -> line.chunked(4).map { it[1] } }
                .transpose()
                .map { line -> line.filter { it != ' ' } }

            stacks.zip(crates).forEach { (stack, crates) -> stack.addAll(crates) }

            return SupplyStacks(stacks)
        }

        fun parseProcedures(procedure: String) = procedure.lines()
            .map { line -> line.toIntegers().let { RearrangementProcedure(it[0], it[1], it[2]) } }
    }

    constructor(input: String) : this(input.sanitize().splitByEmptyLine())

    constructor(input: List<String>) : this(
        parseStacks(input[0]),
        parseProcedures(input[1])
    )

    fun partOne(): String {
        stacks.moveOneByOne(procedures)
        return stacks.topCrates().joinToString("")
    }

    fun partTwo(): String {
        stacks.moveAllAtOnce(procedures)
        return stacks.topCrates().joinToString("")
    }
}

data class SupplyStacks(
    private val stacks: List<SupplyStack>,
) {
    fun moveOneByOne(procedures: List<RearrangementProcedure>) {
        procedures.forEach { stacks[it.to - 1].addAll(stacks[it.from - 1].pop(it.times)) }
    }

    fun moveAllAtOnce(procedures: List<RearrangementProcedure>) {
        procedures.forEach { stacks[it.to - 1].addAll(stacks[it.from - 1].pop(it.times).reversed()) }
    }

    fun topCrates() = stacks.map { it.top() }
}

data class SupplyStack(
    private val id: Int,
    private val crates: Stack<Char> = Stack(),
) {
    fun addAll(crates: Collection<Char>) {
        this.crates.addAll(crates)
    }

    fun pop(times: Int) = (1..times).map { this.crates.pop() }

    fun top(): Char = this.crates.peek()
}

data class RearrangementProcedure(
    val times: Int,
    val from: Int,
    val to: Int,
)