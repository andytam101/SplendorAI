val emptyTokenInventory = TokenInventory(0, 0, 0, 0, 0, 0)
fun startingTokenBank(playerCount: Int): TokenInventory = when (playerCount) {
    2 -> TokenInventory(4, 4, 4, 4, 4, 5)
    3 -> TokenInventory(5, 5, 5, 5, 5, 5)
    4 -> TokenInventory(7, 7, 7, 7, 7, 5)
    else -> throw Exception("Invalid player count")
}

val allRoyalties: List<Royalty> = listOf(
    Royalty(mapOf(Colour.BLACK to 4, Colour.WHITE to 4)),
    Royalty(mapOf(Colour.BLACK to 3, Colour.BLUE to 3, Colour.WHITE to 3)),
    Royalty(mapOf(Colour.BLUE to 4, Colour.WHITE to 4)),
    Royalty(mapOf(Colour.RED to 4, Colour.GREEN to 4)),
    Royalty(mapOf(Colour.BLUE to 4, Colour.GREEN to 4)),
    Royalty(mapOf(Colour.BLUE to 3, Colour.GREEN to 3, Colour.WHITE to 3)),
    Royalty(mapOf(Colour.BLACK to 3, Colour.RED to 3, Colour.WHITE to 3)),
    Royalty(mapOf(Colour.BLACK to 3, Colour.RED to 3, Colour.GREEN to 3)),
    Royalty(mapOf(Colour.BLACK to 4, Colour.RED to 4)),
    Royalty(mapOf(Colour.GREEN to 3, Colour.BLUE to 3, Colour.RED to 3))
)


/* PRE: playerCount >= 2 and playerCount <= 4 */
fun getStartingRoyalties(playerCount: Int): List<Royalty> = allRoyalties.shuffled().take(playerCount + 1)


val levelOneCards = listOf(
    Card(Colour.BLACK, 0, mapOf(Colour.BLUE to 1, Colour.GREEN to 1, Colour.RED to 1, Colour.WHITE to 1), 1),
    Card(Colour.BLACK, 0, mapOf(Colour.BLUE to 2, Colour.GREEN to 1, Colour.RED to 1, Colour.WHITE to 1), 1),
    Card(Colour.BLACK, 0, mapOf(Colour.BLUE to 2, Colour.RED to 1, Colour.WHITE to 2), 1),
    Card(Colour.BLACK, 0, mapOf(Colour.BLACK to 1, Colour.GREEN to 1, Colour.RED to 3), 1),
    Card(Colour.BLACK, 0, mapOf(Colour.GREEN to 2, Colour.RED to 1), 1),
    Card(Colour.BLACK, 0, mapOf(Colour.GREEN to 2, Colour.WHITE to 2), 1),
    Card(Colour.BLACK, 0, mapOf(Colour.GREEN to 3), 1),
    Card(Colour.BLACK, 1, mapOf(Colour.BLUE to 4), 1),
    Card(Colour.BLUE, 0, mapOf(Colour.BLACK to 1, Colour.GREEN to 1, Colour.RED to 1, Colour.WHITE to 1), 1),
    Card(Colour.BLUE, 0, mapOf(Colour.BLACK to 1, Colour.GREEN to 1, Colour.RED to 2, Colour.WHITE to 1), 1),
    Card(Colour.BLUE, 0, mapOf(Colour.GREEN to 2, Colour.RED to 2, Colour.WHITE to 1), 1),
    Card(Colour.BLUE, 0, mapOf(Colour.BLUE to 1, Colour.GREEN to 3, Colour.RED to 1), 1),
    Card(Colour.BLUE, 0, mapOf(Colour.BLACK to 2, Colour.WHITE to 1), 1),
    Card(Colour.BLUE, 0, mapOf(Colour.BLACK to 2, Colour.GREEN to 2), 1),
    Card(Colour.BLUE, 0, mapOf(Colour.BLACK to 3), 1),
    Card(Colour.BLUE, 1, mapOf(Colour.RED to 4), 1),
    Card(Colour.WHITE, 0, mapOf(Colour.BLACK to 1, Colour.BLUE to 1, Colour.GREEN to 1, Colour.RED to 1), 1),
    Card(Colour.WHITE, 0, mapOf(Colour.BLACK to 1, Colour.BLUE to 1, Colour.GREEN to 2, Colour.RED to 1), 1),
    Card(Colour.WHITE, 0, mapOf(Colour.BLACK to 1, Colour.BLUE to 2, Colour.GREEN to 2), 1),
    Card(Colour.WHITE, 0, mapOf(Colour.BLACK to 1, Colour.BLUE to 1, Colour.WHITE to 3), 1),
    Card(Colour.WHITE, 0, mapOf(Colour.BLACK to 1, Colour.RED to 2), 1),
    Card(Colour.WHITE, 0, mapOf(Colour.BLACK to 2, Colour.BLUE to 2), 1),
    Card(Colour.WHITE, 0, mapOf(Colour.BLUE to 3), 1),
    Card(Colour.WHITE, 1, mapOf(Colour.GREEN to 4), 1),
    Card(Colour.GREEN, 0, mapOf(Colour.BLACK to 1, Colour.BLUE to 1, Colour.RED to 1, Colour.WHITE to 1), 1),
    Card(Colour.GREEN, 0, mapOf(Colour.BLACK to 2, Colour.BLUE to 1, Colour.RED to 1, Colour.WHITE to 1), 1),
    Card(Colour.GREEN, 0, mapOf(Colour.BLACK to 2, Colour.BLUE to 1, Colour.RED to 2), 1),
    Card(Colour.GREEN, 0, mapOf(Colour.BLUE to 3, Colour.GREEN to 1, Colour.WHITE to 1), 1),
    Card(Colour.GREEN, 0, mapOf(Colour.BLUE to 1, Colour.WHITE to 2), 1),
    Card(Colour.GREEN, 0, mapOf(Colour.BLUE to 2, Colour.RED to 2), 1),
    Card(Colour.GREEN, 0, mapOf(Colour.RED to 3), 1),
    Card(Colour.GREEN, 1, mapOf(Colour.BLACK to 4), 1),
    Card(Colour.RED, 0, mapOf(Colour.BLACK to 1, Colour.BLUE to 1, Colour.GREEN to 1, Colour.WHITE to 1), 1),
    Card(Colour.RED, 0, mapOf(Colour.BLACK to 1, Colour.BLUE to 1, Colour.GREEN to 1, Colour.WHITE to 2), 1),
    Card(Colour.RED, 0, mapOf(Colour.BLACK to 2, Colour.GREEN to 1, Colour.WHITE to 2), 1),
    Card(Colour.RED, 0, mapOf(Colour.BLACK to 3, Colour.RED to 1, Colour.WHITE to 1), 1),
    Card(Colour.RED, 0, mapOf(Colour.BLUE to 2, Colour.GREEN to 1), 1),
    Card(Colour.RED, 0, mapOf(Colour.RED to 2, Colour.WHITE to 2), 1),
    Card(Colour.RED, 0, mapOf(Colour.WHITE to 3), 1),
    Card(Colour.RED, 1, mapOf(Colour.WHITE to 4), 1)
)

val levelTwoCards = listOf(
    Card(Colour.BLACK, 1, mapOf(Colour.BLUE to 2, Colour.GREEN to 2, Colour.WHITE to 3), 2),
    Card(Colour.BLACK, 1, mapOf(Colour.BLACK to 2, Colour.GREEN to 3, Colour.WHITE to 3), 2),
    Card(Colour.BLACK, 2, mapOf(Colour.BLUE to 1, Colour.GREEN to 4, Colour.RED to 2), 2),
    Card(Colour.BLACK, 2, mapOf(Colour.GREEN to 5, Colour.RED to 3), 2),
    Card(Colour.BLACK, 2, mapOf(Colour.WHITE to 5), 2),
    Card(Colour.BLACK, 3, mapOf(Colour.BLACK to 6), 2),
    Card(Colour.BLUE, 1, mapOf(Colour.BLUE to 2, Colour.GREEN to 2, Colour.RED to 3), 2),
    Card(Colour.BLUE, 1, mapOf(Colour.BLACK to 3, Colour.BLUE to 2, Colour.GREEN to 3), 2),
    Card(Colour.BLUE, 2, mapOf(Colour.BLUE to 3, Colour.WHITE to 5), 2),
    Card(Colour.BLUE, 2, mapOf(Colour.BLACK to 4, Colour.RED to 1, Colour.WHITE to 2), 2),
    Card(Colour.BLUE, 2, mapOf(Colour.BLUE to 5), 2),
    Card(Colour.BLUE, 3, mapOf(Colour.BLUE to 6), 2),
    Card(Colour.WHITE, 1, mapOf(Colour.BLACK to 2, Colour.GREEN to 3, Colour.RED to 2), 2),
    Card(Colour.WHITE, 1, mapOf(Colour.BLUE to 3, Colour.RED to 3, Colour.WHITE to 2), 2),
    Card(Colour.WHITE, 2, mapOf(Colour.BLACK to 2, Colour.GREEN to 1, Colour.RED to 4), 2),
    Card(Colour.WHITE, 2, mapOf(Colour.BLACK to 3, Colour.RED to 5), 2),
    Card(Colour.WHITE, 2, mapOf(Colour.RED to 5), 2),
    Card(Colour.WHITE, 3, mapOf(Colour.WHITE to 6), 2),
    Card(Colour.GREEN, 1, mapOf(Colour.GREEN to 2, Colour.RED to 3, Colour.WHITE to 3), 2),
    Card(Colour.GREEN, 1, mapOf(Colour.BLACK to 2, Colour.BLUE to 3, Colour.WHITE to 2), 2),
    Card(Colour.GREEN, 2, mapOf(Colour.BLACK to 1, Colour.BLUE to 2, Colour.WHITE to 4), 2),
    Card(Colour.GREEN, 2, mapOf(Colour.BLUE to 5, Colour.GREEN to 3), 2),
    Card(Colour.GREEN, 2, mapOf(Colour.GREEN to 5), 2),
    Card(Colour.GREEN, 3, mapOf(Colour.GREEN to 6), 2),
    Card(Colour.RED, 1, mapOf(Colour.BLACK to 3, Colour.RED to 2, Colour.WHITE to 2), 2),
    Card(Colour.RED, 1, mapOf(Colour.BLACK to 3, Colour.BLUE to 3, Colour.RED to 2), 2),
    Card(Colour.RED, 2, mapOf(Colour.BLUE to 4, Colour.GREEN to 2, Colour.WHITE to 1), 2),
    Card(Colour.RED, 2, mapOf(Colour.BLACK to 5, Colour.WHITE to 3), 2),
    Card(Colour.RED, 2, mapOf(Colour.BLACK to 5), 2),
    Card(Colour.RED, 3, mapOf(Colour.RED to 6), 2)
)

val levelThreeCards = listOf(
    Card(Colour.BLACK, 3, mapOf(Colour.BLUE to 3, Colour.GREEN to 5, Colour.RED to 3, Colour.WHITE to 3), 3),
    Card(Colour.BLACK, 4, mapOf(Colour.RED to 7), 3),
    Card(Colour.BLACK, 4, mapOf(Colour.BLACK to 3, Colour.GREEN to 3, Colour.RED to 6), 3),
    Card(Colour.BLACK, 5, mapOf(Colour.BLACK to 3, Colour.RED to 7), 3),
    Card(Colour.BLUE, 3, mapOf(Colour.BLACK to 5, Colour.GREEN to 3, Colour.RED to 3, Colour.WHITE to 3), 3),
    Card(Colour.BLUE, 4, mapOf(Colour.WHITE to 7), 3),
    Card(Colour.BLUE, 4, mapOf(Colour.BLACK to 3, Colour.BLUE to 3, Colour.WHITE to 6), 3),
    Card(Colour.BLUE, 5, mapOf(Colour.BLUE to 3, Colour.WHITE to 7), 3),
    Card(Colour.WHITE, 3, mapOf(Colour.BLACK to 3, Colour.BLUE to 3, Colour.GREEN to 3, Colour.RED to 5), 3),
    Card(Colour.WHITE, 4, mapOf(Colour.BLACK to 7), 3),
    Card(Colour.WHITE, 4, mapOf(Colour.BLACK to 6, Colour.RED to 3, Colour.WHITE to 3), 3),
    Card(Colour.WHITE, 5, mapOf(Colour.BLACK to 7, Colour.WHITE to 3), 3),
    Card(Colour.GREEN, 3, mapOf(Colour.BLACK to 3, Colour.BLUE to 3, Colour.RED to 3, Colour.WHITE to 5), 3),
    Card(Colour.GREEN, 4, mapOf(Colour.BLUE to 7), 3),
    Card(Colour.GREEN, 4, mapOf(Colour.BLUE to 6, Colour.GREEN to 3, Colour.WHITE to 3), 3),
    Card(Colour.GREEN, 5, mapOf(Colour.BLUE to 7, Colour.GREEN to 3), 3),
    Card(Colour.RED, 3, mapOf(Colour.BLACK to 3, Colour.BLUE to 5, Colour.GREEN to 3, Colour.WHITE to 3), 3),
    Card(Colour.RED, 4, mapOf(Colour.GREEN to 7), 3),
    Card(Colour.RED, 4, mapOf(Colour.BLUE to 3, Colour.GREEN to 6, Colour.RED to 3), 3),
    Card(Colour.RED, 5, mapOf(Colour.GREEN to 7, Colour.RED to 3), 3)
)
