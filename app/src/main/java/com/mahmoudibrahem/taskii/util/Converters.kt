package com.mahmoudibrahem.taskii.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mahmoudibrahem.taskii.model.CheckItem
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
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