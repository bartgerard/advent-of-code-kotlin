package aock2024

import com.microsoft.z3.Context
import com.microsoft.z3.IntNum
import com.microsoft.z3.Status
import shared.sanitize
import shared.splitByEmptyLine
import shared.toLongs
import shared.x
import shared.y

data class Year2024Day13(
    private val machines: List<Machine>
) {
    companion object {
        const val CONVERSION = 10000000000000L
    }

    constructor(input: String) : this(input.sanitize().splitByEmptyLine().map { it.toLongs() }.map { Machine(it) })

    fun partOne() = machines.sumOf { it.solve() }
    fun partTwo() = machines.map { Machine(it.buttonA, it.buttonB, CONVERSION + it.prize.x() to CONVERSION + it.prize.y()) }.sumOf { it.solve() }
}

data class Machine(
    val buttonA: Pair<Long, Long>,
    val buttonB: Pair<Long, Long>,
    val prize: Pair<Long, Long>,
) {
    constructor(input: List<Long>) : this(input[0] to input[1], input[2] to input[3], input[4] to input[5])

    fun solve(): Long {
        val b = (buttonA.x() * prize.y() - buttonA.y() * prize.x()) / (buttonA.x() * buttonB.y() - buttonA.y() * buttonB.x())
        val a = (prize.x() - buttonB.x() * b) / buttonA.x()

        return if (a >= 0 && b >= 0 && buttonA.x() * a + buttonB.x() * b == prize.x() && buttonA.y() * a + buttonB.y() * b == prize.y()) {
            a * 3 + b
        } else {
            0L
        }
    }

    fun solveZ3() = Context().use { ctx ->
        val solver = ctx.mkSolver()

        val a = ctx.mkIntConst("a")
        val b = ctx.mkIntConst("b")

        solver.add(ctx.mkGe(a, ctx.mkInt(0)))
        solver.add(ctx.mkGe(b, ctx.mkInt(0)))
        solver.add(ctx.mkEq(ctx.mkInt(prize.first), ctx.mkAdd(ctx.mkMul(a, ctx.mkInt(buttonA.first)), ctx.mkMul(b, ctx.mkInt(buttonB.first)))))
        solver.add(ctx.mkEq(ctx.mkInt(prize.y()), ctx.mkAdd(ctx.mkMul(a, ctx.mkInt(buttonA.y())), ctx.mkMul(b, ctx.mkInt(buttonB.y())))))

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