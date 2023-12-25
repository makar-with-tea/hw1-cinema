package entity

data class TicketEntity(
    val ticketId: Int = -1,
    val place: PlaceEntity = PlaceEntity(),
    val movieId: Int = -1,
    val sessionId: Int = -1
)
