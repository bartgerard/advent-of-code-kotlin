package shared.geometry3d

data class Box3d(
    val x: ClosedFloatingPointRange<Double>,
    val y: ClosedFloatingPointRange<Double>,
    val z: ClosedFloatingPointRange<Double> = 0.0..0.0
) {
    constructor(length: Double, width: Double, height: Double = 0.0) : this(0.0..length, 0.0..width, 0.0..height)

    fun length() = x.endInclusive - x.start
    fun width() = y.endInclusive - y.start
    fun height() = z.endInclusive - z.start

    fun volume() = length() * width() * height()
    fun surfaceArea() = 2 * (length() * width() + width() * height() + height() * length())
    fun areaOfSmallestSide() = sequenceOf(length() * width(), width() * height(), height() * length()).min()
    fun smallestPerimeter() = 2 * sequenceOf(length() + width(), width() + height(), height() + length()).min()

    fun contains(p: Point3d) = p.x in x && p.y in y && p.z in z
}