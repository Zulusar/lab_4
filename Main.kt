fun main() {
    println(
        "Доброго времени суток! Если Вы хотите поиграть в Крестики-Нолики - введите цифру 1, если в Балду - введите 2"
    )
    val xO =
        """Вы выбрали Крестики-Нолики. Правила просты: введите цифры от 0 до 2 через пробел, обозначая координаты, если хотите сделать ход.
        Если хотите откатить игру - введите 3 и количество ходов, на которое желаете вернуться.
        Приятной игры.
    """.trimMargin()
    val balda =
        """Вы выбрали игру Балда. Правила просты: введите 1 слово (не более 5 букв), после чего вводите цифры от 0 до 4, символ, который добавляете
        и слово, которое вы подразумевали. Кто наберт больше очков, тот и выиграл.
        Приятной игры, желаю удачи.
    """.trimMargin()
    val choose: Int = readln().toInt()
    var enterData: String?
    if (choose == 1) {
        println(xO)
        val string1 = "         "
        val cells: Array<Array<Char>> = arrayOf(emptyArray(), emptyArray(), emptyArray())
        val field = Board(cells, string1)
        val condition = StateXO(field)
        var coordinates: Step
        val allGamesXO = MultiGame(condition)
        var enterDataForXO: List<String>?
        var iter = 0

        do {
            println("Введите 6, если хотите выйти.Введите координаты или команду на откат(цифра 7 и количество ходов для отката):")
            enterData = readln()
            when (Input.parse(enterData)) {
                is Exit -> {
                    println("Вы ввели команду на выход. До свидания!")
                    return
                }

                is TakeBack -> {
                    enterDataForXO = enterData.split(' ')
                    field.cells = allGamesXO.states[enterDataForXO[1].toInt()].board.cells
                    allGamesXO.states.clear()
                    field.printField()
                    when (iter % 2) {
                        0 -> iter = enterDataForXO[1].toInt()
                        1 -> iter = enterDataForXO[1].toInt() + 1
                    }
                    println("Введите 6, если хотите выйти.Введите координаты или команду на откат(цифра 7 и количество ходов для отката):")
                    enterData = readln()
                    enterDataForXO = enterData.split(' ')
                    coordinates = Step(x = enterDataForXO[0].toInt(), y = enterDataForXO[1].toInt())
                }

                else -> {
                    enterDataForXO = enterData.split(' ')
                    coordinates = Step(x=enterDataForXO[0].toInt(), y=enterDataForXO[1].toInt())
                }
            }
            condition.checkStep(coordinates)
            if (!condition.checkStep(coordinates)) {
                do {
                    println("Проверьте данные и введите их снова:")
                    enterDataForXO = readln().split(' ')
                    coordinates = Step(x = enterDataForXO[0].toInt(), y = enterDataForXO[1].toInt())
                } while (!condition.checkStep(coordinates))
            }
            allGamesXO.states.add(condition.copy(condition.copyMassive()))
            condition.step(coordinates)
            field.check()
            field.printField()
            condition.checkWin(field)
            if (condition.gameResult != null) {
                println(condition.gameResult)
                return
            }
            if(field.isFill!=null) {
                println(field.isFill)
                return
            }
            iter++
        } while (true)

    }


    if (choose == 2) {
        println(balda)
        val field = Board()
        val newField = field.rerecoding(field)
        var iter = 0
        val condition = StateBalda(newField, iter)
        var coordinates = Step()
        val allGamesBalda = MultiGame(condition)
        var enterDataForBalda: List<String>?
        val enterFirstWord = readln().toCharArray()
        for (i in newField.cells[2].indices) {
            newField.cells[2][i] = enterFirstWord[i]
        }
        newField.printField()

        do {
            println("Введите 6, если хотите выйти.Введите координаты, букву и слово, которое вы хотите добавить на доску или команду на откат (цифра 7 и количество ходов на откат):")
            enterData = readln()
            Input.parse(enterData)
            when (Input.parse(enterData)) {
                is TakeBack -> {
                    enterDataForBalda = enterData.split(' ')
                    newField.cells = allGamesBalda.states[enterDataForBalda[1].toInt()].board.cells
                    allGamesBalda.states.clear()
                    newField.printField()
                    when (iter % 2) {
                        0 -> iter = enterDataForBalda[1].toInt() + 1
                        1 -> iter = enterDataForBalda[1].toInt()
                    }
                    println("Введите 6, если хотите выйти.Введите координаты или команду на откат(цифра 7 и количество ходов для отката):")
                    enterData = readln()
                    enterDataForBalda = enterData.split(' ')
                    coordinates = Step(
                        x = enterDataForBalda[0].toInt(),
                        y = enterDataForBalda[1].toInt(),
                        param = listOf(enterDataForBalda[2], enterDataForBalda[3])
                    )
                }

                is Exit -> {
                    println("Вы ввели команду на выход. До свидания!")
                    condition.checkWin()
                    println(condition.gameResult)
                    return
                }

                else -> {
                    enterDataForBalda = enterData.split(" ")
                    coordinates.checkData(enterDataForBalda)
                }
            }
            newField.getOrNull(coordinates)
            if (newField.getOrNull(coordinates) != null) {
                do {
                    println("Введите координаты, букву и слово, которое вы хотите добавить на доску")
                    enterDataForBalda = readln().split(" ")
                    coordinates.checkData(enterDataForBalda)
                    newField.getOrNull(coordinates)
                } while (field.getOrNull(coordinates) != null)
            }
            allGamesBalda.states.add(condition.copy(condition.copyMassive()))
            condition.checkStep(coordinates)
            condition.step(coordinates)
            newField.printField()
            if(newField.newCheck()!=null) {
                return
            }
            newField.newCheck()
            iter++
        } while (true)
    }
}

