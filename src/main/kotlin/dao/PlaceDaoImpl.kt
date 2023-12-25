package dao

import service.exception.*
import entity.PlaceEntity
import entity.TicketEntity

class PlaceDaoImpl: PlaceDao {
    override fun sellTicketForPlace(place: PlaceEntity) : PlaceEntity {
        if (place.bought) {
            throw ImproperTicketException("Это место уже куплено!")
        }
        place.bought = true
        return place
    }

    override fun returnTicketForPlace(place: PlaceEntity) : PlaceEntity {
        if (!place.bought) {
            throw ImproperTicketException("Это место не было куплено!")
        }
        if (place.taken) {
            throw ImproperTicketException("Это место уже занято, возврат невозможен!")
        }
        place.bought = false
        return place
    }

    override fun takePlace(place: PlaceEntity) : PlaceEntity {
        if (!place.bought) {
            throw ImproperTicketException("Это место не было куплено!")
        }
        if (place.taken) {
            throw ImproperTicketException("Это место уже занято!")
        }
        place.taken = true
        return place
    }
}