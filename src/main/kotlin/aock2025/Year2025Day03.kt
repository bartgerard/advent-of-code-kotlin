package aock2025

import shared.sanitize

data class Year2025Day03(
    private val banks: List<BatteryBank>
) {
    constructor(input: String) : this(input.sanitize().lines().map { BatteryBank.parse(it) })

    fun partOne() = banks.sumOf { bank -> bank.maxJoltage(2) }
    fun partTwo() = banks.sumOf { bank -> bank.maxJoltage(12) }
}

data class BatteryBank(
    private val batteries: List<Int>
) {
    companion object {
        fun parse(bankAsString: String) = BatteryBank(bankAsString.map { it.digitToInt() })

        private fun selectBatteriesProducingMaxJoltage(
            batteries: List<Int>,
            turnOnCount: Int
        ): String {
            require(batteries.isNotEmpty())

            if (turnOnCount <= 0) {
                return ""
            }

            val selectedBattery = batteries.subList(0, batteries.size - turnOnCount + 1).max()
            val selectedBatteryIndex = batteries.indexOfFirst { it == selectedBattery }
            val remainingSelectableBatteries = batteries.subList(selectedBatteryIndex + 1, batteries.size)

            return selectedBattery.toString() + selectBatteriesProducingMaxJoltage(
                remainingSelectableBatteries,
                turnOnCount - 1
            )
        }
    }

    fun maxJoltage(turnOnCount: Int) = selectBatteriesProducingMaxJoltage(batteries, turnOnCount).toLong()

}