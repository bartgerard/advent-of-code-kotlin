package ec2025

import shared.ComplexNumber
import shared.sanitize

data class Year2025Quest02(
    private val input: Map<String, ComplexNumber>
) {
    companion object {

        private fun cycle1(previous: ComplexNumber, coordinate: ComplexNumber): ComplexNumber {
            val step1 = previous * previous
            val step2 = step1 / ComplexNumber(10, 10)
            val step3 = step2 + coordinate
            return step3
        }

        private fun cycle2(previous: ComplexNumber, coordinate: ComplexNumber): ComplexNumber {
            val step1 = previous * previous
            val step2 = step1 / ComplexNumber(100000, 100000)
            val step3 = step2 + coordinate
            return step3
        }

        private fun shouldBeEngraved(coordinate: ComplexNumber): Boolean =
            generateSequence(ComplexNumber.ZERO) { cycle2(it, coordinate) }
                .drop(1)
                .take(100)
                .all { (real, imaginary) -> real in -1000000..1000000 && imaginary in -1000000..1000000 }
    }

    constructor(input: String) : this(
        input.sanitize().lines()
            .map { it.split("=") }
            .associate { it[0] to ComplexNumber.parse(it[1]) }
    )

    fun partOne(): ComplexNumber = generateSequence(ComplexNumber.ZERO) { cycle1(it, input["A"]!!) }
        .drop(1)
        .take(3)
        .last()

    fun partTwo(): Int = generateSequence(input["A"]!!) { previous -> previous + ComplexNumber(10, 0) }.take(101)
        .flatMap { generateSequence(it) { previous -> previous + ComplexNumber(0, 10) }.take(101) }
        .filter { shouldBeEngraved(it) }
        .count()

    fun partThree(): Int = generateSequence(input["A"]!!) { previous -> previous + ComplexNumber(1, 0) }.take(1001)
        .flatMap { generateSequence(it) { previous -> previous + ComplexNumber(0, 1) }.take(1001) }
        .filter { shouldBeEngraved(it) }
        .count()
}