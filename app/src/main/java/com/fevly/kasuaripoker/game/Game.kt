class Game {
}
/*=================HIGH CARD==================================
rule
- tidak ada pair
- tidak ada bobot yg lebih tinggi


* TODO di highcard, 1 kartu player tertinggi dikomparasi dgn 1 krt tertinggi diseluruh pemain

High card bisa batal kalau suitnya SAMA dikelima kartu (karena transform ke FLUSH)
============================================================ */



fun hasPair(allCards: List<String>): Boolean {
/*============================================================
  tested dgn AD, QH, 9H, AC, 3D
 ===============================================================*/
    val valueCounts = mutableMapOf<Int, Int>()
    for (card in allCards) {
        val value = cardValue(card)
        // kalau keynya sdh ada lgsg sj return (psti ada kembar)
        if (valueCounts.containsKey(value))
            return true
        else
            valueCounts.put(value, 0)
    }

    return false
}

fun main() {
    val bobot = mapOf(
        1 to "High card",
        2 to "1 Pair",
        3 to "2 Pair",
        4 to "Three of kinds",
        5 to "Straight",
        6 to "Flush",
        7 to "Full House",
        8 to "Four of Kind",
        9 to "Straight Flush",
        10 to "Royal Flush",

        )

    // s = spade (yg kayak skop pasir)
    // c = club (yg kayak daun singkong)
    // h = yg lope-lope
    // d = layangan
    val suits = arrayOf("H", "D", "C", "S")
    val ranks = arrayOf("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A")

    val deck = Array(52) { IntArray(2) }
    var index = 0

    // generasi 52 kartu deck
    for (suitIndex in 0 until suits.size) {
        for (rankIndex in 0 until ranks.size) {
            deck[index][0] = suitIndex  // Suit index
            deck[index][1] = rankIndex  // Rank index
            index++
        }
    }

    val myCards = mutableListOf("AD", "QH")
    val cardsOnTheBoard = mutableListOf("9H", "5C", "3D")

    val allCards = myCards + cardsOnTheBoard
    val hasPair = hasPair(allCards)

    println(hasPair)

}

fun printDeck(deck: Array<IntArray>, suits: Array<String>, ranks: Array<String>) {
    val maxCardLength = ranks.maxByOrNull { it.length }!!.length + suits.maxByOrNull { it.length }!!.length + 4
    val boxWidth = maxCardLength + 4
    val boxHeight = 5

    for (rowIndex in 0 until boxHeight) {
        for (colIndex in 0 until 13) {
            print("+${"-".repeat(boxWidth)}")
        }
        println("+")

        for (card in deck) {
            val suitIndex = card[0]
            val rankIndex = card[1]
            val suit = suits[suitIndex]
            val rank = ranks[rankIndex]
            val cardString = "$rank of $suit"
            val padding = " ".repeat(maxCardLength - cardString.length)

            print("| $cardString$padding |")
        }
        println()

        for (card in deck) {
            print("+${"-".repeat(boxWidth)}")
        }
        println("+")
    }
}

fun cardValue(card: String): Int {
    val rank = card.substring(0, card.length - 1) // diindex 0, sebanyak 1 kartu
    val suit = card.last()
    return when (rank) {
        "J" -> 11
        "Q" -> 12
        "K" -> 13
        "A" -> 14
        else -> rank.toIntOrNull() ?: 0
    }
}




