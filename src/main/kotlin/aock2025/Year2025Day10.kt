package aock2025

import com.microsoft.z3.Context
import com.microsoft.z3.IntExpr
import com.microsoft.z3.IntNum
import com.microsoft.z3.Status
import shared.Dijkstra
import shared.Solution
import shared.SystemOfLinearEquations
import shared.gcd
import shared.sanitize
import shared.toIntegers
import shared.toLongs
import shared.transpose

data class Year2025Day10(
    private val factories: List<Factory>
) {

    constructor(input: String) : this(input.sanitize().lines().map { Factory.parse(it) })

    fun partOne() = factories.sumOf { it.findFewestButtonsPressesForIndicatorLights() }
    fun partTwo() = factories.sumOf { it.findFewestButtonsPressesForJoltageRequirements() }
}

data class Factory(
    val indicatorLights: IndicatorLights,
    val buttons: List<Button>,
    val joltageRequirements: JoltageRequirements
) {
    companion object {
        private val INDICATOR_LIGHTS_REGEX = "\\[(.+)]".toRegex()
        private val BUTTONS_REGEX = "\\(([0-9,]+)\\)".toRegex()
        private val JOLTAGE_REQUIREMENTS_REGEX = "\\{([0-9,]+)}".toRegex()

        fun parse(line: String): Factory = Factory(
            IndicatorLights(INDICATOR_LIGHTS_REGEX.findAll(line).map { it.groupValues[1] }.first()),
            Button.parse(BUTTONS_REGEX.findAll(line).map { it.value }.toList()),
            JoltageRequirements(JOLTAGE_REQUIREMENTS_REGEX.findAll(line).map { it.groupValues[1] }.first().toLongs())
        )
    }

    fun findFewestButtonsPressesForIndicatorLights(): Long {
        val shortestPath: Solution<String> = Dijkstra.findShortestPath(
            indicatorLights.initial(),
            { it == indicatorLights.lights },
            { _, current ->
                buttons.map { button -> current.apply(button) }
            }
        )

        return shortestPath.cost()
    }

    fun findFewestButtonsPressesForJoltageRequirements(): Long {
        return Context().use { ctx ->
            val unknowns = buttons.indices.map { "x$it" }

            val variables: Map<String, IntExpr> = buildMap {
                putAll(unknowns.associateWith { ctx.mkIntConst(it) })
            }

            val optimizer = ctx.mkOptimize()

            unknowns.map { variables[it] }.forEach {
                optimizer.Add(ctx.mkGe(it, ctx.mkInt(0)))
            }

            val equations = joltageRequirements.requirements.mapIndexed { i, requirement ->
                val coefficients: Array<IntExpr> = buttons.withIndex()
                    .filter { (_, button) -> button.toggles.contains(i) }
                    .map { (index, _) -> variables["x$index"]!! }
                    .toTypedArray()

                ctx.mkEq(
                    ctx.mkAdd(*coefficients),
                    ctx.mkInt(requirement)
                )
            }

            equations.forEach { optimizer.Add(it) }

            val sum = ctx.mkAdd(*unknowns.map { variables[it] }.toTypedArray())
            optimizer.MkMinimize(sum)

            when (optimizer.Check()) {
                Status.SATISFIABLE -> {
                    unknowns
                        .map { optimizer.model.eval(variables[it], false) as IntNum }
                        .sumOf { it.int64 }
                }

                else -> error("No solution found!")
            }
        }
    }

    fun findFewestButtonsPressesForJoltageRequirementsV2(): Long {
        val size = joltageRequirements.requirements.size

        val equations = buttons.map { button -> button.toEquations(size) }
            .transpose()

        val solution = SystemOfLinearEquations.solve(
            equations,
            joltageRequirements.requirements
        )

        return 0L
    }

    fun findFewestButtonsPressesForJoltageRequirementsV1(): Long {
        val gcd = joltageRequirements.requirements.gcd()

        val shortestPath: Solution<List<Long>> = Dijkstra.findShortestPath(
            joltageRequirements.initial(),
            { it == joltageRequirements.requirements },
            { _, current ->
                buttons.map { button -> current.apply(button) }
                    .filter { JoltageRequirements.isBelowOrEqualToMax(it, joltageRequirements.requirements) }
            }
        )

        return shortestPath.cost()
    }

}

data class Button(
    val toggles: Set<Int>
) {
    companion object {
        fun parse(buttonsAsStrings: List<String>): List<Button> = buttonsAsStrings
            .map { Button(it.toIntegers().toSet()) }
    }

    fun toEquations(length: Int) = (0..<length)
        .map { index -> if (index in toggles) 1 else 0 }
}

data class IndicatorLights(
    val lights: String
) {
    fun initial() = IndicatorLight.OFF.symbol.toString().repeat(lights.length)
}

enum class IndicatorLight(
    val symbol: Char
) {
    ON('#'),
    OFF('.');

    companion object {
        fun flip(symbol: Char) = when (symbol) {
            ON.symbol -> OFF.symbol
            OFF.symbol -> ON.symbol
            else -> error("Unknown symbol: $symbol")
        }
    }
}

private fun String.apply(button: Button): String = this.mapIndexed { index, light ->
    if (button.toggles.contains(index)) {
        IndicatorLight.flip(light)
    } else {
        light
    }
}
    .joinToString("")

data class JoltageRequirements(
    val requirements: List<Long>
) {
    companion object {
        fun isBelowOrEqualToMax(
            currentRequirements: List<Long>,
            maxRequirements: List<Long>
        ): Boolean {
            return currentRequirements.zip(maxRequirements).all { (current, max) -> current <= max }
        }
    }

    fun initial() = requirements.map { 0L }
}

private fun List<Long>.apply(button: Button): List<Long> = this.mapIndexed { index, requirement ->
    if (button.toggles.contains(index)) {
        requirement + 1
    } else {
        requirement
    }
}