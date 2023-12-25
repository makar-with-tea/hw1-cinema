package dao

import entity.*
import service.Serializer
import service.exception.*
import java.time.LocalTime

class MovieDaoImpl : MovieDao {

    override fun setName(movie: MovieEntity, newName: String) : MovieEntity {
        movie.name = newName
        return movie
    }

    override fun setInfo(movie: MovieEntity, newInfo: String) : MovieEntity {
        movie.info = newInfo
        return movie
    }
}