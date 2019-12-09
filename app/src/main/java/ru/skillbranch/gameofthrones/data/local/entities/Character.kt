package ru.skillbranch.gameofthrones.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


data class Character(
    val id: String,
    val name: String,
    val gender: String,
    val culture: String,
    val born: String,
    val died: String,
    val titles: List<String> = listOf(),
    val aliases: List<String> = listOf(),
    val father: String, //rel
    val mother: String, //rel
    val spouse: String,
    val houseId: String//rel
)

@Entity(tableName = "character_item")
data class CharacterItem(
    @PrimaryKey
    val id: String,
    val house: String, //rel
    val name: String,
    val titles: List<String>,
    val aliases: List<String>
)

@Entity(tableName = "character_full")
data class CharacterFull(
    @PrimaryKey
    val id: String,
    val name: String,
    val words: String,
    val born: String,
    val died: String,
    val titles: List<String>,
    val aliases: List<String>,
    val house:String, //rel
    @Embedded(prefix = "father")
    val father: RelativeCharacter?,
    @Embedded(prefix = "mather")
    val mother: RelativeCharacter?
)

@Entity
data class RelativeCharacter(
    @PrimaryKey
    val id: String,
    val name: String,
    val house:String //rel
)