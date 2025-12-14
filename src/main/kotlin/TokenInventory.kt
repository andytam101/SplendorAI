data class TokenInventory(
    var red: Int,
    var green: Int,
    var blue: Int,
    var black: Int,
    var white: Int,
    var gold: Int,
) {
    fun updateTokenCount(colour: Colour, count: Int) = when (colour) {
        Colour.WHITE -> white += count
        Colour.GREEN -> green += count
        Colour.BLUE -> blue += count
        Colour.RED -> red += count
        Colour.BLACK -> black += count
        Colour.GOLD -> gold += count
    }
}
