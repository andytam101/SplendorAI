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

    fun getTokenCount(colour: Colour): Int = when(colour) {
        Colour.WHITE -> white
        Colour.GREEN -> green
        Colour.BLUE -> blue
        Colour.RED -> red
        Colour.BLACK -> black
        Colour.GOLD -> gold
    }

    fun getTokenCount(): Int = white + green + blue + red + black + gold

    fun toMap(): Map<Colour, Int> = mapOf(
        Colour.WHITE to white,
        Colour.GREEN to green,
        Colour.BLUE to blue,
        Colour.RED to red,
        Colour.BLACK to black,
        Colour.GOLD to gold
    )
}
