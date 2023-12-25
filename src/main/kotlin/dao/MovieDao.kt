package dao

import entity.MovieEntity
import entity.TicketEntity
import java.time.LocalTime

interface MovieDao {
    fun setName(movie: MovieEntity, newName: String) : MovieEntity

    fun setInfo(movie: MovieEntity, newInfo: String) : MovieEntity
}