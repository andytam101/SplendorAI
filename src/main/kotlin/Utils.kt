fun calculateCost(player: Player, card: Card): Map<Colour, Int> {
    /* Calculates cost without gold ONLY, no checking whether it can be afforded */
    val cost = card.cost.toMutableMap()
    val cards = player.getCardColours()

    cost.keys.forEach {
        cost[it] = (cost[it]!! - cards.getOrDefault(it, 0)).coerceAtLeast(0)
    }

    return cost
}


fun canAfford(player: Player, card: Card) = calculatePayment(card, player) != null


fun calculatePayment(card: Card, player: Player) = calculatePayment(calculateCost(player, card), player)
fun calculatePayment(cost: Map<Colour, Int>, player: Player): Map<Colour, Int>? {
    /* Checks whether it is affordable and includes use of gold if needed */
    val payment: Map<Colour, Int> = cost.keys.associateWith {
        player.getTokenCount(it).coerceAtMost(cost[it]!!)
    }

    val goldNeeded = cost.values.sum() - payment.values.sum()

    if (goldNeeded > player.getTokenCount(Colour.GOLD)) {
        return null
    } else {
        val paymentWithGold = payment.toMutableMap()
        paymentWithGold[Colour.GOLD] = goldNeeded
        return paymentWithGold.toMap()
    }
}


fun getAvailableRoyalties(player: Player, royalties: List<Royalty>): List<Royalty> {
    val playerColours = player.getCardColours()
    return royalties.filter {
        it.cost.all { (colour, count) -> playerColours.getOrDefault(colour, 0) >= count }
    }
}


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

fun getAllPurchaseBoardLevel(player: Player, level: Int, cards: List<Card?>): List<Action.PurchaseBoard> {
    val res = cards.map { it?.let { canAfford(player, it) } ?: false }

    return res.mapIndexedNotNull { idx, affordable ->
        if (affordable) Action.PurchaseBoard(
            level,
            idx,
            cards[idx]!!
        ) else null
    }
}

fun getAllPurchaseBoard(player: Player, gameState: PublicGameState): List<Action.PurchaseBoard> =
    getAllPurchaseBoardLevel(player, 1, gameState.levelOneBoard) +
            getAllPurchaseBoardLevel(player, 2, gameState.levelTwoBoard) +
            getAllPurchaseBoardLevel(player, 3, gameState.levelThreeBoard)


fun getAllReserveBoard(player: Player, gameState: PublicGameState): List<Action.ReserveBoard> =
    if (player.getReservedCount() >= 3) listOf()
    else {
        gameState.levelOneBoard.mapIndexedNotNull { idx, card -> card?.let { Action.ReserveBoard(1, idx, card) } } +
                gameState.levelTwoBoard.mapIndexedNotNull { idx, card ->
                    card?.let {
                        Action.ReserveBoard(
                            2,
                            idx,
                            card
                        )
                    }
                } +
                gameState.levelThreeBoard.mapIndexedNotNull { idx, card ->
                    card?.let {
                        Action.ReserveBoard(
                            3,
                            idx,
                            card
                        )
                    }
                }
    }


fun getAllPurchaseReserved(player: Player): List<Action.PurchaseReserved> {
    val reservedCards = player.getReservedCards()
    val affordableCards = reservedCards.filter { canAfford(player, it) }

    return affordableCards.map { Action.PurchaseReserved(it) }
}

fun getAllReserveTop(player: Player, gameState: PublicGameState): List<Action.ReserveTop> {
    if (player.getReservedCount() >= 3) return listOf()
    return listOf(
        gameState.canReserveTopOne,
        gameState.canReserveTopTwo,
        gameState.canReserveTopThree
    ).mapIndexedNotNull { idx, canReserve ->
        if (canReserve) Action.ReserveTop(idx) else null
    }
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
