package aock2022

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.int
import shared.indicesOf
import shared.productOf
import shared.sanitize
import shared.splitByEmptyLine
import java.util.*

data class Year2022Day13(
    private val packetPairs: List<List<PacketData>>
) {
    companion object {
        val DIVIDER_PACKETS = listOf("[[2]]", "[[6]]").map { PacketData.parse(it) }
    }

    constructor(input: String) : this(
        input.sanitize().splitByEmptyLine()
            .map { packets -> packets.lines().map { PacketData.parse(it) } }
    )

    fun partOne() = packetPairs.mapIndexed { index, packetPair ->
        index to PacketData.COMPARATOR.compare(
            packetPair.first(),
            packetPair.last()
        )
    }
        .filter { it.second <= 0 }
        .sumOf { it.first + 1 }

    fun partTwo() = (packetPairs.flatMap { it } + DIVIDER_PACKETS)
        .sortedWith(PacketData.COMPARATOR)
        .indicesOf(DIVIDER_PACKETS)
        .productOf { it + 1 }
}

/**
 * PacketData.COMPARATOR.compare(PacketData.parse("9"), PacketData.parse("[8,7,6]"))
 */
interface PacketData {
    companion object {
        val COMPARATOR = PacketDataComparator()

        fun parse(line: String) = parse(Json.parseToJsonElement(line))

        private fun parse(node: JsonElement): PacketData = when (node) {
            is JsonArray -> ListItem(node.map { parse(it) })
            is JsonPrimitive -> IntItem(node.int)
            else -> error("unsupported")
        }
    }
}

data class ListItem(
    val items: List<PacketData>
) : PacketData {
    override fun toString(): String = Objects.toString(items)
}

data class IntItem(
    val item: Int
) : PacketData {
    override fun toString(): String = Objects.toString(item)
    fun toListItem() = ListItem(listOf(this))
}

class PacketDataComparator : Comparator<PacketData> {
    override fun compare(p1: PacketData, p2: PacketData) = when (p1) {
        is ListItem if p2 is ListItem -> compare(p1, p2)
        is ListItem if p2 is IntItem -> compare(p1, p2.toListItem())
        is IntItem if p2 is IntItem -> compare(p1, p2)
        is IntItem if p2 is ListItem -> -compare(p2, p1.toListItem())
        else -> error("invalid case")
    }

    private fun compare(p1: ListItem, p2: ListItem): Int = p1.items.zip(p2.items)
        .map { (i1, i2) -> compare(i1, i2) }
        .firstOrNull { it != 0 }
        ?: p1.items.size.compareTo(p2.items.size)

    private fun compare(p1: IntItem, p2: IntItem) = p1.item.compareTo(p2.item)
}