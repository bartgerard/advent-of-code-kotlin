package ec2025

import shared.floorMod
import shared.geometry2d.Vector2dInt
import shared.sanitize
import shared.spatial.Direction
import shared.splitByEmptyLine

data class Year2025Quest01(
    private val names: List<String>,
    private val instructions: List<Vector2dInt>
) {
    constructor(input: String) : this(input.sanitize().splitByEmptyLine())
    constructor(input: List<String>) : this(
        input[0].split(","),
        input[1].split(",")
            .map { Vector2dInt.forDirection(Direction.parse(it[0])) * it.substring(1).toInt() }
    )

    fun partOne(): String {
        val range = 0..<names.size
        val index = instructions.fold(0) { acc, instruction -> (acc + instruction.x).coerceIn(range) }
        return names[index]
    }

    fun partTwo(): String {
        val index = instructions.sumOf { it.x } % names.size
        return names[index]
    }

    fun partThree(): String {
        val names = names.toMutableList()
        instructions.forEach { (x, _) ->
            val index = x.floorMod(names.size)
            val name = names[index]
            names[index] = names[0]
            names[0] = name
        }
        return names[0]
    }
}