package com.example.auth

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromParking(parking: Parking?): String {
        return Gson().toJson(parking)
    }

    @TypeConverter
    fun toParking(json: String?): Parking? {
        return json?.let { Gson().fromJson(it, Parking::class.java) }
    }
}
