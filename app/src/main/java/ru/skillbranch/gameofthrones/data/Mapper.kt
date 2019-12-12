package ru.skillbranch.gameofthrones.data

import ru.skillbranch.gameofthrones.data.local.entities.Character
import ru.skillbranch.gameofthrones.data.local.entities.House
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import ru.skillbranch.gameofthrones.data.remote.res.shortName

fun HouseRes.toHouse(): House =
    House(
        id = shortName(),
        name = name,
        region = region,
        coatOfArms = coatOfArms,
        words = words,
        titles = titles,
        seats = seats,
        currentLord = currentLord,
        heir = heir,
        overlord = overlord,
        founded = founded,
        founder = founder,
        diedOut = diedOut,
        ancestralWeapons = ancestralWeapons
    )

fun CharacterRes.toCharacter(): Character =
    Character(
        id = url.split("/").last(),
        name = name,
        gender = gender,
        culture = culture,
        born = born,
        died = died,
        titles = titles,
        aliases = aliases,
        father = father.split("/").lastOrNull() ?: "",
        mother = mother.split("/").lastOrNull() ?: "",
        spouse = spouse,
        houseId = houseId
    )

