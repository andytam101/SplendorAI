package strategy

import getAllCollectThree
import getAllCollectTwo
import removeRandomTokens
import Action
import PublicGameState
import Player
import TokenInventory
import Colour
import Royalty


class PurchaseCheapestStrategy : Strategy {
    override fun makeMove(player: Player, gameState: PublicGameState): Action {
        val allPurchases = player.getAllPurchaseBoard(gameState) + player.getAllPurchaseReserved()
        if (allPurchases.isNotEmpty()) {
            return allPurchases.minBy {
                player.calculatePayment(it.card)?.let { c -> effectiveCost(c) } ?: Integer.MAX_VALUE
            }
        } else {
            val collectThrees = getAllCollectThree(gameState)
            val collectTwos = getAllCollectTwo(gameState)
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

    fun effectiveCost(cost: Map<Colour, Int>): Int {
        /* Number of tokens */
        return cost.values.sum()
    }
}