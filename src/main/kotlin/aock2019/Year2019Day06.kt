package aock2019

import shared.TreeNode
import shared.sanitize

data class Year2019Day06(
    private val nodes: Map<String, TreeNode<String>>
) {
    constructor(input: String) : this(
        input.sanitize().lines()
            .map {
                it.split(")")
                    .let { (center, orbit) -> Pair(center, orbit) }
            }
            .let { TreeNode.toNodes(it) }
    )

    fun partOne() = nodes.values.sumOf { it.depth() }
    fun partTwo(): Long {
        val you = nodes["YOU"]!!
        val san = nodes["SAN"]!!

        val pathToYou = you.pathToRoot()
        val pathToSan = san.pathToRoot()

        val intersection = pathToYou.intersect(pathToSan)

        return pathToYou.size + pathToSan.size - intersection.size * 2L - 2
    }
}