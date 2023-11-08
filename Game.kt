class Game(state: State = State()) {

    var indexStates = 0
    val states = mutableListOf(state)

    fun step(point: Point): Boolean {
        return if (states[indexStates].board.cells[point.x][point.y] != ' ') false
        else true
    }

    fun takeBack(shift: Int): Boolean {
        return if (shift >= indexStates) false
        else true
    }
}

