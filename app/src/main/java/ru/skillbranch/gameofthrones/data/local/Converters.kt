package ru.skillbranch.gameofthrones.data.local

import androidx.room.TypeConverter
import ru.skillbranch.gameofthrones.data.local.entities.RelativeCharacter

class Converters {

    @TypeConverter
    fun listToString(list: List<String>): String = list.joinToString ( ", ")

    @TypeConverter
    fun stringToListOfString(str: String): List<String> = str.split(", ")

    @TypeConverter
    fun stringToRelativeCharacter(idCharacter: String): RelativeCharacter = RelativeCharacter(id = idCharacter)

    @TypeConverter
    fun relativeCharacterToString(relativeCharacter: RelativeCharacter): String = relativeCharacter.id
}