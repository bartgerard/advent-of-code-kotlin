package aock2025

import shared.sanitize

data class Year2025Day11(
    private val reactor: Reactor
) {
    constructor(input: String) : this(Reactor(input.sanitize().lines().map { Device.parse(it) }))

    fun partOne() = reactor.countAllPossiblePathsFrom("you", "out")

    fun partTwo() = 0L
}

data class Reactor(
    val devices: Map<String, List<String>>
) {

    constructor(devices: List<Device>) : this(devices.associate { it.id to it.outputs })

    fun countAllPossiblePathsFrom(
        fromId: String,
        toId: String,
        memory: MutableMap<String, Long> = mutableMapOf()
    ): Long = memory.getOrPut(fromId) {
        if (fromId == toId) {
            return 1L
        }

        return devices[fromId]!!
            .sumOf { output -> countAllPossiblePathsFrom(output, toId, memory) }
    }
}

data class Device(
    val id: String,
    val outputs: List<String>
) {
    companion object {
        val DEVICE_REGEX = "[a-zA-Z0-9]+".toRegex()

        fun parse(line: String): Device {
            val deviceIds = DEVICE_REGEX.findAll(line)
                .map { it.value }
                .toList()

            return Device(
                deviceIds.first(),
                deviceIds.drop(1)
            )
        }
    }
}