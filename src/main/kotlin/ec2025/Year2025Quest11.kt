package ec2025

import shared.sanitize
import shared.toLongs
import kotlin.math.abs

data class Year2025Quest11(
    private val flock: List<Long>
) {
    companion object {
        fun checksum(flock: List<Long>): Long = flock.mapIndexed { index, ducks -> ducks * (index + 1) }.sum()
    }

    constructor(input: String) : this(input.sanitize().toLongs())

    fun partOne() = checksum(simulateRounds(flock, 10))
    fun partTwo() = countRoundsToStabilize(flock)
    fun partThree(): Long {
        val average = flock.sum() / flock.size
        return flock.sumOf { abs(it - average) } / 2L
    }

    fun simulateRounds(flock: List<Long>, rounds: Int): List<Long> {
        val newFlock = flock.toMutableList()

        var direction = Direction.RIGHT
        var round = 0L

        while (round < rounds) {
            val moved = move(newFlock, direction)

            if (moved) {
                round++
            } else if (direction == Direction.RIGHT) {
                direction = Direction.LEFT
            } else {
                break
            }
        }

        return newFlock
    }

    fun countRoundsToStabilize(flock: List<Long>): Long {
        val newFlock = flock.toMutableList()

        var direction = Direction.RIGHT
        var rounds = 0L

        while (true) {
            val moved = move(newFlock, direction)

            if (moved) {
                rounds++
            } else if (direction == Direction.RIGHT) {
                direction = Direction.LEFT
            } else {
                break
            }
        }

        return rounds
    }

    private fun move(
        flock: MutableList<Long>,
        direction: Direction
    ): Boolean {
        var moved = false

        for (i in 1..<flock.size) {
            when (direction) {
                Direction.RIGHT -> {
                    if (flock[i - 1] > flock[i]) {
                        flock[i - 1] -= 1
                        flock[i] += 1
                        moved = true
                    }
                }

                Direction.LEFT -> {
                    if (flock[i - 1] < flock[i]) {
                        flock[i - 1] += 1
                        flock[i] -= 1
                        moved = true
                    }
                }
            }
        }

        return moved
    }
}

enum class Direction {
    LEFT,
    RIGHT
}