package service

import java.io.File

class Validator {
    private fun isDigit(arg: String): Boolean {
        for (ch in arg) {
            if (!ch.isDigit()) {
                return false
            }
        }
        return true
    }

    fun validateIntInInterval(arg : String, start : Int = 1, end: Int = 15) : Boolean {
        return arg == "STOP" || (isDigit(arg) && arg.toInt() in start..end)
    }

    fun validateMovieId(movieId : String): Boolean {
        return isDigit(movieId) && checkIfMovieExists(movieId.toInt())
    }

    fun validateSessionId(movieId : String, sessionId: String): Boolean {
        return isDigit(sessionId) && checkIfSessionExists(movieId.toInt(), sessionId.toInt())
    }

    fun validateTicketId(ticketId : String): Boolean {
        return isDigit(ticketId) && checkIfTicketExists(ticketId.toInt())
    }

    fun validateTime(arg: String) : Boolean {
        val listTime = arg.split(":")
        if (listTime.size != 2) {
            return false
        }
        val hour = listTime[0]
        val minute = listTime[1]
        return isDigit(hour) && isDigit(minute) && hour.toInt() in 0..23 && minute.toInt() in 0..59
    }

    fun checkIfMovieExists(movieId: Int) : Boolean {
        val jsonFile = File("src\\main\\data\\movies\\movie$movieId")
        return jsonFile.exists()
    }

    fun checkIfSessionExists(movieId: Int, sessionId : Int) : Boolean {
        val jsonFile = File("src\\main\\data\\movies\\movie$movieId\\sessions\\session$sessionId.json")
        return jsonFile.exists()
    }

    fun checkIfTicketExists(ticketId: Int) : Boolean {
        val jsonFile = File("src\\main\\data\\tickets\\ticket$ticketId\\ticket.json")
        return jsonFile.exists()
    }

    fun checkIfMovieHasSessions(movieId: Int) : Boolean {
        val serializer = Serializer()
        val movie = serializer.readMovie(movieId)
        return movie.sessionCnt > 0
    }
}