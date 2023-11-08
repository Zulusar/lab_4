interface Input {
    companion object {
        fun parse(string: String): Any? {
            val exit = Exit()
            val takeBack = TakeBack()
            val division = string.split(' ').map(String::toString)
            return when (division[0].toInt()) {
                6 -> {
                    exit
                }
                7 -> {
                    takeBack
                }
                else -> null
            }
        }
    }
}
