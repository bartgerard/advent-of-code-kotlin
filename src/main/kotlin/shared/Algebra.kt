package shared

// a x + b y + c = 0
data class LinearEquation2d(
    val a: Double,
    val b: Double,
    val c: Double
) {
    companion object {
        fun intersect(eq1: LinearEquation2d, eq2: LinearEquation2d): Pair<Double, Double> {
            val y = (eq1.a * eq2.c - eq2.a * eq1.c) / (eq1.a * eq2.b - eq2.a * eq1.b)
            val x = (eq1.c - eq1.b * y) / eq1.a

            return x to y
        }
    }

    constructor(a: Number, b: Number, c: Number) : this(a.toDouble(), b.toDouble(), c.toDouble())

    // y = (- a x - c) / b
    fun at(x: Double) = (-a * x - c) / b

    fun slope() = -a / b
    fun intercept() = -c / b

    fun toLinearFunction() = LinearFunction2d(slope(), intercept())

    infix fun intersect(eq: LinearEquation2d) = intersect(this, eq)
}

data class LinearFunction2d(
    val m: Double,
    val y0: Double
) {
    companion object {
        fun from(line: Line2d): LinearFunction2d = LinearFunction2d(line.slope(), line.yIntercept())

        fun intersect(f1: LinearFunction2d, f2: LinearFunction2d) = ((f2.y0 - f1.y0) / (f2.m - f1.m))
            .let { x -> x to f1.at(x) }
    }

    constructor(m: Number, y0: Number) : this(m.toDouble(), y0.toDouble())

    // y = (c - a x) / b
    fun at(x: Double) = m * x + y0

    fun slope() = m
    fun intercept() = y0

    fun toGeneralForm() = LinearEquation2d(m, 1.0, -y0)

    infix fun intersect(f: LinearFunction2d) = intersect(this, f)
}