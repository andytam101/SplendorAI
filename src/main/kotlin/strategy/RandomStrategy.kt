package strategy

import getAllCollectThree
import getAllCollectTwo
import removeRandomTokens
import Player
import PublicGameState
import Action
import Colour
import TokenInventory
import Royalty

class RandomStrategy : Strategy {
    override fun makeMove(player: Player, gameState: PublicGameState): Action {
        val purchases = player.getAllPurchaseBoard(gameState) + player.getAllPurchaseReserved()
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
                val reserves = player.getAllReserveTop(gameState) + player.getAllReserveBoard(gameState)
                return reserves.random()
            }
        }
    }

    override fun removeTokens(count: Int, tokens: TokenInventory): Map<Colour, Int> = removeRandomTokens(count, tokens)
    override fun chooseRoyalty(royalties: List<Royalty>): Royalty = royalties.random()
}