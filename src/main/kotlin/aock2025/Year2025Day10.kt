package aock2025

import shared.Dijkstra
import shared.Solution
import shared.sanitize
import shared.toIntegers

data class Year2025Day10(
    private val factories: List<Factory>
) {

    constructor(input: String) : this(input.sanitize().lines().map { Factory.parse(it) })

    fun partOne() = factories.sumOf { it.findFewestButtonsPresses() }
    fun partTwo() = 0L
}

data class Factory(
    val indicatorLights: IndicatorLights,
    val buttons: List<Button>
) {
    companion object {
        private val INDICATOR_LIGHTS_REGEX = "\\[(.+)]".toRegex()
        private val BUTTONS_REGEX = "\\(([0-9,]+)\\)".toRegex()

        fun parse(line: String): Factory {
            return Factory(
                IndicatorLights(INDICATOR_LIGHTS_REGEX.findAll(line).map { it.groupValues[1] }.first()),
                Button.parse(BUTTONS_REGEX.findAll(line).map { it.value }.toList())
            )
        }
    }

    fun findFewestButtonsPresses(): Long {
        val shortestPath: Solution<String> = Dijkstra.findShortestPath(
            indicatorLights.initial(),
            { it == indicatorLights.lights },
            { _, current ->
                buttons.map { button -> current.apply(button) }
            }
        )

        return shortestPath.cost()
    }
}

data class Button(
    val lightToggles: Set<Int>
) {
    companion object {
        fun parse(buttonsAsStrings: List<String>): List<Button> = buttonsAsStrings
            .map { Button(it.toIntegers().toSet()) }
    }
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

fun String.apply(button: Button): String = this.mapIndexed { index, light ->
    if (button.lightToggles.contains(index)) {
        IndicatorLight.flip(light)
    } else {
        light
    }
}
    .joinToString("")