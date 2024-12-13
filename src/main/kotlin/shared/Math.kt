package shared

import org.apache.commons.lang3.Validate.isTrue
import java.util.Collections.unmodifiableMap
import kotlin.math.abs
import kotlin.math.pow

fun factorial(n: Long) = (1..n).fold(1, Long::times)

fun combinations(k: Long, n: Long): Long {
    isTrue(0 <= k, "k should be positive")
    isTrue(k <= n, "k should be smaller or equal to n")

    return factorial(n) / (factorial(k) * factorial(n - k))
}

fun simplex(k: Long, n: Long) = combinations(k, n + k)

fun triangular(n: Long) = n * (n + 1) / 2

fun primeFactors(value: Long): Map<Long, Int> {
    val powerByPrimeFactor: MutableMap<Long, Int> = HashMap()

    var absNumber = abs(value)

    var factor = 2L
    while (factor <= absNumber) {
        while (absNumber % factor == 0L) {
            val power = powerByPrimeFactor[factor] ?: 0
            powerByPrimeFactor[factor] = power + 1
            absNumber /= factor
        }
        factor++
    }

    return unmodifiableMap(powerByPrimeFactor)
}

// least common multiple
fun lcm(values: List<Long>) = lcm(values.toLongArray())

fun lcm(values: LongArray): Long {
    val maxPowerByPrimeFactor = values.asSequence().map { primeFactors(it) }.flatMap { it.entries }.groupBy { it.key }
        .mapValues { (_, values) -> values.maxOf { it.value } }

    return maxPowerByPrimeFactor.map { it.key.toDouble().pow(it.value).toLong() }
        .fold(1L, Long::times)// { x: Long, y: Long -> x * y }
}

// greatest common divisor
tailrec fun gcd(x: Long, y: Long): Long = when {
    x == 0L -> y
    y == 0L -> x
    else -> {
        val max = maxOf(x, y)
        val min = minOf(x, y)
        gcd(min, max % min)
    }
}

fun sum(range: IntRange) = sum(range.min().toLong()..range.max().toLong())

fun sum(range: LongRange): Long = (range.min() + range.max()) * range.length() / 2