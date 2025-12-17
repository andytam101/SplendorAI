data class PublicGameState(
    val levelOneBoard: List<Card?>,
    val levelTwoBoard: List<Card?>,
    val levelThreeBoard: List<Card?>,

    val players: List<Player>,
    val bank: Map<Colour, Int>,

    val canReserveTopOne: Boolean,
    val canReserveTopTwo: Boolean,
    val canReserveTopThree: Boolean,
)
