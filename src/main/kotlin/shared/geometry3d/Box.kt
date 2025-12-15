package shared.geometry3d

data class Box(
    val length: Long,
    val width: Long,
    val height: Long
) {
    fun volume() = length * width * height
    fun surfaceArea() = 2 * (length * width + width * height + height * length)
    fun areaOfSmallestSide() = sequenceOf(length * width, width * height, height * length).min()
    fun smallestPerimeter() = 2 * sequenceOf(length + width, width + height, height + length).min()
}