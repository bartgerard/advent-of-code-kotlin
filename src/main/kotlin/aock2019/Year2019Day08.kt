package aock2019

import shared.sanitize

data class Year2019Day08(
    private val input: String
) {

    fun partOne(width: Int, height: Int): Int {
        val layers = input.sanitize().chunked(width * height)
        val layerWithFewestZeroDigits = layers.minBy { layer -> layer.count { it == '0' } }
        return layerWithFewestZeroDigits.count { it == '1' } * layerWithFewestZeroDigits.count { it == '2' }
    }

    fun partTwo(width: Int, height: Int): Long {
        val layerSize = width * height
        val layers = input.sanitize().chunked(layerSize)
        val render = (0..<layerSize)
            .map { i -> layers.map { layer -> layer[i] }.first { it != '2' } }
            .map { if (it == '1') '#' else ' ' }
            .chunked(width)
            .joinToString("\n") { it.joinToString("") }
        println(render)
        return 0L
    }
}