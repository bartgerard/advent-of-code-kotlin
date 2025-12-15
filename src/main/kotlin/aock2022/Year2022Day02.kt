package aock2022

import shared.game.Outcome
import shared.game.RoShamBo
import shared.sanitize

data class Year2022Day02(
    private val input: List<Pair<String, String>>
) {
    companion object {
        fun asChoice(value: String): RoShamBo {
            return when (value) {
                "A", "X" -> RoShamBo.ROCK
                "B", "Y" -> RoShamBo.PAPER
                "C", "Z" -> RoShamBo.SCISSORS
                else -> error("invalid choice: $value")
            }
        }

        fun asOutCome(value: String): Outcome {
            return when (value) {
                "X" -> Outcome.LOSS
                "Y" -> Outcome.DRAW
                "Z" -> Outcome.WIN
                else -> error("invalid outcome: $value")
            }
        }

        fun score(pair: Pair<String, String>): Int {
            val opponentChoice = asChoice(pair.first)
            val yourChoice = asChoice(pair.second)
            val outcome = yourChoice.outcomeWhenPlayedAgainst(opponentChoice)
            return score(yourChoice) + score(outcome)
        }

        fun scoreWithExpectedOutcome(pair: Pair<String, String>): Int {
            val opponentChoice = asChoice(pair.first)
            val outcome = asOutCome(pair.second)
            val yourChoice = opponentChoice.choiceForExpectedOutcome(outcome)
            return score(yourChoice) + score(outcome)
        }

        fun score(roShamBo: RoShamBo): Int = when (roShamBo) {
            RoShamBo.ROCK -> 1
            RoShamBo.PAPER -> 2
            RoShamBo.SCISSORS -> 3
        }

        fun score(outcome: Outcome): Int = when (outcome) {
            Outcome.WIN -> 6
            Outcome.DRAW -> 3
            Outcome.LOSS -> 0
        }
    }

    constructor(input: String) : this(
        input.sanitize()
            .lines()
            .map { line -> line.split(" ").let { it[0] to it[1] } }
    )

    fun partOne() = input.sumOf { score(it) }
    fun partTwo() = input.sumOf { scoreWithExpectedOutcome(it) }
}