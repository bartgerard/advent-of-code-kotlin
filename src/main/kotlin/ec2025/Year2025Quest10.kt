package ec2025

import shared.geometry2d.Dimension2d
import shared.geometry2d.Point2dInt
import shared.geometry2d.Vector2dInt
import shared.grid.CharGrid

data class Year2025Quest10(
    private val board: DragonChessBoard,
    private val initialState: InitialState
) {
    companion object {
        const val DRAGON = 'D'
        const val SHEEP = 'S'
        const val HIDEOUT = '#'
    }

    constructor(input: String) : this(CharGrid(input))
    constructor(grid: CharGrid) : this(
        DragonChessBoard(
            grid.dimension(),
            grid.findAll(HIDEOUT).toSet<Point2dInt>()
        ),
        InitialState(
            grid.findAll(DRAGON).single<Point2dInt>(),
            grid.findAll(SHEEP).toSet<Point2dInt>()
        )
    )

    fun partOne(moves: Int): Int {
        val reachableAfterMoves = board.dragonPositions(initialState.dragon).take(moves + 1).flatten().toSet()
        return initialState.sheep.count { reachableAfterMoves.contains(it) }
    }

    fun partTwo(rounds: Int): Int {
        val dragonChess = DragonChessV2(
            board,
            initialState
        )
        var state = GameStateV2(
            setOf(initialState.dragon),
            initialState.sheep,
        )

        repeat(rounds) {
            state = dragonChess.playRound(state)
        }

        return state.eaten
    }

    fun partThree(): Long = DragonChessV3(board, initialState).countAllWinningSequences()

}

data class DragonChessBoard(
    val dimension: Dimension2d,
    val hideouts: Set<Point2dInt>
) {
    fun dragonPositions(dragon: Point2dInt) = generateSequence(setOf(dragon)) { moveDragon(it) }

    fun moveDragon(previous: Set<Point2dInt>): Set<Point2dInt> = previous.flatMap { moveDragon(it) }.toSet()

    fun moveDragon(previous: Point2dInt) = previous
        .neighbours(Vector2dInt.KNIGHT_MOVES)
        .filter { dimension.contains(it) }
        .toSet()

    fun moveSheep(previous: Set<Point2dInt>): Set<Point2dInt> = previous
        .map { it + Vector2dInt.SOUTH }
        .filter { dimension.contains(it) }
        .toSet()
}

data class InitialState(
    val dragon: Point2dInt,
    val sheep: Set<Point2dInt>
)

data class GameStateV2(
    val dragon: Set<Point2dInt>,
    val sheep: Set<Point2dInt>,
    val eaten: Int = 0
)

data class DragonChessV2(
    private val board: DragonChessBoard,
    private val initialState: InitialState
) {

    fun playRound(state: GameStateV2): GameStateV2 = state
        .let {
            it.copy(
                dragon = board.moveDragon(it.dragon)
            )
        }
        .let { eatSheep(it) }
        .let {
            it.copy(
                sheep = board.moveSheep(it.sheep)
            )
        }
        .let { eatSheep(it) }

    fun eatSheep(state: GameStateV2): GameStateV2 {
        val eatableSheep = state.sheep.filter { !board.hideouts.contains(it) && state.dragon.contains(it) }

        return state.copy(
            sheep = state.sheep - eatableSheep.toSet(),
            eaten = state.eaten + eatableSheep.count(),
        )
    }

}

data class GameStateV3(
    val dragon: Point2dInt,
    val sheep: Set<Point2dInt>,
    val turn: Turn = Turn.SHEEP,
    val eatenCount: Int = 0
)

data class DragonChessV3(
    private val board: DragonChessBoard,
    private val initialState: InitialState
) {

    fun countAllWinningSequences(): Long = countSequences(
        GameStateV3(initialState.dragon, initialState.sheep),
        mutableMapOf()
    )

    private fun countSequences(
        state: GameStateV3,
        cache: MutableMap<GameStateV3, Long>
    ): Long = cache.getOrPut(state) {
        if (initialState.sheep.size != state.eatenCount + state.sheep.size) {
            return 0L
        } else if (state.sheep.isEmpty()) {
            return 1L
        } else {
            val result = when (state.turn) {
                Turn.SHEEP -> {
                    val possibleMoves = getPossibleSheepMoves(state)

                    if (possibleMoves.isEmpty()) {
                        countSequences(state.copy(turn = Turn.DRAGON), cache)
                    } else {
                        possibleMoves.sumOf { newState ->
                            countSequences(newState, cache)
                        }
                    }
                }

                Turn.DRAGON -> getPossibleDragonMoves(state).sumOf { newState ->
                    countSequences(newState, cache)
                }
            }

            cache[state] = result
            return result
        }
    }

    private fun getPossibleSheepMoves(state: GameStateV3): List<GameStateV3> = state.sheep
        .map { it to it + Vector2dInt.SOUTH }
        .filter { (_, newPosition) ->
            when {
                !board.dimension.contains(newPosition) -> true
                board.hideouts.contains(newPosition) -> true
                state.dragon == newPosition -> false
                else -> true
            }
        }
        .map { (oldPosition, newPosition) ->
            val newSheep =
                state.sheep - oldPosition + if (board.dimension.contains(newPosition)) setOf(newPosition) else emptySet()
            eatSheep(GameStateV3(state.dragon, newSheep, Turn.DRAGON, state.eatenCount))
        }

    private fun getPossibleDragonMoves(state: GameStateV3): List<GameStateV3> = board.moveDragon(state.dragon)
        .map { newPosition ->
            eatSheep(GameStateV3(newPosition, state.sheep, Turn.SHEEP, state.eatenCount))
        }

    private fun eatSheep(state: GameStateV3): GameStateV3 {
        val eatableSheep = state.sheep.filter { !board.hideouts.contains(it) && state.dragon == it }

        return state.copy(
            sheep = state.sheep - eatableSheep.toSet(),
            eatenCount = state.eatenCount + eatableSheep.size
        )
    }

}

enum class Turn {
    SHEEP,
    DRAGON
}