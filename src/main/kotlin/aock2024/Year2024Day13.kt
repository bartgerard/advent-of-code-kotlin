package aock2024

import com.microsoft.z3.Context
import com.microsoft.z3.IntNum
import com.microsoft.z3.Status
import shared.sanitize
import shared.splitByEmptyLine
import shared.toLongs

data class Year2024Day13(
    private val machines: List<Machine>
) {
    companion object {
        const val CONVERSION = 10000000000000L
    }

    constructor(input: String) : this(input.sanitize().splitByEmptyLine().map { it.toLongs() }.map { Machine(it) })

    fun partOne(): Long = machines.sumOf { it.solve() }
    fun partTwo(): Long = machines.map { Machine(it.buttonXa, it.buttonYa, it.buttonXb, it.buttonYb, CONVERSION + it.prizeX, CONVERSION + it.prizeY) }.sumOf { it.solve() }
}

data class Machine(
    val buttonXa: Long,
    val buttonYa: Long,
    val buttonXb: Long,
    val buttonYb: Long,
    val prizeX: Long,
    val prizeY: Long,
) {
    constructor(input: List<Long>) : this(input[0], input[1], input[2], input[3], input[4], input[5])

    fun solve(): Long = Context().use { ctx ->
        val solver = ctx.mkSolver()

        val a = ctx.mkIntConst("a")
        val b = ctx.mkIntConst("b")

        solver.add(ctx.mkGe(a, ctx.mkInt(0)))
        solver.add(ctx.mkGe(b, ctx.mkInt(0)))
        solver.add(ctx.mkEq(ctx.mkInt(prizeX), ctx.mkAdd(ctx.mkMul(a, ctx.mkInt(buttonXa)), ctx.mkMul(b, ctx.mkInt(buttonXb)))))
        solver.add(ctx.mkEq(ctx.mkInt(prizeY), ctx.mkAdd(ctx.mkMul(a, ctx.mkInt(buttonYa)), ctx.mkMul(b, ctx.mkInt(buttonYb)))))

        when (solver.check()) {
            Status.SATISFIABLE -> {
                val x = solver.model.eval(a, false) as IntNum
                val y = solver.model.eval(b, false) as IntNum
                return x.int64 * 3 + y.int64
            }

            else -> 0L
        }
    }
}