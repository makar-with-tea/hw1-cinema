package dao

import entity.SessionEntity
import entity.TicketEntity
import java.time.LocalTime

interface SessionDao {
    fun sellTicket(session: SessionEntity, ticket: TicketEntity)

    fun takePlace(session: SessionEntity, ticket: TicketEntity)

    fun returnTicket(session: SessionEntity, ticket: TicketEntity)

    fun editTime(session: SessionEntity, newTime: String)

    fun makeHall(session: SessionEntity)

    fun printHall(session: SessionEntity)
}