package aock2015

import shared.Box3d
import shared.asBox
import shared.asLongs
import shared.byLine

data class Day2(
    private val boxes: List<Box3d>
) {
    constructor(text: String) : this(text.byLine().map { it.asLongs().asBox() })

    fun requiredAreaOfWrappingPaper(): Long {
        return this.boxes.sumOf { it.surfaceArea() + it.areaOfSmallestSide() }
    }

    fun requiredLengthOfRibbon(): Long {
        return this.boxes.sumOf { it.smallestPerimeter() + it.volume() }
    }
}