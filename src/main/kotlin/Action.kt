sealed interface Action {
    data class TakeThreeTokens(val c1: Colour, val c2: Colour, val c3: Colour) : Action
    data class TakeTwoTokens(val colour: Colour) : Action
    data class PurchaseBoard(val level: Int, val idx: Int, val card: Card) : Action
    data class PurchaseReserved(val card: Card) : Action
    data class ReserveBoard(val level: Int, val idx: Int, val card: Card) : Action
    data class ReserveTop(val level: Int) : Action
}
