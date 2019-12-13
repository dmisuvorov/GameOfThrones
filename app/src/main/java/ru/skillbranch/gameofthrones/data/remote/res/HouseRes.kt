package ru.skillbranch.gameofthrones.data.remote.res

import ru.skillbranch.gameofthrones.util.dropLastUntil

data class HouseRes(
    val url: String,
    val name: String,
    val region: String,
    val coatOfArms: String,
    val words: String,
    val titles: List<String> = listOf(),
    val seats: List<String> = listOf(),
    val currentLord: String,
    val heir: String,
    val overlord: String,
    val founded: String,
    val founder: String,
    val diedOut: String,
    val ancestralWeapons: List<String> = listOf(),
    val cadetBranches: List<Any> = listOf(),
    val swornMembers: List<String> = listOf()
)

fun HouseRes.shortName(): String {
    return name.split(" ").dropLastUntil { it == "of" }.last()
}