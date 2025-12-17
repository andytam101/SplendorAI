class Game(val playerCount: Int, private val gameStateHandler: GameStateHandler) {
    val royalties = getStartingRoyalties(playerCount).toMutableList()
    private val bank = startingTokenBank(playerCount).copy()

    var started: Boolean = false
    var ended: Boolean = false
    var turn: Int = 0

    val levelOneBoard = gameStateHandler.getStartingCards(1)
    val levelTwoBoard = gameStateHandler.getStartingCards(2)
    val levelThreeBoard = gameStateHandler.getStartingCards(3)

    val players: MutableList<Player> = mutableListOf()

    private fun ongoing(): Boolean = started && !ended
    private fun nextTurn() {
        turn = (turn + 1) % playerCount
    }

    fun displayState() {
        players.forEach {
            println("${it.name}: ${it.getTokenInv()}")
            println(it.getCardColours())
        }
    }

    fun addPlayer(name: String, strategy: Strategy) {
        if (started) return
        val player = Player(players.size, name, strategy)
        players.add(player)
    }

    fun start() {
        started = true
        val winners = play()
        println(winners.joinToString(", ", prefix="Winners are: ") { it.name })
    }

    private fun play(): List<Player> {
        while (!ended || turn != 0) {
            // has to end with the first player
            val result = playOneTurn()
            if (result) {
                ended = true
            }
            nextTurn()
        }

        /* Check Winner */
        val maxPoints: Int = players.maxOf { it.getTotalPoints() }
        return players.filter { it.getTotalPoints() == maxPoints }
    }

    private fun playOneTurn(): Boolean {
        val player = players[turn]
        val gameState = PublicGameState(
            levelOneBoard.toList(),
            levelTwoBoard.toList(),
            levelThreeBoard.toList(),
            players,
            bank.toMap(),
            gameStateHandler.canReserveTop(1),
            gameStateHandler.canReserveTop(2),
            gameStateHandler.canReserveTop(3),

        )
        var actionPrint: Action?
        do {
            val action: Action = player.makeMove(gameState)
            val result: Boolean = when (action) {
                is Action.PurchaseBoard -> handlePurchaseBoard(player, action)
                is Action.PurchaseReserved -> handlePurchaseReserved(player, action)
                is Action.ReserveBoard -> handleReserveBoard(player, action)
                is Action.ReserveTop -> handleReserveTop(player, action)
                is Action.TakeThreeTokens -> handleTakeThreeTokens(player, action)
                is Action.TakeTwoTokens -> handleTakeTwoTokens(player, action)
            }
            actionPrint = action
        } while (!result)

        println("Player ${player.name} did $actionPrint")
        displayState()
        println()

        val availableRoyalties = getAvailableRoyalties(player, royalties)
        val royaltyToTake: Royalty? = if (availableRoyalties.size > 1) {
            player.chooseRoyalty(availableRoyalties)
        } else {
            availableRoyalties.firstOrNull()
        }

        if (royaltyToTake != null) {
            player.receiveRoyalty()
            royalties.remove(royaltyToTake)
            println("Player ${player.name} received $royaltyToTake")
            println()
        }

        if (player.getTokenCount() > 10) {
            val tokensToRemove = player.chooseRemoveTokens(player.getTokenCount() - 10)
            tokensToRemove.forEach { (colour, count) ->
                bank.updateTokenCount(colour, count)
                player.updateToken(colour, -count)
            }
            println("Player ${player.name} got rid of $tokensToRemove")
            println()
        }

        return player.getTotalPoints() >= 15
    }

    private fun removeCardFromBoard(level: Int, idx: Int) {
        val board = getBoard(level)
        board[idx] = gameStateHandler.getNewCard(level)
    }

    private fun getBoard(level: Int): Array<Card?> = when (level) {
        1 -> levelOneBoard
        2 -> levelTwoBoard
        3 -> levelThreeBoard
        else -> throw Exception("Invalid level $level")
    }

    private fun getCardFromBoard(level: Int, idx: Int): Card? {
        val board = getBoard(level)
        return board[idx]
    }

    private fun handleReserveTop(player: Player, reserveTop: Action.ReserveTop): Boolean {
        val card = gameStateHandler.getNewCard(reserveTop.level) ?: return false

        player.reserveCard(card)
        takeFromBank(player, Colour.GOLD)

        return true
    }

    private fun handlePurchaseBoard(player: Player, purchaseBoard: Action.PurchaseBoard): Boolean {
        if (purchaseBoard.card != getCardFromBoard(purchaseBoard.level, purchaseBoard.idx)) return false

        val cost = calculateCost(player, purchaseBoard.card)
        val payment = calculatePayment(cost, player) ?: return false

        payment.forEach { (colour, count) ->
            bank.updateTokenCount(colour, count)
            player.updateToken(colour, -count)
        }

        removeCardFromBoard(purchaseBoard.level, purchaseBoard.idx)
        player.purchaseCard(purchaseBoard.card)

        return true
    }

    private fun takeFromBank(player: Player, colour: Colour) {
        /* Bank gives 1 token of that colour to player */
        if (bank.getTokenCount(colour) > 0) {
            bank.updateTokenCount(colour, -1)
            player.updateToken(colour, 1)
        }

    }

    private fun handleReserveBoard(player: Player, reserveBoard: Action.ReserveBoard): Boolean {
        if (reserveBoard.card != getCardFromBoard(reserveBoard.level, reserveBoard.idx)) return false
        if (player.getReservedCount() == 3) return false

        removeCardFromBoard(reserveBoard.level, reserveBoard.idx)
        player.reserveCard(reserveBoard.card)

        takeFromBank(player, Colour.GOLD)
        return true
    }

    private fun handlePurchaseReserved(player: Player, purchaseReserved: Action.PurchaseReserved): Boolean {
        /* Assume purchaseReserved's card is legit */
        val card = purchaseReserved.card

        val cost = calculateCost(player, card)
        val payment = calculatePayment(cost, player) ?: return false

        payment.forEach { (colour, count) ->
            bank.updateTokenCount(colour, count)
            player.updateToken(colour, -count)
        }

        player.purchaseReservedCard(card)
        return true
    }

    private fun handleTakeTwoTokens(player: Player, takeTwoTokens: Action.TakeTwoTokens): Boolean {
        if (takeTwoTokens.colour == Colour.GOLD) return false
        if (bank.getTokenCount(takeTwoTokens.colour) < 4) return false
        bank.updateTokenCount(takeTwoTokens.colour, -2)
        player.updateToken(takeTwoTokens.colour, 2)
        return true
    }

    private fun handleTakeThreeTokens(player: Player, takeThreeTokens: Action.TakeThreeTokens): Boolean {
        if (takeThreeTokens.colours.any { it == Colour.GOLD } ) return false
        if (takeThreeTokens.colours.all {bank.getTokenCount(it) == 0}) return false

        takeThreeTokens.colours.forEach { takeFromBank(player, it) }

        return true
    }

}
