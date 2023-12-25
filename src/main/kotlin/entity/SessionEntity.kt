package entity

import java.time.LocalTime

data class SessionEntity (
    val movieId: Int = -1,
    val sessionId: Int = -1,
    var time: String = "00:00",
    var hall: ArrayList<ArrayList<PlaceEntity>> = arrayListOf()
)