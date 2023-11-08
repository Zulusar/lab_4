abstract class AbstractState(val board: Board) {

    abstract val gameResult: String?
    abstract fun copy(z:Array<Array<Char>>): AbstractState
    open fun step(step: Step): AbstractState? {
        return if (!checkStep(step)) {
            println("Ошибка! Проверьте правильность данных")
            null
        } else {
            this.board.setAndCopy(Point(step.x, step.y), board[Point(step.x, step.y)])
            nextState(step)
        }
    }

    open fun checkStep(step: Step): Boolean {
        board.stepOnTheBoard(Point(step.x, step.y))
        board.getOrNull(Point(step.x, step.y))
        board.check()
        if (!board.stepOnTheBoard(Point(step.x, step.y)) || board.getOrNull(Point(step.x, step.y)) != null) return false
        return if (board.isFill != null) false
        else true
    }

    abstract fun nextState(step: Step): AbstractState

}