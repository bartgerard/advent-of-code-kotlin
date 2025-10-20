package shared

import kotlin.math.absoluteValue

class Spiral {
    companion object {

        fun generatePoints(
            startDirection: Direction = Direction.EAST,
            turn: Turn = Turn.LEFT
        ): Sequence<Point2d> = sequence {
            var point = Point2d.ZERO
            yield(point)

            val orderOfDirections = generateSequence(startDirection) { it.rotate(turn) }
                .map { Vector2d.forDirection(it) }
                .take(5)
                .toList()

            generateSequence(3) { it + 2 }.forEach { ring ->
                point += startDirection
                yield(point)

                repeat(ring - 2) {
                    point += orderOfDirections[1]
                    yield(point)
                }
                (2..4).forEach { i ->
                    repeat(ring - 1) {
                        point += orderOfDirections[i]
                        yield(point)
                    }
                }
            }
        }

    }
}

fun Int.factorize(): List<Int> {
    var remaining = this.absoluteValue

    if (remaining == 0) {
        return emptyList()
    } else if (remaining == 1) {
        return listOf(this)
    }

    return sequence {
        if (this@factorize < 0) {
            yield(-1)
        }

        var divisor = 2

        while (divisor * divisor <= remaining) {
            while (remaining % divisor == 0) {
                yield(divisor)
                remaining /= divisor
            }
            divisor++
        }

        if (remaining > 1) {
            yield(remaining)
        }
    }
        .toList()
}

data class IntFraction(
    val numerator: Int,
    val denominator: Int
) {
    fun simplify(): IntFraction {
        val gcd = gcd(numerator.absoluteValue, denominator.absoluteValue)
        val sign = if (denominator < 0) -1 else 1
        return IntFraction(sign * numerator / gcd, sign * denominator / gcd)
    }
}