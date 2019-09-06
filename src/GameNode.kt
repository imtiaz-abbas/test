
typealias PlayerID = Int
typealias  Directions = Array<Direction>
typealias NodeList = Array<GameNode>

enum class Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT
}

data class GameNode(val x: Int, val y: Int) {

    var threshold: Int = 0
    var playerId: PlayerID = -1
    var currentValue: Int = 0
    var directions: Array<Direction>? = null

    fun withThreshold(threshold: Int): GameNode {
        this.threshold = threshold
        return this
    }

    fun withDirections(directions: Directions): GameNode {
        this.directions = directions
        return this
    }
}

data class GameBoard(val x: Int, val y: Int) {

}

data class GameNodeIndex(val x: Int, val y: Int) {

}