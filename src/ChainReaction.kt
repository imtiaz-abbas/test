class ChainReaction {

    var matrix = arrayOf<Array<Node>>()
    var maxX: Int = 5
    var maxY: Int = 5
    var currentUser: Int = 0
    var i: Int = 0
    var j: Int = 0

    var v: Node = Node(0,0)

    fun startGame() {
        constuctMatrix()
        constructAdjacencyList()
        printMatrix()
        getUserInput()
    }

    fun getUserInput() {
        print("Enter x: ")
        var x = readLine()!!

        print("Enter y: ")
        var y = readLine()!!

        matrix[x.toInt()][y.toInt()].addOne()
        printMatrix()
        getUserInput()
    }

    fun constructAdjacencyList() {
        for (x in 0 until maxX) {
            for (y in 0 until maxY) {
                var top: Node? = null
                var bottom: Node? = null
                var right: Node? = null
                var left: Node? = null
                if (x > 0 && y > 0 && x < maxX - 1 && y < maxY - 1) {
                    top = matrix[x - 1][y]
                    bottom = matrix[x + 1][y]
                    right = matrix[x][y + 1]
                    left = matrix[x][y - 1]
                } else if (x == 0 && y != 0 && y != maxY - 1) {
                    bottom = matrix[x + 1][y]
                    right = matrix[x][y + 1]
                    left = matrix[x][y - 1]
                } else if (y == 0 && x != 0 && x != maxX - 1) {
                    top = matrix[x - 1][y]
                    bottom = matrix[x + 1][y]
                    right = matrix[x][y + 1]
                } else if(x == maxX - 1 && y != 0 && y != maxY - 1) {
                    top = matrix[x - 1][y]
                    right = matrix[x][y + 1]
                    left = matrix[x][y - 1]
                } else if(y == maxY - 1 && x != 0 && x != maxX - 1) {
                    top = matrix[x - 1][y]
                    bottom = matrix[x + 1][y]
                    left = matrix[x][y - 1]
                } else if (x == 0 && y == 0) {
                    bottom = matrix[x + 1][y]
                    right = matrix[x][y + 1]
                } else if (x == maxX - 1 && y == maxY - 1) {
                    top = matrix[x - 1][y]
                    left = matrix[x][y - 1]
                } else if (x == maxX - 1 && y == 0) {
                    top = matrix[x - 1][y]
                    right = matrix[x][y + 1]
                } else if (x == 0 && y == maxY - 1) {
                    bottom = matrix[x + 1][y]
                    left = matrix[x][y - 1]
                }
                if (top != null) {
                    matrix[x][y].adjacencyList += top
                }
                if (bottom != null) {
                    matrix[x][y].adjacencyList += bottom
                }
                if (right != null) {
                    matrix[x][y].adjacencyList += right
                }
                if (left != null) {
                    matrix[x][y].adjacencyList += left
                }
            }
        }
    }

    fun printMatrix() {
        for (x in matrix) {
            for (y in x) {
                print("${y.currentValue}-${y.userName}\t")
            }
            println()
        }
    }

    fun constuctMatrix() {
        for (i in 0 until maxX) {
            var temp = arrayOf<Node>()
            for (j in 0 until maxY) {
                var n = Node(i, j)
                if ((i == 0 && j == 0) || (i == 0 && j == maxY - 1) || (i == maxX - 1 && j == 0) || (i == maxX - 1 && j == maxY - 1)) {
                    n.threshold = 1
                } else if (i == 0 || j == 0 || i == maxX - 1 || j == maxY - 1) {
                    n.threshold = 2
                } else {
                    n.threshold = 3
                }
                temp += n
            }
            matrix += temp
        }
    }
}