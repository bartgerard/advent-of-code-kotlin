package aock2018

import shared.distinctPairs
import shared.frequencies
import shared.sanitize

data class Year2018Day02(
    private val input: List<String>
) {
    constructor(input: String) : this(input.sanitize().lines())

    fun partOne() = input.map { it.asSequence().frequencies() }
        .let { frequenciesByLine ->
            frequenciesByLine.count { it.containsValue(2) } * frequenciesByLine.count { it.containsValue(3) }
        }


    fun partTwo() = input.distinctPairs()
        .mapNotNull { (s1, s2) ->
            val common = s1.zip(s2).filter { (c1, c2) -> c1 == c2 }
                .map { (c1, _) -> c1 }
                .joinToString("")
            if (s1.length - common.length == 1) common else null
        }
        .first()

}