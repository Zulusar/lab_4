open class Step(
    x: Int = 0, y:Int = 0, var param: List<String> = listOf("  ", "  "),
) : Point(x,y), Input {
    fun checkData(enterData: List<String>): Boolean {
        if (enterData.size == 2) {
            x = enterData[0].toInt()
            y = enterData[1].toInt()
            return true
        }
        return if (enterData.size == 4) {
            x = enterData[0].toInt()
            y = enterData[1].toInt()
            this.param = listOf(enterData[2], enterData[3])
            true
        } else false
    }
}