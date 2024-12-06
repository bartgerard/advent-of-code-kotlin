package aock2015

import shared.*

data class Year2015Day02(
    private val boxes: List<Box>
) {
    constructor(input: String) : this(input.sanitize().lines().map { it.toLongs().toBox() })

    fun requiredAreaOfWrappingPaper(): Long = this.boxes.sumOf { it.surfaceArea() + it.areaOfSmallestSide() }

    fun requiredLengthOfRibbon(): Long = this.boxes.sumOf { it.smallestPerimeter() + it.volume() }

}