package aock2016

import shared.permutations
import shared.sanitize
import shared.toIntegers

data class Year2016Day03(
    private val input: List<List<Int>>
) {
    companion object {
        fun isValidTriangle(a: Int, b: Int, c: Int) = a + b > c
    }

    constructor(input: String) : this(input.sanitize().lines().map { it.toIntegers() })

    fun partOne() = countValidTriangles(input)

    fun partTwo() = input.chunked(3)
        .flatMap { lines -> (0..<3).map { i -> lines.map { line -> line[i] } } }
        .let { countValidTriangles(it) }

    private fun countValidTriangles(triangles: List<List<Int>>): Int = triangles.count { it.permutations().all { (a, b, c) -> isValidTriangle(a, b, c) } }
}