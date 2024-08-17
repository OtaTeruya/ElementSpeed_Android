package com.complete.elementspeed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class GameView: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_game)

        supportFragmentManager.beginTransaction()
            .replace(R.id.ly2p, Player2Fragment())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.ly1p, Player1Fragment())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.ly_daihuda, DaihudaFragment())
            .commit()
    }
}