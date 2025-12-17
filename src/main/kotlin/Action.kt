sealed interface Action {

    sealed interface Purchase : Action {
        val card: Card
    }

    sealed interface Collection : Action
    sealed interface Reservation : Action

    data class TakeThreeTokens(val colours: List<Colour>) : Collection
    data class TakeTwoTokens(val colour: Colour) : Collection
    data class PurchaseBoard(val level: Int, val idx: Int, override val card: Card) : Purchase
    data class PurchaseReserved(override val card: Card) : Purchase
    data class ReserveBoard(val level: Int, val idx: Int, val card: Card) : Reservation
    data class ReserveTop(val level: Int) : Reservation
}
