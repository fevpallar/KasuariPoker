package kususkasuari
/*==========================================
Author : Fevly Pallar
contact : fevly.pallar@gmail.com
=========================================*/
import com.fevly.kasuaripoker.depend.KasuariClientEngine
import com.fevly.kasuaripoker.game.GameEngine
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

fun main() {

    Thread(Runnable {
        /*      ============================================================
              300324 ini mesti dithread sendiri,
              karena thread disini  bound ke thread internal server tdk
              langsung execute.
              =========================================================*/
        var gameEngine = GameEngine()
        gameEngine.start(9999)
    }).start()
    /*==================================================
       ISSUE

       buffer diinternal server blum bisa flush dgn proper.
       Jadi ditengah jalan, "bisa saja" data broken.
       tapi kemungkinannya small

    ********************************
    Sample proper response :

        server:  client 1711810812502 : pullboard
        board card :  JS 3D AC 4D 6H
         server:  client 1711810812502 : req1
        Current player card: AS KS
     ********************************
       Sample broken response :

      ver :  9C 4H 6C 6D 10H
       H 2S
       ver :  AS 5D JS 9D 7D
        server :  3C QS QD JH 2S
       S 2D 9H JC
        9C 4C 5S AC KD
       om server :  5S KS 5H AH 4C
       QD 2D 2C 2H 9C
        3C 5H 9C KC 6C
       *****************************


      ================================================= */

    var player = Player()

    val kasuariClientEngine = KasuariClientEngine(9999)
    var connClient = kasuariClientEngine.getConn()

    /*=====================================================
    note 30032023
     thread-flag cukup sulit (kalau nanti playernya sdh banyak).
     jadi, instead of boolean flagging,
     dari sini hint saja ke internal serving pakai mark 'pull'
     untuk proses generate datanya (1 player saja)
    ======================================================== */
    player.sendMessage(connClient, "pullboard")
    player.getMessage(connClient)
    player.sendMessage(connClient, "req1")
    player.getMessage(connClient)


}

class Player {
    var cliendIdentificator = System.currentTimeMillis()
    fun getMessage(sock: Socket) {
        val inputStream = sock.getInputStream()
        val inputStreamReader = InputStreamReader(inputStream, "ASCII")
        val bufferedReader = BufferedReader(inputStreamReader)
        val extracted = bufferedReader.readLine()
        println(" $extracted")
//        bufferedReader.close()
        // sock.close() - Don't close the socket here if you want to send a message back
    }

    fun sendMessage(sock: Socket, message: String) {
        val outputStream = sock.getOutputStream()
        val out = PrintWriter(outputStream, true)
        out.println("client $cliendIdentificator : " + message)
        fun sendMessage(sock: Socket, message: String) {
            val outputStream = sock.getOutputStream()
            val out = PrintWriter(outputStream, true)
            out.println("client $cliendIdentificator : $message")
        }
    }
}

