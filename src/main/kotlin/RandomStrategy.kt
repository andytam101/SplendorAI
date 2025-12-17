class RandomStrategy : Strategy {
    override fun makeMove(player: Player, gameState: PublicGameState): Action {
        val purchases = getAllPurchaseBoard(player, gameState) + getAllPurchaseReserved(player)
        val collectThrees = getAllCollectThree(gameState)
        val collectTwos = getAllCollectTwo(gameState)

        if (purchases.isNotEmpty()) {
            // if can purchase: 70% purchase, 25% collect 3, 5 % collect 2
            val x = Math.random()
            if (x < 0.7) {
                return purchases.random()
            } else if (x < 0.95) {
                return collectThrees.random()
            } else {
                return collectTwos.randomOrNull() ?: collectThrees.random()
            }

        } else {
            // if cannot purchase: 80% collect 3, 15% collect 2, 5% reserve
            val x = Math.random()
            if (x < 0.8) {
                return collectThrees.random()
            } else if (x < 0.95) {
                return collectTwos.randomOrNull() ?: collectThrees.random()
            } else {
                val reserves = getAllReserveTop(player, gameState) + getAllReserveBoard(player, gameState)
                return reserves.random()
            }
        }
    }

    override fun removeTokens(count: Int, tokens: TokenInventory): Map<Colour, Int> = removeRandomTokens(count, tokens)
    override fun chooseRoyalty(royalties: List<Royalty>): Royalty = royalties.random()
}