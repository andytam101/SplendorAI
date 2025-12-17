class AutoGameStateHandler : GameStateHandler {
    private val levelOneDeck = levelOneCards.shuffled()
    private val levelTwoDeck = levelTwoCards.shuffled()
    private val levelThreeDeck = levelThreeCards.shuffled()

    private var levelOneIdx = 0
    private var levelTwoIdx = 0
    private var levelThreeIdx = 0

    override fun removeCard(card: Card) { /* Do nothing */ }
    override fun getRoyalties(playerCount: Int) = getStartingRoyalties(playerCount)

    override fun getStartingCards(level: Int): Array<Card?> = when (level) {
        1 -> Array(4) { i -> levelOneCards[i] }
        2 -> Array(4) { i -> levelTwoCards[i] }
        3 -> Array(4) { i -> levelThreeCards[i] }
        else -> throw Exception("Invalid Level")
    }

    override fun getNewCard(level: Int) = when(level) {
        1 -> {
            val res = levelOneDeck.getOrNull(levelOneIdx)
            levelOneIdx++
            res
        }
        2 -> {
            val res = levelTwoDeck.getOrNull(levelTwoIdx)
            levelTwoIdx++
            res
        }
        3 -> {
            val res = levelThreeDeck.getOrNull(levelThreeIdx)
            levelThreeIdx++
            res
        }
        else -> null
    }

    override fun canReserveTop(level: Int): Boolean = when(level) {
        1 -> levelOneIdx < levelOneCards.size
        2 -> levelTwoIdx < levelTwoCards.size
        3 -> levelThreeIdx < levelThreeCards.size
        else -> throw Exception("Invalid level $level")
    }
}
