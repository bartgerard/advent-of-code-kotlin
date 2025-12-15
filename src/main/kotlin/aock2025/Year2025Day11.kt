package aock2025

import shared.product
import shared.sanitize

data class Year2025Day11(
    private val reactor: Reactor
) {
    companion object {
        const val YOU = "you"
        const val OUT = "out"
        const val SVR = "svr" // server rack
        const val FFT = "fft" // fast Fourier transform
        const val DAC = "dac" // digital-to-analog converter

        val REQUIRED_DEVICES = setOf(FFT, DAC)
    }

    constructor(input: String) : this(Reactor(input.sanitize().lines().map { Device.parse(it) }))

    fun partOne() = reactor.countAllPossiblePathsV2(OriginWithState(YOU), OUT)
    fun partTwo() = reactor.countAllPossiblePathsV2(OriginWithState(SVR), OUT, REQUIRED_DEVICES)

    fun partOneV1() = reactor.countAllPossiblePaths(YOU, OUT)

    fun partTwoV2() = listOf(
        listOf(
            reactor.countAllPossiblePaths(SVR, FFT),
            reactor.countAllPossiblePaths(FFT, DAC),
            reactor.countAllPossiblePaths(DAC, OUT)
        ),
        listOf(
            reactor.countAllPossiblePaths(SVR, DAC),
            reactor.countAllPossiblePaths(DAC, FFT),
            reactor.countAllPossiblePaths(FFT, OUT)
        )
    )
        .sumOf { it.product() }

    fun partTwoV1(): Int = reactor.findAllPossiblePaths(SVR, OUT)
        .count { path -> path.containsAll(REQUIRED_DEVICES) }
}

data class Reactor(
    val devices: Map<String, Set<String>>
) {

    constructor(devices: List<Device>) : this(devices.associate { it.id to it.outputs.toSet() })

    fun countAllPossiblePaths(
        fromId: String,
        toId: String,
        memory: MutableMap<String, Long> = mutableMapOf()
    ): Long = memory.getOrPut(fromId) {
        if (fromId == toId) {
            1L
        } else {
            devices[fromId]
                ?.sumOf { output -> countAllPossiblePaths(output, toId, memory) }
                ?: 0L
        }
    }

    fun findAllPossiblePaths(
        fromId: String,
        toId: String,
        memory: MutableMap<String, Set<List<String>>> = mutableMapOf()
    ): Set<List<String>> = memory.getOrPut(fromId) {
        if (fromId == toId) {
            setOf(listOf(fromId))
        } else {
            devices[fromId]
                ?.flatMap { output ->
                    findAllPossiblePaths(output, toId, memory)
                        .filterNot { output.contains(fromId) }
                        .map {
                            buildList {
                                add(fromId)
                                addAll(it)
                            }
                        }
                }
                ?.toSet()
                ?: emptySet()
        }
    }

    fun countAllPossiblePathsV2(
        from: OriginWithState,
        toId: String,
        required: Set<String> = emptySet(),
        memory: MutableMap<OriginWithState, Long> = mutableMapOf()
    ): Long = memory.getOrPut(from) {
        if (from.fromId == toId) {
            if (from.visited == required) 1L else 0L
        } else {
            devices[from.fromId]
                ?.sumOf { output ->
                    countAllPossiblePathsV2(
                        from.plus(output, required),
                        toId,
                        required,
                        memory
                    )
                }
                ?: 0L
        }
    }
}

data class OriginWithState(
    val fromId: String,
    val visited: Set<String> = setOf(),
) {
    fun plus(
        toId: String,
        required: Set<String>
    ) = OriginWithState(
        toId,
        if (required.contains(toId)) visited + toId else visited
    )
}

data class Device(
    val id: String,
    val outputs: List<String>
) {
    companion object {
        val DEVICE_REGEX = "[a-z]+".toRegex()

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
