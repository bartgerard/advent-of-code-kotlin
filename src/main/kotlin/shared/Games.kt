package shared

enum class Outcome {
    WIN,
    DRAW,
    LOSS,
}

enum class RoShamBo {
    ROCK,
    PAPER,
    SCISSORS;

    fun outcomeWhenPlayedAgainst(choice: RoShamBo): Outcome {
        return when (this) {
            ROCK -> when (choice) {
                ROCK -> Outcome.DRAW
                PAPER -> Outcome.LOSS
                SCISSORS -> Outcome.WIN
            }

            PAPER -> when (choice) {
                ROCK -> Outcome.WIN
                PAPER -> Outcome.DRAW
                SCISSORS -> Outcome.LOSS
            }

            SCISSORS -> when (choice) {
                ROCK -> Outcome.LOSS
                PAPER -> Outcome.WIN
                SCISSORS -> Outcome.DRAW
            }
        }
    }

    fun choiceForExpectedOutcome(outcome: Outcome): RoShamBo {
        return when (this) {
            ROCK -> when (outcome) {
                Outcome.WIN -> PAPER
                Outcome.DRAW -> ROCK
                Outcome.LOSS -> SCISSORS
            }

            PAPER -> when (outcome) {
                Outcome.WIN -> SCISSORS
                Outcome.DRAW -> PAPER
                Outcome.LOSS -> ROCK
            }

            SCISSORS -> when (outcome) {
                Outcome.WIN -> ROCK
                Outcome.DRAW -> SCISSORS
                Outcome.LOSS -> PAPER
            }
        }
    }
}