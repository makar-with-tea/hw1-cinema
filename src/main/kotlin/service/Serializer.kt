package service

import com.fasterxml.jackson.databind.ObjectMapper
import entity.*
import service.exception.FileFailureException
import com.fasterxml.jackson.databind.JsonNode
import java.io.File

class Serializer {
    companion object {
        val mapper = ObjectMapper()
    }

    private fun updateMaxMovieId() {
        val jsonFile = File("src\\main\\data\\maxMovieId.json")
        mapper.writeValue(jsonFile, getMaxMovieId() + 1)
    }

    private fun updateMaxTicketId() {
        val jsonFile = File("src\\main\\data\\maxTicketId.json")
        mapper.writeValue(jsonFile, getMaxTicketId() + 1)
    }

    fun getMaxMovieId(): Int {
        val jsonFile = File("src\\main\\data\\maxMovieId.json")
        if (!jsonFile.exists()) {
            return 0
        }
        val rootNode: JsonNode = mapper.readTree(jsonFile)
        return rootNode.asInt()
    }

    fun getMaxTicketId(): Int {
        val jsonFile = File("src\\main\\data\\maxTicketId.json")
        if (!jsonFile.exists()) {
            return 0
        }
        val rootNode: JsonNode = mapper.readTree(jsonFile)
        return rootNode.asInt()
    }

    fun writeMovie(movie: MovieEntity) {
        val folder = File("src\\main\\data\\movies\\movie${movie.movieId}\\sessions")
        if (!folder.exists()) {
            try {
                folder.mkdirs()
            }
            catch (e : Exception) {
                throw FileFailureException("Ошибка при создании директории фильма!")
            }
            updateMaxMovieId()
        }
        val jsonFile = File("src\\main\\data\\movies\\movie${movie.movieId}\\movie.json")
        try {
            mapper.writeValue(jsonFile, movie)
        }
        catch (e : Exception) {
            throw FileFailureException("Ошибка при создании файла фильма!")
        }
    }

    fun writeSession(session: SessionEntity) {
        val jsonFile = File("src\\main\\data\\movies\\movie${session.movieId}\\sessions\\session${session.sessionId}.json")
        try {
            mapper.writeValue(jsonFile, session)
        }
        catch (e : Exception) {
            println(e.message)
            throw FileFailureException("Ошибка при создании файла сеанса!")
        }
    }

    fun writeTicket(ticket: TicketEntity) {
        val folder = File("src\\main\\data\\tickets\\ticket${ticket.ticketId}")
        if (!folder.exists()) {
            try {
                folder.mkdirs()
            }
            catch (e : Exception) {
                throw FileFailureException("Ошибка при создании директории билетов!")
            }
            updateMaxTicketId()
        }
        val jsonFile = File("src\\main\\data\\tickets\\ticket${ticket.ticketId}\\ticket.json")
        try {
            mapper.writeValue(jsonFile, ticket)
        }
        catch (e : Exception) {
            throw FileFailureException("Ошибка при создании файла билета!")
        }
    }

    fun readMovie(movieId: Int) : MovieEntity {
        val jsonFile = File("src\\main\\data\\movies\\movie$movieId\\movie.json")
        try {
            return mapper.readValue(jsonFile, MovieEntity::class.java)
        }
        catch (e : Exception) {
            throw FileFailureException("Ошибка при чтении информации о фильме!")
        }
    }

    fun readSession(movieId: Int, sessionId: Int) : SessionEntity {
        val jsonFile = File("src\\main\\data\\movies\\movie$movieId\\sessions\\session$sessionId.json")
        try {
            return mapper.readValue(jsonFile, SessionEntity::class.java)
        }
        catch (e : Exception) {
            throw FileFailureException("Ошибка при чтении информации о сеансе!")
        }
    }

    fun readTicket(ticketId: Int) : TicketEntity {
        val jsonFile = File("src\\main\\data\\tickets\\ticket$ticketId\\ticket.json")
        try {
            return mapper.readValue(jsonFile, TicketEntity::class.java)
        }
        catch (e : Exception) {
            throw FileFailureException("Ошибка при чтении информации о билете!")
        }
    }

    fun deleteMovie(movieId: Int) {
        val folder = File("src\\main\\data\\movies\\movie${movieId}")
        try {
            folder.deleteRecursively()
        }
        catch (e : Exception) {
            throw FileFailureException("Ошибка при удалении фильма!")
        }
    }

    fun deleteSession(movieId: Int, sessionId: Int) {
        val jsonFile = File("src\\main\\data\\movies\\movie$movieId\\sessions\\session$sessionId.json")
        try {
            jsonFile.delete()
        }
        catch (e : Exception) {
            throw FileFailureException("Ошибка при удалении сеанса!")
        }
    }

    fun deleteTicket(ticketId: Int) {
        val jsonFile = File("src\\main\\data\\tickets\\ticket$ticketId\\ticket.json")
        try {
            jsonFile.delete()
        }
        catch (e : Exception) {
            throw FileFailureException("Ошибка при удалении билета!")
        }
    }
}