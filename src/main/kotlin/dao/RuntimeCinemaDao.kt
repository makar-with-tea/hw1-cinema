package dao

import entity.*
import service.Serializer
import service.Validator
import service.exception.*

class RuntimeCinemaDao(private val movieHandler: MovieDao, private val sessionHandler: SessionDaoImpl) : CinemaDao {

    companion object {
        val serializer = Serializer()
        val validator = Validator()
    }
    private var nextId: Int = serializer.getMaxMovieId()
    private var nextTicketId: Int = serializer.getMaxTicketId()

    // returns new movie's id
    // movies can have similar names
    override fun addMovie(name: String, description: String): Int {
        serializer.writeMovie(MovieEntity(name, nextId, description))
        return nextId++
    }

    override fun deleteMovie(movieId: Int) {
        if (!validator.checkIfMovieExists(movieId)) {
            throw MovieNotFoundException("Такого фильма не существует!")
        }
        serializer.deleteMovie(movieId)
    }

    override fun editMovieName(movieId: Int, newName: String) {
        if (!validator.checkIfMovieExists(movieId)) {
            throw MovieNotFoundException("Такого фильма не существует!")
        }
        val movie = serializer.readMovie(movieId)
        movieHandler.setName(movie, newName)
        serializer.writeMovie(movie)
    }

    override fun editMovieInfo(movieId: Int, newInfo: String) {
        if (!validator.checkIfMovieExists(movieId)) {
            throw MovieNotFoundException("Такого фильма не существует!")
        }
        val movie = serializer.readMovie(movieId)
        movieHandler.setInfo(movie, newInfo)
        serializer.writeMovie(movie)
    }

    override fun addSession(movieId: Int, time: String): Int {
        if (!validator.checkIfMovieExists(movieId)) {
            throw MovieNotFoundException("Такого фильма не существует!")
        }
        val movie = serializer.readMovie(movieId)
        val session = SessionEntity(movie.movieId, movie.nextSessionId, time)
        movie.nextSessionId++
        movie.sessionCnt++
        sessionHandler.makeHall(session)
        serializer.writeSession(session)
        serializer.writeMovie(movie)
        return movie.nextSessionId - 1
    }

    override fun editSessionTime(movieId: Int, sessionId: Int, newTime: String) {
        if (!validator.checkIfMovieExists(movieId)) {
            throw MovieNotFoundException("Такого фильма не существует!")
        }
        if (!validator.checkIfSessionExists(movieId, sessionId)) {
            throw SessionNotFoundException("Такого сеанса не существует!")
        }
        val session = serializer.readSession(movieId, sessionId)
        sessionHandler.editTime(session, newTime)
        serializer.writeSession(session)
    }

    override fun deleteSession(movieId: Int, sessionId: Int) {
        val movie = serializer.readMovie(movieId)
        serializer.deleteSession(movieId, sessionId)
        movie.sessionCnt--
        serializer.writeMovie(movie)
    }

    override fun sellTicket(movieId: Int, sessionId: Int, place: PlaceEntity): Int {
        if (!validator.checkIfMovieExists(movieId)) {
            throw MovieNotFoundException("Такого фильма не существует!")
        }
        if (!validator.checkIfSessionExists(movieId, sessionId)) {
            throw SessionNotFoundException("Такого сеанса не существует!")
        }
        val ticket = TicketEntity(nextTicketId++, place, movieId, sessionId)
        val session = serializer.readSession(movieId, sessionId)
        sessionHandler.sellTicket(session, ticket)
        serializer.writeSession(session)
        serializer.writeTicket(ticket)
        return ticket.ticketId
    }

    override fun returnTicket(ticketId: Int) {
        val ticket = serializer.readTicket(ticketId)
        if (!validator.checkIfMovieExists(ticket.movieId)) {
            throw MovieNotFoundException("Такого фильма не существует!")
        }
        if (!validator.checkIfSessionExists(ticket.movieId, ticket.sessionId)) {
            throw SessionNotFoundException("Такого сеанса не существует!")
        }
        val movie = serializer.readMovie(ticket.movieId)
        if (ticket.movieId != movie.movieId) {
            throw ImproperTicketException("Ваш билет не относится к данному фильму!")
        }
        val session = serializer.readSession(ticket.movieId, ticket.sessionId)
        sessionHandler.returnTicket(session, ticket)
        serializer.writeSession(session)
        serializer.deleteTicket(ticket.ticketId)
    }

    override fun takePlace(ticketId: Int) {
        val ticket = serializer.readTicket(ticketId)
        val session = serializer.readSession(ticket.movieId, ticket.sessionId)
        sessionHandler.takePlace(session, ticket)
        serializer.writeSession(session)
        serializer.writeTicket(ticket)
    }

    override fun printMovies() {
        for (i in 0..<serializer.getMaxMovieId()) {
            if (validator.checkIfMovieExists(i)) {
                println(serializer.readMovie(i))
            }
        }
    }

    override fun printSessions(movieId: Int) {
        val movie = serializer.readMovie(movieId)
        for (i in 0..<movie.nextSessionId) {
            if (validator.checkIfSessionExists(movieId, i)) {
                println(serializer.readSession(movieId, i))
            }
        }
    }

    override fun printTickets() {
        for (i in 0..<serializer.getMaxTicketId()) {
            if (validator.checkIfTicketExists(i)) {
                println(serializer.readTicket(i))
            }
        }
    }

    override fun printHall(movieId: Int, sessionId: Int) {
        val session = serializer.readSession(movieId, sessionId)
        sessionHandler.printHall(session)
    }
}