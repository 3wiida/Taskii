package com.mahmoudibrahem.taskii.util

import androidx.room.TypeConverter
import java.time.LocalDateTime

class Converters {

    @TypeConverter
    fun toDateTime(dateTime: String): LocalDateTime {
        return LocalDateTime.parse(dateTime)
    }

    @TypeConverter
    fun fromDateTime(dateTime: LocalDateTime): String {
        return dateTime.toString()
    }
}