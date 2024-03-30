/*==========================================
Author : Fevly Pallar
contact : fevly.pallar@gmail.com
=========================================*/
package com.fevly.kasuaripoker.fragments



import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.fevly.kasuaripoker.R

import com.fevly.kasuaripoker.storage.Permission
import com.fevly.kasuaripoker.storage.StorageUtil
import com.fevly.kasuariprogroom.transmission.KasuariNetworkChannelManager
import com.google.android.material.tabs.TabLayout


class BoardFragment : Fragment() {
    /*  lateinit var tabLayout: TabLayout
      lateinit var viewPager: ViewPager
      var currentPiece = ""
      var currentRow = -1
      var currentCol = -1

      var colorToMoveFlag=0; // 0 -> putih (white), 1 -> hitam (bleeki..)

      lateinit var board: Array<Array<String>>

      lateinit var chessboardLayout: GridLayout

      lateinit var trackedClickedPiece: MutableSet<Pair<Int, Int>>
      lateinit var imageViewArray: Array<Array<ImageView>>

      lateinit var pm: PieceMoves

      lateinit var latestBoard: Array<Array<String>>
      lateinit var snapshotMoves: MutableList<Array<Array<String>>>

      var labelHuruf = arrayOf("a", "b", "c", "d", "e", "f", "g", "h")
      var labelAngka = arrayOf("1", "2", "3", "4", "5", "6", "7", "8")
  */

    private lateinit var editText: EditText
    private lateinit var listView: ListView
    private lateinit var connect: Button
    lateinit var chessboardLayout: GridLayout
    lateinit var permission: Permission
    lateinit var storageUtil: StorageUtil
    lateinit var kasuariNetworkChannelManager: KasuariNetworkChannelManager

    private lateinit var adapter: ArrayAdapter<String>
    val arrayOfGifts =
        intArrayOf(R.drawable.minuman, R.drawable.minuman2, R.drawable.icecream, R.drawable.pizza)
    val arrayOfTempUser =
        intArrayOf(R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4)
    val arrayOfTempCard =
        intArrayOf(R.drawable._1h, R.drawable._2c, R.drawable._3c, R.drawable._4c, R.drawable._5c)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.board_fragment, container, false)

        kasuariNetworkChannelManager = KasuariNetworkChannelManager(requireContext())
        chessboardLayout = view.findViewById<GridLayout>(R.id.chessboard)
        connect = view.findViewById<Button>(R.id.connect)
//        chessboardLayout.setBackgroundColor(Color.RED)
        drawLayoutAndBoard()

        connect.setOnClickListener(
            View.OnClickListener {
                kasuariNetworkChannelManager.exposeServicePleaseDoItNowKasuari(
                    "sampleservice",
                    9999
                )
            }
        )



        return view
    }

    fun drawLayoutAndBoard() {

        chessboardLayout.post {
            val cellWidth = chessboardLayout.width / 9

            /*=================================================================
            Untuk cellHeight mesti dibagi dari lebar (dan bukan tinggi) agar seluruh
            cell punya ukuran yg sama
             ===================================================================*/
            val cellHeight = chessboardLayout.width / 9
            chessboardLayout.removeAllViews()// reset viewnya

            for (i in 0 until 4) {
                for (j in 0 until 9) {


                    if ((i == 0 && j == 2) || (i == 0 && j == 4) || (i == 0 && j == 6)) {

                        val parentLayout = FrameLayout(requireContext()).apply {
                            layoutParams = GridLayout.LayoutParams().apply {
                                width = cellWidth
                                height = cellHeight - cellHeight/8
                            }
                        }

                        /*====================================================
                        Note: 26/03/2024
                        Foto player harus ambil seluruh porsi (ruang) dari grid cell
                        * ======================================================*/

                        val fotoUser = ImageView(requireContext()).apply {
                            setImageResource(arrayOfTempUser[(0..3).random()])
                            layoutParams = FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT,
                                FrameLayout.LayoutParams.MATCH_PARENT
                            )
                        }/*====================================================
                            Note: 26/03/2024
                           sedangkan Gambar pernak-pernik game(botol,pizza,dll) punya size yg minor
                         relative ke Foto player
                    * ======================================================*/
                        val fotoPernakPernik = ImageView(requireContext()).apply {
                            setImageResource(arrayOfGifts[(0..2).random()])
                            val imageWidth = cellWidth / 2 + cellWidth / 4
                            val leftMargin = (cellWidth) / 4

                            // Create FrameLayout.LayoutParams with initial values
                            var params = FrameLayout.LayoutParams(
                                imageWidth,
                                cellWidth / 2 - cellWidth / 16
                            )
                            // Modify the params
                            params.gravity = Gravity.BOTTOM or Gravity.END  // sudut bawah-kanan
                            params.leftMargin = leftMargin

                            layoutParams = params
                        }

                        parentLayout.addView(fotoUser)
                        parentLayout.addView(fotoPernakPernik)

                        chessboardLayout.addView(parentLayout)
                    }
                    // ini yg jadi component disebelah user (kartu-kartunya)
                    else if ((i == 0 && j == 2 + 1) || (i == 0 && j == 4 + 1) || (i == 0 && j == 6 + 1)) {

                        val parentLayout = FrameLayout(requireContext()).apply {
                            layoutParams = GridLayout.LayoutParams().apply {
                                width = cellWidth
                                    height =cellHeight - cellHeight/8
                            }
                        }

                        val firstCard = ImageView(requireContext()).apply {
                            setImageResource(arrayOfTempCard[(0..3).random()])
                            layoutParams = FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT,
                                FrameLayout.LayoutParams.MATCH_PARENT
                            )
                            rotation=-20f
                        }
                        val secondCard = ImageView(requireContext()).apply {
                            setImageResource(arrayOfTempCard[(0..2).random()])
                            val imageWidth = cellWidth / 2 + cellWidth / 4
                            val leftMargin = (cellWidth) / 4

                            // Create FrameLayout.LayoutParams with initial values
                            var params = FrameLayout.LayoutParams(
                                imageWidth,
                                cellHeight

                            )
                            // Modify the params
                            params.gravity = Gravity.BOTTOM or Gravity.END  // sudut bawah-kanan
                            params.leftMargin = leftMargin

                            layoutParams = params
                        }
                        parentLayout.addView(firstCard)
                        parentLayout.addView(secondCard)

                        chessboardLayout.addView(parentLayout)
                    }



                    else if ((i == 3 && j == 2) || (i == 3 && j == 4) || (i == 3 && j == 6)) {
                        val parentLayout = FrameLayout(requireContext()).apply {
                            layoutParams = GridLayout.LayoutParams().apply {
                                width = cellWidth
                                height =cellHeight - cellHeight/8
//                                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 0.5f) // ini gak ngefek
                            }
                        }

                        /*====================================================
                        Note: 26/03/2024
                        Foto player harus ambil seluruh porsi (ruang) dari grid cell
                        * ======================================================*/

                        val fotoUser = ImageView(requireContext()).apply {
                            setImageResource(arrayOfTempUser[(0..3).random()])
                            layoutParams = FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT,
                                FrameLayout.LayoutParams.MATCH_PARENT
                            )
                        }

                        /*====================================================
                            Note: 26/03/2024
                           sedangkan Gambar pernak-pernik game(botol,pizza,dll) punya size yg minor
                         relative ke Foto player
                    * ======================================================*/
                        val fotoPernakPernik = ImageView(requireContext()).apply {
                            setImageResource(arrayOfGifts[(0..2).random()])
                            val imageWidth = cellWidth / 2 + cellWidth / 4  // Width of the ImageView
                            val leftMargin = (cellWidth) / 4  // Calculate left margin to center the ImageView

                            var params = FrameLayout.LayoutParams(
                                imageWidth,
                                cellWidth / 2 - cellWidth / 16
                            )

                            params.gravity = Gravity.BOTTOM or Gravity.END  // Set gravity to bottom-right corner
                            params.leftMargin = leftMargin

                            layoutParams = params
                        }

                        parentLayout.addView(fotoUser)
                        parentLayout.addView(fotoPernakPernik)

                        chessboardLayout.addView(parentLayout)
                    }

                    // ini yg jadi component disebelah user (kartu-kartunya)
                    else if ((i == 3 && j == 2 + 1) || (i == 3 && j == 4 + 1) || (i == 3 && j == 6 + 1)) {
                        val parentLayout = FrameLayout(requireContext()).apply {
                            layoutParams = GridLayout.LayoutParams().apply {
                                width = cellWidth
                                height =cellHeight - cellHeight/8
                            }
                        }
                        // kartu kiri
                        val firstCard = ImageView(requireContext()).apply {
                            setImageResource(arrayOfTempCard[(0..3).random()])
                            layoutParams = FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT,
                                FrameLayout.LayoutParams.MATCH_PARENT
                            )
                            rotation=-20f
                        }
                        // kartu kanan
                        val secondCard = ImageView(requireContext()).apply {
                            setImageResource(arrayOfTempCard[(0..2).random()])
                            val imageWidth = cellWidth / 2 + cellWidth / 4
                            val leftMargin = (cellWidth) / 4

                            // ini jadi wrapper untuk 2 kartu . Dimensi mesti match ke cell gridlayout
                            var params = FrameLayout.LayoutParams(
                                imageWidth,
                                cellHeight
                            )
                            params.gravity = Gravity.BOTTOM or Gravity.END  // sudut bawah-kanan
                            params.leftMargin = leftMargin

                            layoutParams = params
                        }
                        parentLayout.addView(firstCard)
                        parentLayout.addView(secondCard)

                        chessboardLayout.addView(parentLayout)
                    }

                    else {

                        val imageView = ImageView(requireContext()).apply {
//                            setImageResource(R.drawable.notebook)
                            val params = GridLayout.LayoutParams().apply {
                                width = cellWidth
                                height = cellHeight
                                rowSpec = GridLayout.spec(i)
                                columnSpec = GridLayout.spec(j)
                            }
                            layoutParams = params
                        }
                        chessboardLayout.addView(imageView) // Add each ImageView to the GridLayout
                    }


                }
            }

        }
    }


}