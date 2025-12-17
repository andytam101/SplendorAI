package strategy

import Player
import PublicGameState
import Action
import TokenInventory
import Colour
import Royalty


interface Strategy {
    fun makeMove(player: Player, gameState: PublicGameState): Action

    // function will only be called after makeMove, so use previous gameState
    fun removeTokens(count: Int, tokens: TokenInventory): Map<Colour, Int>

    // function will only be called after makeMove, so use previous gameState
    fun chooseRoyalty(royalties: List<Royalty>): Royalty
}