package aock2015

import shared.Box
import shared.asBox
import shared.asLongs
import shared.byLine

data class Year2015Day02(
    private val boxes: List<Box>
) {
    constructor(text: String) : this(text.byLine().map { it.asLongs().asBox() })

    fun requiredAreaOfWrappingPaper(): Long {
        return this.boxes.sumOf { it.surfaceArea() + it.areaOfSmallestSide() }
    }

    fun requiredLengthOfRibbon(): Long {
        return this.boxes.sumOf { it.smallestPerimeter() + it.volume() }
    }
}