class StateBalda(
    board: Board = Board(),
    var turn: Int = 1,
    private val words1: ArrayList<String> = arrayListOf(),
    private val words2: ArrayList<String> = arrayListOf()
) : AbstractState(board) {

    fun copyMassive(): Array<Array<Char>> {
        val copyMassiv: Array<Array<Char>> = arrayOf(
            emptyArray(), emptyArray(), emptyArray(), emptyArray(),
            emptyArray()
        )
        for (i in this.board.cells.indices) {
            copyMassiv[i] = this.board.cells[i].clone()
        }
        return copyMassiv
    }

    override fun copy(z: Array<Array<Char>>): StateBalda {
        val state = StateBalda(board.rerecoding())
        for (i in state.board.cells.indices) {
            state.board.cells[i] = z[i].clone()
        }
        return state
    }

    override fun nextState(step: Step): StateBalda {
        return copy(board.cells)
    }

    override fun checkStep(step: Step): Boolean {
        board.stepOnTheBoard(Point(step.x, step.y))
        board.getOrNull(Point(step.x, step.y))
        return if (!board.stepOnTheBoard(Point(step.x, step.y)) || board.getOrNull(Point(step.x, step.y)) != null) {
            false
        } else {
            true
        }
    }

    override fun step(step: Step): StateBalda? {
        return if (!checkStep(step)) {
            println("Ошибка! Проверьте правильность данных")
            null
        } else {
            turn++
            when (turn % 2) {
                0 -> words1.add(step.param[1])
                1 -> words2.add(step.param[1])
            }
            val symbol: CharArray = step.param[0].toCharArray()
            this.board.setAndCopy(Point(step.x, step.y), symbol[0])
            nextState(step)
        }
    }

    fun checkWin(): String? {
        if (words1.size > words2.size) {
            return ("Win 1 player!")
        }
        return if (words1.size < words2.size) {
            ("Win 2 player!")
        } else null
    }

    override val gameResult: String
        get() = "${checkWin()}"


}