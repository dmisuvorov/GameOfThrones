package ru.skillbranch.gameofthrones.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey
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

data class CharacterItem(
    val id: String,
    val house: String, //rel
    val name: String,
    val titles: List<String>,
    val aliases: List<String>
)

data class CharacterFull(
    val id: String,
    val name: String,
    val words: String,
    val born: String,
    val died: String,
    val titles: List<String>,
    val aliases: List<String>,
    val house: String, //rel
    val father: RelativeCharacter?,
    val mother: RelativeCharacter?
)

data class RelativeCharacter(
    val id: String,
    val name: String = "",
    val house: String = "" //rel
)

fun CharacterFull.newInstance(
    id: String = this.id, name: String = this.name, words: String = this.words,
    born: String = this.born, died: String = this.died, titles: List<String> = this.titles,
    aliases: List<String> = this.aliases, house: String = this.house,
    father: RelativeCharacter? = this.father, mother: RelativeCharacter? = this.mother
): CharacterFull =
    CharacterFull(id, name, words, born, died, titles, aliases, house, father, mother)