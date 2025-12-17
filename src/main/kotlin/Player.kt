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

}