package aock2024

import shared.byLine
import shared.intervals
import shared.toLongs
import shared.withoutIndex
import kotlin.math.abs
import kotlin.math.sign

fun parse2024Day02(input: String): Year2024Day02 {
    return Year2024Day02.parse(input)
}

data class Year2024Day02(
    private val reports: List<List<Long>>
) {
    companion object {
        fun parse(input: String): Year2024Day02 = input.byLine()
            .map { it.toLongs() }
            .let { Year2024Day02(it) }
    }

    fun countSafe(): Int = reports.count { isSafe(it) }

    fun countSafeWithTolerance(): Int = reports.count { isSafeWithTolerance(it) }

    private fun isSafe(report: List<Long>): Boolean {
        if (report.size <= 2) {
            return true
        }

        val intervals = report.intervals()
        val firstInterval = intervals[0]

        return intervals.all { isSafe(it, firstInterval) }
    }

    private fun isSafe(difference: Long, firstInterval: Long) =
        abs(difference) in 1..3 && firstInterval.sign == difference.sign

    private fun isSafeWithTolerance(report: List<Long>): Boolean {
        if (isSafe(report.withoutIndex(0))) {
            // isSafe ignoring the first entry
            // if the remainder of the report is safe, we can tolerate the bad level at the start
            return true
        }

        val intervals = report.intervals()
        val firstInterval = intervals[0]

        val indexOfFirstBadLevel = intervals.indexOfFirst { !isSafe(it, firstInterval) }

        return indexOfFirstBadLevel == intervals.size - 1
                || isSafe(report.withoutIndex(indexOfFirstBadLevel))
                || isSafe(report.withoutIndex(indexOfFirstBadLevel + 1))
    }

}