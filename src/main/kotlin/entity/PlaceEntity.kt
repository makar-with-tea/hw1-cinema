package entity

data class PlaceEntity (
    val row : Int = -1,
    val seat: Int = -1,
    var bought: Boolean = false,
    var taken: Boolean = false // уже пришел гость
)