package aock2015

import shared.Box
import shared.byLine
import shared.toBox
import shared.toLongs

data class Year2015Day02(
    private val boxes: List<Box>
) {
    constructor(input: String) : this(input.byLine().map { it.toLongs().toBox() })

    fun requiredAreaOfWrappingPaper(): Long = this.boxes.sumOf { it.surfaceArea() + it.areaOfSmallestSide() }

    fun requiredLengthOfRibbon(): Long = this.boxes.sumOf { it.smallestPerimeter() + it.volume() }

}