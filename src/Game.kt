

import kotlin.collections.HashMap

class Game {
    var currentPlayer: PlayerID = 0
    val x: Int = 4
    val y: Int = 4
    var gameNodes: HashMap<Int, GameNode> = hashMapOf<Int, GameNode>()
    var prevGameNodes: HashMap<Int, GameNode> = hashMapOf<Int, GameNode>()
    var redoGameNodes: HashMap<Int, GameNode> = hashMapOf<Int, GameNode>()
    var gameNodeGraph: HashMap<Int, NodeList> = hashMapOf<Int, NodeList>()
    var board: GameBoard = GameBoard(this.x, this.y)
    var players: Array<PlayerID> = arrayOf<PlayerID>()

    fun start() {
        this.board = GameBoard(this.x, this.y)
        constuctBoard()
        constructAdjacencyNodes()


        var nodeQueue: NodeQueue<GameNode> = NodeQueue<GameNode>()

        nodeQueue.enqueue(GameNode(0, 0))
        nodeQueue.enqueue(GameNode(0, 1))
        nodeQueue.dequeue()

    }

    fun stop() {

    }

    private fun constuctBoard() {
        val maxX = this.board.x - 1
        val maxY = this.board.y - 1

        for (i in 0..maxX) {
            for (j in 0..maxY) {
                val t =
                    if ((i == 0 && j == 0) || (i == 0 && j == maxY) || (i == maxX && j == 0) || (i == maxX && j == maxY)) {
                        1
                    } else if (i == 0 || j == 0 || i == maxX || j == maxY) {
                        2
                    } else {
                        3
                    }
                val gameNode = GameNode(i, j)
                val gameNodeIndex = GameNodeIndex(i, j)
                gameNodes[gameNodeIndex.hashCode()] = gameNode.withThreshold(t)
            }
        }
    }

    private fun constructAdjacencyNodes() {
        val maxX = this.board.x
        val maxY = this.board.y

        for (x in 0..maxX - 1) {
            for (y in 0..maxY - 1) {

                val gameNodeIndex = GameNodeIndex(x, y)

                val top = gameNodes[GameNodeIndex(x - 1, y).hashCode()]
                val bottom = gameNodes[GameNodeIndex(x + 1, y).hashCode()]
                val right = gameNodes[GameNodeIndex(x, y + 1).hashCode()]
                val left = gameNodes[GameNodeIndex(x, y - 1).hashCode()]

                var directions: Array<Direction> = arrayOf<Direction>()

                if (x > 0 && y > 0 && x < maxX - 1 && y < maxY - 1) {
                    directions += Direction.UP
                    directions += Direction.DOWN
                    directions += Direction.RIGHT
                    directions += Direction.LEFT
                } else if (x == 0 && y != 0 && y != maxY - 1) {
                    directions += Direction.DOWN
                    directions += Direction.RIGHT
                    directions += Direction.LEFT
                } else if (y == 0 && x != 0 && x != maxX - 1) {
                    directions += Direction.UP
                    directions += Direction.DOWN
                    directions += Direction.RIGHT
                } else if (x == maxX - 1 && y != 0 && y != maxY - 1) {
                    directions += Direction.UP
                    directions += Direction.RIGHT
                    directions += Direction.LEFT
                } else if (y == maxY - 1 && x != 0 && x != maxX - 1) {
                    directions += Direction.UP
                    directions += Direction.DOWN
                    directions += Direction.LEFT
                } else if (x == 0 && y == 0) {
                    directions += Direction.DOWN
                    directions += Direction.RIGHT
                } else if (x == maxX - 1 && y == maxY - 1) {
                    directions += Direction.UP
                    directions += Direction.LEFT
                } else if (x == maxX - 1 && y == 0) {
                    directions += Direction.UP
                    directions += Direction.RIGHT
                } else if (x == 0 && y == maxY - 1) {
                    directions += Direction.DOWN
                    directions += Direction.LEFT
                }
                var nodeList: NodeList = arrayOf<GameNode>();
                if (directions.contains(Direction.UP) && top != null) {
                    nodeList += top
                }
                if (directions.contains(Direction.DOWN) && bottom != null) {
                    nodeList += bottom
                }
                if (directions.contains(Direction.RIGHT) && right != null) {
                    nodeList += right
                }
                if (directions.contains(Direction.LEFT) && left != null) {
                    nodeList += left
                }
                var node = gameNodes[gameNodeIndex.hashCode()]
                if (node != null) {
                    gameNodes[gameNodeIndex.hashCode()] = node.withDirections(directions)
                    gameNodeGraph.put(gameNodeIndex.hashCode(), nodeList)
                }
            }
        }
    }

    private fun undoAction() {
        if (!prevGameNodes.isEmpty()) {
            redoGameNodes = gameNodes
            gameNodes = prevGameNodes
            prevGameNodes = hashMapOf<Int, GameNode>()
            prevPlayer()
        }
    }

    private fun redoAction() {
        if (!redoGameNodes.isEmpty()) {
            prevGameNodes = gameNodes
            gameNodes = redoGameNodes
            redoGameNodes = hashMapOf<Int, GameNode>()
            nextPlayer()
        }
    }


    private fun nextPlayer() {
        if (this.currentPlayer == this.players.size - 1) {
            this.currentPlayer = 0
        } else {
            this.currentPlayer += 1
        }
    }

    private fun prevPlayer() {
        if (this.currentPlayer == 0) {
            this.currentPlayer = this.players.size - 1
        } else {
            this.currentPlayer -= 1
        }
    }
}


class NodeQueue<T> {
    var nodes:MutableList<T> = mutableListOf<T>()


    fun enqueue(element: T) {
        nodes.add(nodes.size, element)
    }

    fun dequeue(): T? {
        if (nodes.isEmpty()) {
            return null
        }
        else{
            val tempElement = nodes.first()
            nodes.removeAt(0)
            return tempElement
        }
    }

    fun isEmpty(): Boolean {
        return nodes.isEmpty()
    }
}
