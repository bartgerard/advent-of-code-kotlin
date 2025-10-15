package aock2024

import shared.difference
import shared.sanitize
import shared.splitByEmptyLine
import java.util.*

data class Year2024Day24(
    private val inputs: Map<String, Boolean>,
    private val gates: Map<String, Gate>
) {
    companion object {
        fun parseBoolean(value: String) = when (value) {
            "1" -> true
            else -> false
        }

        fun toString(boolean: Boolean) = if (boolean) {
            "1"
        } else {
            "0"
        }

        fun toLabel(value: Int) = value.toString().padStart(2, '0')

        fun toLong(value: Char, registers: Map<String, Boolean>) = registers.entries
            .filter { it.key.startsWith(value) }
            .sortedBy { it.key }
            .reversed()
            .map { it.value }
            .joinToString(separator = "") { toString(it) }
            .toLong(2)

        fun toBinary(value: Long) = value.toString(2)

        fun toPair(value: String) = value[0] to value.substring(1).toInt()
    }

    constructor(input: String) : this(input.sanitize().splitByEmptyLine())
    constructor(input: List<String>) : this(
        input[0].lines()
            .map { it.split(": ") }
            .associate { it[0] to parseBoolean(it[1]) },
        input[1].lines()
            .map { it.split(" ") }
            .associate { it[4] to Gate(setOf(it[0], it[2]), Operation.parse(it[1])) }
    )

    fun partOne() = toLong('z', calculate())

    fun partTwo(operation: String, swaps: Int): String {
        val maxZ = gates.keys.filter { it.startsWith('z') }.maxOf { it.substring(1).toInt() }

        val bookkeeper = Bookkeeper(gates, inputs.keys)
        //toGraph()

        when (operation) {
            "AND" -> (0..maxZ).forEach {
                val i = toLabel(it)
                bookkeeper.expect("x$i", "y$i", Operation.AND, "z$i")
            }

            "ADD" -> (0..maxZ - 1).forEach {
                val i = toLabel(it)
                val j = toLabel(it - 1)

                if (it == 0) {
                    bookkeeper.expect("x$i", "y$i", Operation.XOR, "z$i") // z00
                    bookkeeper.expect("x$i", "y$i", Operation.AND, "carry$i") // ppj
                } else {
                    bookkeeper.expect("x$i", "y$i", Operation.XOR, "xor$i") // mtd,
                    bookkeeper.expect("xor$i", "carry$j", Operation.XOR, "z$i") // z01, zo2, z03..

                    bookkeeper.expect("x$i", "y$i", Operation.AND, "and$i") // dsm
                    bookkeeper.expect("xor$i", "carry$j", Operation.AND, "half$i") // jrp

                    val carryLabel = if (it < maxZ) {
                        "carry$i" // wjg
                    } else {
                        "z${toLabel(maxZ)}" // z45
                    }

                    bookkeeper.expect("and$i", "half$i", Operation.OR, carryLabel)
                }
            }
        }

        return bookkeeper.swapped.keys.sorted().joinToString(",")
    }

    private fun expectedValue(operation: String): Long {
        val x = toLong('x', inputs)
        val y = toLong('y', inputs)

        return when (operation) {
            "ADD" -> x + y
            "AND" -> x.and(y)
            else -> 0L
        }
    }

    private fun expectedGates(operation: String, maxZ: Int): Map<String, Gate> = when (operation) {
        "AND" -> buildMap {
            (0..maxZ).forEach {
                val label = toLabel(it)
                put("z$label", Gate(setOf("x$label", "y$label"), Operation.AND))
            }
        }

        "ADD" -> buildMap {
            (0..maxZ - 1).forEach {
                val i = toLabel(it)
                val j = toLabel(it - 1)

                val xorI = Gate(setOf("x$i", "y$i"), Operation.XOR)
                val andI = Gate(setOf("x$i", "y$i"), Operation.AND)

                if (it == 0) {
                    put("z$i", xorI)
                    put("carry$i", andI) // ppj
                } else {
                    put("xor$i", xorI)
                    put("z$i", Gate(setOf("xor$i", "carry$j"), Operation.XOR)) // z01, zo2..

                    put("half$i", Gate(setOf("xor$i", "carry$j"), Operation.AND)) // jrp
                    put("and$i", andI) // dsm

                    val carryLabel = if (it < maxZ) {
                        "carry$i" // wjg
                    } else {
                        "z${toLabel(maxZ)}" // z45
                    }
                    put(carryLabel, Gate(setOf("and$i", "half$i"), Operation.OR))
                }
            }
        }

        else -> emptyMap()
    }

    private fun gatesByDestination(): SortedMap<String, MutableMap<String, Gate>> {
        val destinations = gates.keys
            .filter { it.startsWith('z') }

        return destinations.associateWith {
            val remaining = mutableListOf(it)
            val result = mutableMapOf<String, Gate>()

            while (remaining.isNotEmpty()) {
                val wire = remaining.removeFirst()
                val gate = gates[wire]

                if (gate != null) {
                    result += wire to gate
                    remaining += gate.wires
                }
            }

            result
        }
            .toSortedMap()
    }

    fun wires(): SortedMap<String, Pair<Char, Int>> {
        return buildMap {
            this += inputs.keys.associateWith { toPair(it) }
            this += gates.keys.filter { it.startsWith('z') }.associateWith { toPair(it) }
        }
            .toSortedMap()
    }

    private fun calculate(): Map<String, Boolean> {
        val registers = inputs.toMutableMap()
        val remainingGates = gates.toMutableMap()

        while (remainingGates.isNotEmpty()) {
            for ((out, gate) in remainingGates.toMap()) {
                if (registers.keys.containsAll(gate.wires)) {
                    remainingGates.remove(out)

                    val result = gate.calculate(gate.wires.map { registers[it]!! })
                    registers[out] = result
                }
            }
        }

        return registers
    }

    private fun toGraph() {
        gates.entries
            .flatMap { (out, gate) -> gate.wires.map { "$it -> $out" } }
            .sorted()
            .joinToString(separator = ";\n", prefix = "digraph G {\n", postfix = "}")
            .also { println(it) }
    }
}

data class Gate(
    val wires: Set<String>,
    val operation: Operation
) {
    fun calculate(values: List<Boolean>) = operation.function(values[0], values[1])

    override fun toString() = "${wires.first()} $operation ${wires.last()}"
}

enum class Operation(
    val function: (Boolean, Boolean) -> Boolean
) {
    AND({ a, b -> a.and(b) }),
    OR({ a, b -> a.or(b) }),
    XOR({ a, b -> a.xor(b) });

    companion object {
        fun parse(value: String) = when (value) {
            "AND" -> AND
            "OR" -> OR
            "XOR" -> XOR
            else -> error(value)
        }
    }
}

data class Bookkeeper(
    val gateMap: Map<Gate, String>,
    val mapping: MutableMap<String, String>,
    val swapped: MutableMap<String, String> = mutableMapOf(),
) {
    fun swapIfNeeded(wire: String) = swapped[wire] ?: wire

    fun expect(
        wire1: String,
        wire2: String,
        operation: Operation,
        label: String
    ) {
        val mappedWire1 = swapIfNeeded(mapping[wire1]!!)
        val mappedWire2 = swapIfNeeded(mapping[wire2]!!)

        val wires = setOf(mappedWire1, mappedWire2)
        val gate = Gate(wires, operation)
        val out = gateMap[gate]

        if (out == null) {
            val candidates = gateMap.filter { !Collections.disjoint(it.key.wires, wires) }
                .filter { it.key.operation == operation || it.value == label }

            if (candidates.size == 1) {
                val candidate = candidates.firstNotNullOf { it }

                val difference = (wires difference candidate.key.wires).toList()
                swapped += difference[0] to difference[1]
                swapped += difference[1] to difference[0]

                mapping[label] = candidate.value
            } else {
                error("Unexpected gate $gate")
            }
        } else if (label.startsWith('z') && out != label) {
            swapped += label to out
            swapped += out to label
        } else {
            mapping[label] = out
        }
    }

    constructor(
        gateMap: Map<String, Gate>,
        initial: Collection<String>,
    ) : this(
        gateMap.entries.associate { it.value to it.key },
        initial.associateWith { it }.toMutableMap(),
    )
}