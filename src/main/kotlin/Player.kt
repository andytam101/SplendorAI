import strategy.Strategy

class Player(val playerID: Int, val name: String, private val strategy: Strategy) {
    private val reservedCards: MutableList<Card> = mutableListOf()
    private val purchasedCards: MutableList<Card> = mutableListOf()

    private var royaltyCount = 0

    private val cardColours = mutableMapOf<Colour, Int>()
    private val tokenInventory: TokenInventory = emptyTokenInventory.copy()

    fun getCardColours(): Map<Colour, Int> = cardColours.toMap()
    fun getPurchasedCards(): List<Card> = purchasedCards.toList()

    fun makeMove(gameState: PublicGameState): Action = strategy.makeMove(this, gameState)

    fun updateToken(colour: Colour, count: Int) {
        tokenInventory.updateTokenCount(colour, count)
    }

    fun chooseRemoveTokens(count: Int): Map<Colour, Int> = strategy.removeTokens(count, tokenInventory)

    fun getTokenCount(colour: Colour): Int = tokenInventory.getTokenCount(colour)
    fun getTokenCount(): Int = tokenInventory.getTokenCount()
    fun getTokenInv(): TokenInventory = tokenInventory

    fun purchaseCard(card: Card) {
        purchasedCards.add(card)
        cardColours[card.colour] = cardColours.getOrDefault(card.colour, 0) + 1
    }

    fun getTotalPoints(): Int = purchasedCards.sumOf { it.points } + 3 * royaltyCount
    fun getReservedCount(): Int = reservedCards.size
    fun getReservedCards(): List<Card> = reservedCards.toList()

    fun reserveCard(card: Card) {
        reservedCards.add(card)
    }

    fun chooseRoyalty(royalties: List<Royalty>): Royalty = strategy.chooseRoyalty(royalties)

    fun receiveRoyalty() {
        royaltyCount++
    }

    fun purchaseReservedCard(card: Card) {
        purchaseCard(card)
        reservedCards.remove(card)
    }

    fun calculateCost(card: Card): Map<Colour, Int> {
        /* Calculates cost without gold ONLY, no checking whether it can be afforded */
        val cost = card.cost.toMutableMap()

        cost.keys.forEach {
            cost[it] = (cost[it]!! - cardColours.getOrDefault(it, 0)).coerceAtLeast(0)
        }

        return cost
    }

    fun calculatePayment(card: Card) = calculatePayment(calculateCost(card))

    fun calculatePayment(cost: Map<Colour, Int>): Map<Colour, Int>? {
        /* Checks whether it is affordable and includes use of gold if needed */
        val payment: Map<Colour, Int> = cost.keys.associateWith {
            getTokenCount(it).coerceAtMost(cost[it]!!)
        }

        val goldNeeded = cost.values.sum() - payment.values.sum()

        if (goldNeeded > getTokenCount(Colour.GOLD)) {
            return null
        } else {
            val paymentWithGold = payment.toMutableMap()
            paymentWithGold[Colour.GOLD] = goldNeeded
            return paymentWithGold.toMap()
        }
    }

    fun canAfford(card: Card) = calculatePayment(card) != null

    fun getAvailableRoyalties(royalties: List<Royalty>): List<Royalty> {
        return royalties.filter {
            it.cost.all { (colour, count) -> cardColours.getOrDefault(colour, 0) >= count }
        }
    }

    fun getAllPurchaseBoardLevel(level: Int, cards: List<Card?>): List<Action.PurchaseBoard> {
        val res = cards.map { it?.let { canAfford(it) } ?: false }

        return res.mapIndexedNotNull { idx, affordable ->
            if (affordable) Action.PurchaseBoard(
                level,
                idx,
                cards[idx]!!
            ) else null
        }
    }

    fun getAllPurchaseBoard(gameState: PublicGameState): List<Action.PurchaseBoard> =
        getAllPurchaseBoardLevel(1, gameState.levelOneBoard) +
                getAllPurchaseBoardLevel(2, gameState.levelTwoBoard) +
                getAllPurchaseBoardLevel(3, gameState.levelThreeBoard)

    fun getAllReserveBoard(gameState: PublicGameState): List<Action.ReserveBoard> =
        if (getReservedCount() >= 3) listOf()
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


    fun getAllPurchaseReserved(): List<Action.PurchaseReserved> {
        val affordableCards = reservedCards.filter { canAfford(it) }
        return affordableCards.map { Action.PurchaseReserved(it) }
    }

    fun getAllReserveTop(gameState: PublicGameState): List<Action.ReserveTop> {
        if (getReservedCount() >= 3) return listOf()
        return listOf(
            gameState.canReserveTopOne,
            gameState.canReserveTopTwo,
            gameState.canReserveTopThree
        ).mapIndexedNotNull { idx, canReserve ->
            if (canReserve) Action.ReserveTop(idx) else null
        }
    }

}