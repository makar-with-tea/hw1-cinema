package dao

import entity.PlaceEntity
import entity.TicketEntity

interface PlaceDao {
    fun sellTicketForPlace(place: PlaceEntity) : PlaceEntity

    fun returnTicketForPlace(place: PlaceEntity) : PlaceEntity

    fun takePlace(place: PlaceEntity) : PlaceEntity
}