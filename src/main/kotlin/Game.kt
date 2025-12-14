class Game(val playerCount: Int) {
    val royalties = getStartingRoyalties(playerCount)

    private val levelOneDeck = levelOneCards.shuffled()
    private val levelTwoDeck = levelTwoCards.shuffled()
    private val levelThreeDeck = levelThreeCards.shuffled()

    private val levelOneBoard: Array<Card?> = Array(4) { i -> levelOneDeck[i] }
    private val levelTwoBoard: Array<Card?> = Array(4) { i -> levelTwoDeck[i] }
    private val levelThreeBoard: Array<Card?> = Array(4) { i -> levelThreeDeck[i] }

    private val bank = startingTokenBank(playerCount)

    var started: Boolean = false
    var ended: Boolean = false
    var turn: Int = 0

    val players: MutableList<Player> = mutableListOf()

    private fun ongoing(): Boolean = started && !ended
    private fun nextTurn() {
        turn = (turn + 1) % playerCount
    }

    fun addPlayer(player: Player) {
        if (started) return
        players.add(player)
    }

    fun start() {
        started = true
        play()
    }

    private fun play() {

    }

}
