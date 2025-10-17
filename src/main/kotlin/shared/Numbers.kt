package shared

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

fun main() {
    val a = Spiral.generatePoints().take(20).toList()
}