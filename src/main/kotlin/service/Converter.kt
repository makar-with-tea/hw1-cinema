package service

import java.time.LocalTime
import service.exception.*

class Converter() {
    fun convertStringToLocalTime(str: String) : LocalTime {
        val listTime = str.split(":")
        val hour = listTime[0].toInt()
        val minute = listTime[1].toInt()
        return LocalTime.of(hour, minute)
    }

    fun convertLocalTimeToString(lt: LocalTime) : String {
        return lt.hour.toString() + ":" + lt.minute.toString()
    }
}