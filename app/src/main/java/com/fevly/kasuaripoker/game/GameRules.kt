/*==========================================
Author : Fevly Pallar
contact : fevly.pallar@gmail.com
=========================================*/
class GameRules {}

/*=================HIGH CARD==================================
rule
- tidak ada pair
- tidak ada bobot yg lebih tinggi
* TODO di highcard, 1 kartu player tertinggi dikomparasi dgn 1 krt tertinggi diseluruh pemain
High card bisa batal kalau suitnya SAMA di kelima kartu (karena transform ke FLUSH)
============================================================ */
fun isHighCard(allCards: List<String>): Boolean {
   return !has1Pair(allCards) 
            && !has2Pair(allCards)
            && !is4Kinds(allCards)
            && !isFlush(allCards)
            && !isStraightNon10Starts(allCards)
            && !isStraight10Starts(allCards)
            && !isStraigtFlush(allCards)
            && !isRoyalFlush(allCards)
}
// input test [4, 9, 10, J, Q, K, A] should return false

/*======================================================================
 Dari ke-7 kartu, line bisa trdpt 2 jenis straight sekaligus:

 Straight (start dari rank <10) sekaligus (start dari rank 10 [untuk case royal flash])
 
 Contoh :
        [4, 9, 10, J, Q, K, A]
        
  - straight start rank < 10 = [9, 10, J, Q, K]
  - straight start rank 10 (royal flash) =  [10, J, Q, K, A]

 Di kasus ini. Bobot tertinggilah yg sah, sehingga line terbaik player adalah:

 [10, J, Q, K, A]
 ==========================================================================*/
fun isStraightNon10Starts(allCards: List<String>): Boolean { // straight ini dipisahin (untuk permudah case royal flush)
    val rankList: MutableList<Int> = mutableListOf()

    if (!isStraight10Starts(allCards)) {
        for (card in allCards) {
            val currRank = stringRankToIntRank(card)
            rankList.add(currRank)
        }
        val sortedRank = rankList.sorted()
        var counter = 0
        var numOfCurrentCard = 0

        for (rank in sortedRank) {
            if (counter > 0 && (sortedRank.get(counter) - sortedRank.get(counter - 1)) == 1) {
                /*=======================================================
                 rules:
                 1. kalau sort, rank yg sekarang minus sebelumnya pasti = 1
                 2. rank terus di include selama counter card <5 (kan 5 kartu player).
                 ========================================================*/
                numOfCurrentCard = 2  // 2 kartu sdh sama
                var tempCurrCounter = counter
                while (tempCurrCounter + 1 < sortedRank.size && sortedRank[tempCurrCounter + 1] - sortedRank[tempCurrCounter] == 1) {
                    tempCurrCounter++
                    numOfCurrentCard++
                }
                if (numOfCurrentCard == 5) return true
            }
            counter++
        }
    }
    return false
}

/*==================================================
Flush 5 kartu dari total 7 kartu (player cards + board's card)
===================================================*/
fun isFlush(allCards: List<String>): Boolean {
    val mapSuitDanFrekuensinya = mutableMapOf<Char, Int>()
    for (card in allCards) {
        // note 270324 : Bukan card[1] --> issue misalkan 10H
        val tempSuit = card[card.length - 1] // index/char trakhir (suit)
        if (mapSuitDanFrekuensinya.containsKey(tempSuit)) {
            mapSuitDanFrekuensinya.put(tempSuit, mapSuitDanFrekuensinya.get(tempSuit)!! + 1)
        } else mapSuitDanFrekuensinya.put(tempSuit, 1)
    }
    println(mapSuitDanFrekuensinya)
    for (keyValue in mapSuitDanFrekuensinya) {
        if (keyValue.value > 4) return true // cuma btuh 5 kartu untuk flush
    }
    return false
}

//27/03/2024 straight ini dipisahin (untuk permudah case royal flush)
// karena Royal flush = straight (starts 10) && flush
fun isStraight10Starts(allCards: List<String>): Boolean {

    val rankList: MutableList<Int> = mutableListOf()
    for (card in allCards) {
        val currRank = stringRankToIntRank(card)
        rankList.add(currRank)
    }
    val sortedRank = rankList.sorted()
    return (sortedRank.contains(10) && sortedRank.contains(11) && sortedRank.contains(12) && sortedRank.contains(13) && sortedRank.contains(
        14
    ))
}

fun has1Pair(allCards: List<String>): Boolean {
    val mapRankFreq = mutableMapOf<Int, Int>()
    for (card in allCards) {
        val value = stringRankToIntRank(card)
        // kalau keynya sdh ada lgsg sj return (psti ada kembar)
        if (mapRankFreq.containsKey(value)) return true //jumper langsung out
        else mapRankFreq.put(value, 0)
    }
    return false
}

fun has2Pair(allCards: List<String>): Boolean {
    val mapRankFreq = mutableMapOf<Int, Int>() // rank & frekuensinya
    val setOfPair = mutableSetOf<Int>() // untuk jumper di-loop
    for (card in allCards) {
        val currRank = stringRankToIntRank(card) //  ranknya-nya
        if (mapRankFreq.containsKey(currRank)) {
            mapRankFreq.put(currRank, mapRankFreq.get(currRank)!! + 1)
            setOfPair.add(currRank)
            // [Jumper disini]. Kalau panjang
            // set pair sudah >1 lgsung kluar dri loop
            if (setOfPair.size > 1) return true
        } else mapRankFreq.put(currRank, 1)
    }
    return false
}

fun is4Kinds(allCards: List<String>): Boolean {
    val mapRankFreq = mutableMapOf<Int, Int>()
    for (card in allCards) {
        val rank = stringRankToIntRank(card)
        if (mapRankFreq.containsKey(rank)) {
            val theRankFreq = mapRankFreq.get(rank)
            mapRankFreq.put(rank, theRankFreq!! + 1)

            if (theRankFreq + 1 > 3) return true //jumper lgsung keluar stelah freq. 4
        } else mapRankFreq.put(rank, 1)
    }
    return false
}

fun isStraigtFlush(allCards: List<String>): Boolean {
    return isStraightNon10Starts(allCards) && isFlush(allCards)
}

fun isRoyalFlush(allCards: List<String>): Boolean {
    return isStraight10Starts(allCards) && isFlush(allCards)
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
    val myCards = mutableListOf("8D", "QH")
    val cardsOnTheBoard = mutableListOf("7H", "6S", "10D", "4S", "8C")
    val allCards = myCards + cardsOnTheBoard
    println(has1Pair(allCards))
    println(has2Pair(allCards))
    println(isFlush(allCards))
    println(allCards)
    println(isStraightNon10Starts(allCards))
    println(isStraight10Starts(allCards))
    println("is4Kinds = " + is4Kinds(allCards))
    println(isHighCard(allCards))
}
fun stringRankToIntRank(card: String): Int {
    val rank = card.substring(0, card.length - 1) // diindex 0, sebanyak 1 kartu
    return when (rank) {
        "J" -> 11
        "Q" -> 12
        "K" -> 13
        "A" -> 14
        else -> rank.toIntOrNull() ?: 0
    }
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


