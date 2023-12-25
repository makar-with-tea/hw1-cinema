package dao

import entity.PlaceEntity
import entity.SessionEntity
import entity.TicketEntity
import java.time.LocalTime

interface CinemaDao {
    fun addMovie(name: String, description: String = "") : Int

    fun editMovieName(movieId: Int, newName: String)

    fun editMovieInfo(movieId: Int, newInfo: String)

    fun deleteMovie(movieId: Int)

    fun addSession(movieId: Int, time: String) : Int

    fun editSessionTime(movieId: Int, sessionId: Int, newTime: String)

    fun deleteSession(movieId: Int, sessionId: Int)

    fun sellTicket(movieId: Int, sessionId: Int, place: PlaceEntity) : Int

    fun returnTicket(ticketId: Int)

    fun takePlace(ticketId: Int)

    fun printMovies()

    fun printSessions(movieId: Int)

    fun printTickets()

    fun printHall(movieId: Int, sessionId: Int)
}