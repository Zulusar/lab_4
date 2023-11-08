class StateXO(board: Board = Board()) : AbstractState(board) {

    override val gameResult: String?
        get() = if (checkWin(board) == 'N') null
        else "Win ${checkWin(board)}"

    fun copyMassive(): Array<Array<Char>> {
        val copyMassiv:Array<Array<Char>> = arrayOf(emptyArray(), emptyArray(), emptyArray())
        for (i in this.board.cells.indices) {
            copyMassiv[i] = this.board.cells[i].clone()
        }
        return copyMassiv
    }

    override fun copy(z:Array<Array<Char>>): StateXO {
        val state = StateXO()
        for (i in state.board.cells.indices) {
            state.board.cells[i] = z[i].clone()
        }
        return state
    }

    override fun nextState(step: Step): StateXO {
        return copy(board.cells)
    }

    override fun step(step: Step): StateXO? {
        return if (!checkStep(step)) {
            println("Ошибка! Проверьте правильность данных")
            null
        } else {
            this.board.setAndCopy(Point(step.x, step.y), board[Point(step.x, step.y)])
            nextState(step)
        }
    }

    override fun checkStep(step: Step): Boolean {
        if (board.getOrNull(Point(step.x, step.y)) != null) {
            return false
        }
        return if (!board.stepOnTheBoard(Point(step.x, step.y))) {
            false
        }
        else true
    }

    fun checkWin(board: Board): Char {//проверка на выигрыш
        var a = 0//счетчик
        val winLines = arrayOf(//выигрышные комбинации
            arrayOf(arrayOf(0, 0), arrayOf(0, 1), arrayOf(0, 2)),
            arrayOf(arrayOf(1, 0), arrayOf(1, 1), arrayOf(1, 2)),
            arrayOf(arrayOf(2, 0), arrayOf(2, 1), arrayOf(2, 2)),
            arrayOf(arrayOf(0, 0), arrayOf(1, 0), arrayOf(2, 0)),
            arrayOf(arrayOf(0, 1), arrayOf(1, 1), arrayOf(2, 1)),
            arrayOf(arrayOf(0, 2), arrayOf(1, 2), arrayOf(2, 2)),
            arrayOf(arrayOf(0, 0), arrayOf(1, 1), arrayOf(2, 2)),
            arrayOf(arrayOf(0, 2), arrayOf(1, 1), arrayOf(2, 0))
        )

        for (lines in winLines) {
            val coord1 = lines[0]
            val coord2 = lines[1]
            val coord3 = lines[2]
            if (board.cells[coord1[0]][coord1[1]] == board.cells[coord2[0]][coord2[1]] && board.cells[coord1[0]][coord1[1]] == board.cells[coord3[0]][coord3[1]] && board.cells[coord1[0]][coord1[1]] == '0') {
                a += 3//увеличение счетчика для корректного определния победителя
            }
            if (board.cells[coord1[0]][coord1[1]] == board.cells[coord2[0]][coord2[1]] && board.cells[coord1[0]][coord1[1]] == board.cells[coord3[0]][coord3[1]] && board.cells[coord1[0]][coord1[1]] == 'X') {
                a += 2
            }
        }
        if (a == 3) return '0'
        return if (a == 2) 'X'
        else 'N'
    }
}

