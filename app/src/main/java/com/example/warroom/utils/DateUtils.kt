package com.example.warroom.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtils {

    /**
     * convert string date to
     *
     * @param formattedDate With format "dd/MM/yyyy"
     */
    fun convertFormattedDateToDate(formattedDate: String): Date? {
        val dates = formattedDate.split("/")
        if (dates.size < 3) return null
        val year = dates[2].toInt()
        val month = dates[1].toInt()
        val dayOfMonth = dates[0].toInt()
        //val date = LocalDate.of(year, month, dayOfMonth)
        return null
    }


}