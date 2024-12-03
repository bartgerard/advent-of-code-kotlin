package aock2015

import shared.Box
import shared.toBox
import shared.toLongs
import shared.byLine

data class Year2015Day02(
    private val boxes: List<Box>
) {
    constructor(input: String) : this(input.byLine().map { it.toLongs().toBox() })

    fun requiredAreaOfWrappingPaper(): Long {
        return this.boxes.sumOf { it.surfaceArea() + it.areaOfSmallestSide() }
    }

    fun requiredLengthOfRibbon(): Long {
        return this.boxes.sumOf { it.smallestPerimeter() + it.volume() }
    }

}