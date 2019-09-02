class Node {
    var threshold: Int
    var index: NodeIndex
    var userName: String = ""
    var currentValue: Int = 0
    var adjacencyList = arrayOf<Node>()

    constructor(x: Int, y: Int) {
        this.threshold = 3
        this.index = NodeIndex(x, y)
    }

    fun handleThresholdReached() {
        currentValue = 0
        for (i in adjacencyList) {
            i.addOneBy(this.userName)
        }
    }

    private fun addOneBy(user: String) {
        userName = user
        addOne()
    }

    fun addOne() {
        if (currentValue == threshold) {
            handleThresholdReached()
        } else {
            currentValue += 1
        }
    }
}

class NodeIndex {
    var x: Int
    var y: Int
    constructor(x: Int, y: Int) {
        this.x = x
        this.y = y
    }
}