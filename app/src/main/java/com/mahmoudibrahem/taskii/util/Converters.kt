package com.mahmoudibrahem.taskii.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mahmoudibrahem.taskii.model.CheckItem

class Converters {

     private val gson: Gson= Gson()

    @TypeConverter
    fun fromCheckListToString(checkList: List<CheckItem>): String {
        return gson.toJson(checkList)
    }

    @TypeConverter
    fun fromStringToCheckList(str: String): List<CheckItem> {
        val checkList = object : TypeToken<List<CheckItem>>() {}.type
        return gson.fromJson(str, checkList)
    }
}