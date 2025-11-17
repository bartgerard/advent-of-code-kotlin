package ec2025

import shared.distinctPairs
import shared.sanitize
import shared.toIntegers
import java.util.*
import kotlin.math.max
import kotlin.math.min

data class Year2025Quest08(
    private val threads: List<Pair<Int, Int>>
) {
    constructor(input: String) : this(
        input.sanitize().toIntegers()
            .zipWithNext { a, b -> min(a, b) to max(a, b) }
    )

    fun partOne(nails: Int) = threads.count { (a, b) -> b - a == nails / 2 }

    fun partTwo() = threads.withIndex()
        .drop(1)
        .sumOf { (index, thread1) ->
            threads.subList(0, index - 1)
                .count { thread2 ->
                    Collections.disjoint(setOf(thread1.first, thread1.second), setOf(thread2.first, thread2.second))
                            && thread2.first in thread1.first..thread1.second != thread2.second in thread1.first..thread1.second
                }
        }

    fun partThree(nails: Int) = (1..nails).distinctPairs()
        .maxOf { (x, y) ->
            threads.sumOf { (a, b) ->
                when {
                    a == x && b == y -> 1 // same as cut
                    a == x || a == y || b == x || b == y -> 0
                    (a in (x + 1)..<y) xor (b in (x + 1)..<y) -> 1
                    else -> 0
                }
            }
        }

}