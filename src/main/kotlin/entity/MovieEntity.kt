package entity

data class MovieEntity(
    var name: String = "",
    val movieId: Int = -1,
    var info: String = "",
    var nextSessionId: Int = 0,
    var sessionCnt: Int = 0
)