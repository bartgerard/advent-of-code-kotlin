package aock2024

import shared.Connection
import shared.Network
import shared.sanitize

data class Year2024Day23(
    private val network: Network<String>
) {
    constructor(input: String) : this(
        input.sanitize().lines()
            .map { it.split("-") }
            .map { Connection.of(it.first(), it.last()) }
            .let { Network.ofBidirectional(it.toSet()) }
    )

    fun partOne() = network.interconnectedNodes()
        .filter { computers -> computers.any { it.startsWith('t') } }
        .size

    fun partTwo() = network.interconnectedGroups()
        .maxBy { it.size }
        .sorted()
        .joinToString(",")

}