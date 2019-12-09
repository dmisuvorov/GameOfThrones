package ru.skillbranch.gameofthrones.data.local

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun listToString(list: List<String>): String = list.joinToString { ", " }

    @TypeConverter
    fun stringToListOfString(str: String): List<String> = str.split(", ")
}