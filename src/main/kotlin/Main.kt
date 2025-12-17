import strategy.PurchaseCheapestStrategy
import strategy.RandomStrategy
import strategy.Strategy

fun main() {
    val players: List<Pair<String, Strategy>> = listOf(
        Pair("Random 1", RandomStrategy()),
        Pair("Random 2", RandomStrategy()),
        Pair("PCS 1", PurchaseCheapestStrategy()),
        Pair("PCS 2", PurchaseCheapestStrategy()),
    )
    val game = Game(players.size, AutoGameStateHandler())

    players.forEach { (name, strategy) -> game.addPlayer(name, strategy) }

    game.start()
}
