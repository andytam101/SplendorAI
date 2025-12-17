interface GameStateHandler {
    /* In charge of spawning in new cards and starting royalties */
    fun removeCard(card: Card)
    fun getNewCard(level: Int): Card?
    fun getStartingCards(level: Int): Array<Card?>
    fun getRoyalties(playerCount: Int): List<Royalty>

    fun canReserveTop(level: Int): Boolean
}
