import dao.PlaceDaoImpl
import dao.SessionDaoImpl
import entity.*
import service.Converter
import com.fasterxml.jackson.databind.ObjectMapper
import dao.MovieDaoImpl
import dao.RuntimeCinemaDao
import service.Validator
import java.io.File
import java.time.LocalTime


class Tester {
    fun buyTicket(session: SessionEntity) : Boolean {
        return false
    }
    fun returnTicket(ticket: TicketEntity) : Boolean {
        return false
    }

    fun comeToHall(ticket: TicketEntity) : Boolean {
        return false
    }
}

fun main(args: Array<String>) {
    val run = RuntimeCinemaDao(MovieDaoImpl(), SessionDaoImpl(PlaceDaoImpl(), Converter()))
    val validator = Validator()
    val converter = Converter()

    val rows = 10
    val seats = 15
    println(validator.checkIfTicketExists(0))


    println("Добро пожаловать!")
    val infoCommands = "КОМАНДЫ\n1. Добавить фильм\n" +
            "2. Изменить название фильма\n" +
            "3. Изменить описание фильма\n" +
            "4. Удалить фильм\n" +
            "5. Добавить сеанс фильма\n" +
            "6. Изменить время сеанса\n" +
            "7. Удалить сеанс\n" +
            "8. Продать билет покупателю\n" +
            "9. Осуществить возврат билета\n" +
            "10. Отметить, что обладатель данного билета вошел в зал\n" +
            "11. Вывести на экран информацию о фильмах\n" +
            "12. Вывести на экран информацию о сеансах фильма\n" +
            "13. Вывести на экран информацию о купленных билетах\n" +
            "14. Вывести изображение зала для конкретного сеанса\n" +
            "15. Вывести данную памятку" +
            "\nSTOP - остановка программы"
    println(infoCommands)
    var command = ""
    while (command != "STOP") {
        command = readln()
        while (!validator.validateIntInInterval(command)) {
            println("Повторите ввод команды (число от 1 до 15)")
            command = readln()
        }
        if (command == "STOP") {
            break
        }
        val commandNum = command.toInt()
        when (commandNum) {
            1 -> {
                println("Введите название фильма:")
                val name = readln()
                println("Введите описание фильма:")
                val info = readln()
                println("Ваш фильм добавлен в базу. Его id: ${run.addMovie(name, info)}")
            }
            2 -> {
                println("Введите id фильма:")
                var movieId = readln()
                while (!validator.validateMovieId(movieId)) {
                    println("Неверный id. Попробуйте снова!")
                    movieId = readln()
                }
                println("Введите новое название фильма:")
                val newName = readln()
                try {
                    run.editMovieName(movieId.toInt(), newName)
                    println("Название фильма с id=$movieId успешно изменено.")
                }
                catch (e: Exception) {
                    println(e.message)
                    println("Вы можете продолжить работу в программе, но будьте аккуратнее.")
                }

            }
            3 -> {
                println("Введите id фильма:")
                var movieId = readln()
                while (!validator.validateMovieId(movieId)) {
                    println("Неверный id. Попробуйте снова!")
                    movieId = readln()
                }
                println("Введите новое описание фильма:")
                val newInfo = readln()
                try {
                    run.editMovieName(movieId.toInt(), newInfo)
                    println("Описание фильма с id=$movieId успешно изменено.")
                }
                catch (e: Exception) {
                    println(e.message)
                    println("Вы можете продолжить работу в программе, но будьте аккуратнее.")
                }

            }
            4 -> {
                println("Введите id фильма:")
                var movieId = readln()
                while (!validator.validateMovieId(movieId)) {
                    print("Неверный id. Попробуйте снова!")
                    movieId = readln()
                }
                try {
                    run.deleteMovie(movieId.toInt())
                    print("Фильм с id=$movieId успешно удален.")
                }
                catch (e: Exception) {
                    println(e.message)
                    println("Вы можете продолжить работу в программе, но будьте аккуратнее.")
                }

            }
            5 -> {
                println("Введите id фильма:")
                var movieId = readln()
                while (!validator.validateMovieId(movieId)) {
                    println("Неверный id. Попробуйте снова!")
                    movieId = readln()
                }
                println("Введите время сеанса в формате hh:mm:")
                var time = readln()
                while (!validator.validateTime(time)) {
                    println("Неверный формат времени. Попробуйте снова!")
                    time = readln()
                }
                try {
                    print("Сеанс добавлен в базу. Его id: ")
                    println(run.addSession(movieId.toInt(), time))
                }
                catch (e: Exception) {
                    println(e.message)
                    println("Вы можете продолжить работу в программе, но будьте аккуратнее.")
                }
            }
            6 -> {
                println("Введите id фильма:")
                var movieId = readln()
                while (!validator.validateMovieId(movieId)) {
                    println("Неверный id. Попробуйте снова!")
                    movieId = readln()
                }
                println("Введите id сеанса:")
                var sessionId = readln()
                while (!validator.validateSessionId(movieId, sessionId)) {
                    println("Неверный id. Попробуйте снова!")
                    sessionId = readln()
                }
                println("Введите новое время сеанса в формате hh:mm:")
                var time = readln()
                while (!validator.validateTime(time)) {
                    println("Неверный формат времени. Попробуйте снова!")
                    time = readln()
                }
                try {
                    run.editSessionTime(movieId.toInt(), sessionId.toInt(), time)
                    println("Время сеанса успешно изменено.")
                }
                catch (e: Exception) {
                    println(e.message)
                    println("Вы можете продолжить работу в программе, но будьте аккуратнее.")
                }
            }
            7 -> {
                println("Введите id фильма:")
                var movieId = readln()
                while (!validator.validateMovieId(movieId) || !validator.checkIfMovieHasSessions(movieId.toInt())) {
                    println("Неверный id. Попробуйте снова!")
                    movieId = readln()
                }
                println("Введите id сеанса:")
                var sessionId = readln()
                while (!validator.validateSessionId(movieId, sessionId)) {
                    println("Неверный id. Попробуйте снова!")
                    sessionId = readln()
                }
                run.deleteSession(movieId.toInt(), sessionId.toInt())
                print("Сеанс с id=$sessionId успешно удален.")
            }
            8 -> {
                println("Введите id фильма:")
                var movieId = readln()
                while (!validator.validateMovieId(movieId) || !validator.checkIfMovieHasSessions(movieId.toInt())) {
                    println("Неверный id. Попробуйте снова!")
                    movieId = readln()
                }
                println("Введите id сеанса:")
                var sessionId = readln()
                while (!validator.validateSessionId(movieId, sessionId)) {
                    println("Неверный id. Попробуйте снова!")
                    sessionId = readln()
                }

                println("Зал на данный сеанс заполнен так (закрашенные квадраты означают купленные места):")
                run.printHall(movieId.toInt(), sessionId.toInt())

                println("Какое место выбрал покупатель? Введите ряд.")
                var strRow = readln()
                while (!validator.validateIntInInterval(strRow, 1, rows)) {
                    println("Некорректный ряд. Введите положительное число, меньшее или равное $rows.")
                    strRow = readln()
                }
                val row = strRow.toInt()

                println("Введите место.")
                var strSeat = readln()
                while (!validator.validateIntInInterval(strSeat, 1, seats)) {
                    println("Некорректное место. Введите положительное число, меньшее или равное $seats.")
                    strSeat = readln()
                }
                val seat = strSeat.toInt()
                try {
                    val ticketId = run.sellTicket(movieId.toInt(), sessionId.toInt(), PlaceEntity(row - 1, seat - 1))
                    println("Вы приобрели билет с id=$ticketId")
                }
                catch (e: Exception) {
                    println(e.message)
                    println("Вы можете продолжить работу в программе, но будьте аккуратнее.")
                }
            }
            9 -> {
                println("Введите id возвращаемого билета.")
                var ticketId = readln()
                while (!validator.validateTicketId(ticketId)) {
                    println("Неверный id. Попробуйте снова!")
                    ticketId = readln()
                }
                try {
                    run.returnTicket(ticketId.toInt())
                    println("Возврат билета удался.")
                }
                catch (e: Exception) {
                    println(e.message)
                    println("Вы можете продолжить работу в программе, но будьте аккуратнее.")
                }
            }
            10 -> {
                println("Введите id билета посетителя.")
                var ticketId = readln()
                while (!validator.validateTicketId(ticketId)) {
                    println("Неверный id. Попробуйте снова!")
                    ticketId = readln()
                }
                try {
                    run.takePlace(ticketId.toInt())
                    println("Факт входа посетителя в зал сохранен.")
                }
                catch (e: Exception) {
                    println(e.message)
                    println("Вы можете продолжить работу в программе, но будьте аккуратнее.")
                }
            }
            11 -> {
                try {
                    run.printMovies()
                }
                catch (e: Exception) {
                    println(e.message)
                    println("Вы можете продолжить работу в программе, но будьте аккуратнее.")
                }
            }
            12 -> {
                println("Введите id фильма:")
                var movieId = readln()
                while (!validator.validateMovieId(movieId)) {
                    println("Неверный id. Попробуйте снова!")
                    movieId = readln()
                }
                try {
                    run.printSessions(movieId.toInt())
                }
                catch (e: Exception) {
                    println(e.message)
                    println("Вы можете продолжить работу в программе, но будьте аккуратнее.")
                }
            }
            13 -> {
                try {
                    run.printTickets()
                }
                catch (e: Exception) {
                    println(e.message)
                    println("Вы можете продолжить работу в программе, но будьте аккуратнее.")
                }
            }
            14 -> {
                println("Введите id фильма:")
                var movieId = readln()
                while (!validator.validateMovieId(movieId)) {
                    println("Неверный id. Попробуйте снова!")
                    movieId = readln()
                }
                println("Введите id сеанса:")
                var sessionId = readln()
                while (!validator.validateSessionId(movieId, sessionId)) {
                    println("Неверный id. Попробуйте снова!")
                    sessionId = readln()
                }
                try {
                    run.printHall(movieId.toInt(), sessionId.toInt())
                }
                catch (e: Exception) {
                    println(e.message)
                    println("Вы можете продолжить работу в программе, но будьте аккуратнее.")
                }
            }
            15 -> println(infoCommands)
        }
        println("Введите команду. Чтобы получить список команд, введите 15.")
    }
}