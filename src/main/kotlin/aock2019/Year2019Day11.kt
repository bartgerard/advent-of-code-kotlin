package aock2019

import aock2019.common.LongCodeExecution
import shared.sanitize
import shared.toLongs
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

data class Year2019Day11(
    private val program: List<Long>
) {
    constructor(input: String) : this(input.sanitize().toLongs())

    fun partOne(): Long {
        val camera = LinkedBlockingQueue<Long>()
        val instructions = LinkedBlockingQueue<Long>()
        val programExecution = LongCodeExecution(program.toMutableList(), camera, instructions)
        return 0L
    }

    fun partTwo() = 0L
}

data class Robot(
    val camera: BlockingQueue<Long>,
    val instructions: BlockingQueue<Long>,
) {

}