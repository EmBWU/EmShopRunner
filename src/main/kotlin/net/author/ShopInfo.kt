package net.author

import net.botwithus.rs3.game.Coordinate

data class Shops(
    val debug: String,
    val name: String,
    val position: Coordinate,
    val thingsToBuy: MutableList<String> = mutableListOf(),
    val type : String,
)
val runes = mutableListOf("Air rune", "Water rune", "Earth rune", "Fire rune",
    "Mind rune", "Body rune",  "Nature rune","Death rune", "Law rune", "Blood rune", "Soul rune")
val feather = mutableListOf("Feather", "Feather pack")
val shops = mutableListOf(
    Shops(
        debug = "Yanille Pet Shop",
        name = "Pet shop owner",
        position = Coordinate(2577, 3081, 0),
        thingsToBuy = mutableListOf("Nuts", "Bolts","Beetle bits"),
        type = "Miscellaneous"
    ),
    Shops(
        debug = "Wizards Guild",
        name = "Magic Store owner",
        position = Coordinate(2594,3087,1),
        thingsToBuy = runes,
        type = "Runes"
    ),
    Shops(
        debug = "Al Kharid",
        name = "Ali Morrisane",
        position = Coordinate(3300,3210, 0),
        thingsToBuy = runes,
        type = "Runes"
    ),
    Shops(
        debug = "Edgeville",
        name = "Mage of Zamorak",
        position = Coordinate(3102,3557, 0),
        thingsToBuy = runes,
        type = "Runes"
    ),
    Shops(
        debug = "Port Sarim",
        name = "Betty",
        position = Coordinate(3014,3259, 0),
        thingsToBuy = runes,
        type = "Runes"
    ),
    Shops(
        debug = "Port Sarim",
        name = "Gerrant",
        position = Coordinate(3014,3224, 0),
        thingsToBuy = feather,
        type = "Feather"
    ),
    Shops(
        debug = "Void Knight Outpost",
        name = "Squire",
        position = Coordinate(2630,2658, 0),
        thingsToBuy = runes,
        type = "Runes"
    ),
    Shops(
        debug = "Burthorpe",
        name = "Apprentice Clara",
        position = Coordinate(2915,3543, 0),
        thingsToBuy = runes,
        type = "Runes"
    ),
    Shops(
        debug = "Varrock",
        name = "Aubury",
        position = Coordinate(3194,3254, 0),
        thingsToBuy = runes,
        type = "Runes"
    ),
    Shops(
        debug = "Lumbridge",
        name = "Hank",
        position = Coordinate(3253,3420, 0),
        thingsToBuy = feather,
        type = "Feather"
    ),
    Shops(
        debug = "Menaphos",
        name = "Meena",
        position = Coordinate(3213,2663,0),
        thingsToBuy = feather,
        type = "Feather"
    ),
    Shops(
        debug = "Oo'Glog",
        name = "Frawd",
        position = Coordinate(2548,2839,0),
        thingsToBuy = feather,
        type = "Feather"
    ),
)
