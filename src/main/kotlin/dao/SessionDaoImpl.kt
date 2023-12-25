package dao

import entity.*
import service.Converter
import service.exception.*
import java.time.LocalTime

class SessionDaoImpl(private val placeHandler: PlaceDao,
                     private val converter: Converter): SessionDao {
    override fun sellTicket(session: SessionEntity, ticket: TicketEntity) {
        val row = ticket.place.row
        val seat = ticket.place.seat
        if (row >= session.hall.size || seat >= session.hall[0].size) {
            throw PlaceNotFoundException("Такого места в зале не существует!")
        }
        if (converter.convertStringToLocalTime(session.time) < LocalTime.now()) {
            throw SessionTimeException("Данный сеанс уже начался, операция невозможна!")
        }
        session.hall[row][seat] = placeHandler.sellTicketForPlace(session.hall[row][seat])
    }

    override fun takePlace(session: SessionEntity, ticket: TicketEntity) {
        val row = ticket.place.row
        val seat = ticket.place.seat
        if (row >= session.hall.size || seat >= session.hall[0].size) {
            throw PlaceNotFoundException("Такого места в зале не существует!")
        }
        placeHandler.takePlace(session.hall[row][seat])
    }

    override fun returnTicket(session: SessionEntity, ticket: TicketEntity) {
        val row = ticket.place.row
        val seat = ticket.place.seat
        if (row >= session.hall.size || seat >= session.hall[0].size) {
            throw PlaceNotFoundException("Такого места в зале не существует!")
        }
        if (converter.convertStringToLocalTime(session.time) < LocalTime.now()) {
            throw SessionTimeException("Данный сеанс уже начался, операция невозможна!")
        }
        placeHandler.returnTicketForPlace(session.hall[row][seat])
    }

    override fun editTime(session: SessionEntity, newTime: String) {
        session.time = newTime
    }

    override fun makeHall(session: SessionEntity) {
        for (i in 1..10) {
            session.hall.add(arrayListOf())
            for (j in 1..15) {
                session.hall[i - 1].add(PlaceEntity(i, j))
            }
        }
    }

    override fun printHall(session: SessionEntity) {
        print(" \t")
        for (j in 1..session.hall[0].size) {
            print(j)
            print("\t")
        }
        println()
        var i = 1
        for (row in session.hall) {
            print(i++)
            print("\t")
            for (place in row) {
                if (place.bought) {
                    print("■\t")
                } else {
                    print("□\t")
                }
            }
            println()
        }
    }
}