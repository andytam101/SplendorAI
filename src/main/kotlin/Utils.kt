fun getAllCollectThree(gameState: PublicGameState): List<Action.TakeThreeTokens> {
    val availableColours = gameState.bank.keys.filter { gameState.bank[it]!! > 0 && it != Colour.GOLD}
    return if (availableColours.size < 3) {
        listOf(Action.TakeThreeTokens(availableColours.toList()))
    } else {
        availableColours.combinationsOf3().map { Action.TakeThreeTokens(it) }
    }
}

fun getAllCollectTwo(gameState: PublicGameState): List<Action.TakeTwoTokens> =
    gameState.bank.mapNotNull { (colour, value) ->
        if (value >= 4 && colour != Colour.GOLD) Action.TakeTwoTokens(colour) else null
    }

fun removeRandomTokens(count: Int, tokens: TokenInventory): Map<Colour, Int> {
    val mutableTokens = tokens.toMap().toMutableMap()
    val remove: MutableMap<Colour, Int> = mutableMapOf()
    repeat(count) {
        val colours = mutableTokens.keys.filter { mutableTokens[it]!! > 0 }
        val toDispose = colours.random()
        remove[toDispose] = remove.getOrDefault(toDispose, 0) + 1
        mutableTokens[toDispose] = mutableTokens[toDispose]!! - 1
    }

    return remove.toMap()
}


fun <T> List<T>.combinationsOf3(): List<List<T>> =
    flatMapIndexed { i, a ->
        subList(i + 1, size).flatMapIndexed { j, b ->
            subList(i + j + 2, size).map { c ->
                listOf(a, b, c)
            }
        }
    }
