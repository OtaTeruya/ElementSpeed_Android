package com.complete.elementspeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment

class Player2Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Player2FragmentScreen()
                }
            }
        }
    }

    @Composable
    fun Player2FragmentScreen() {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Card(Element(1,1,
                    "H", "水素", "Hydrogen", 1.0))

                Card(Element(1,1,
                    "H", "水素", "Hydrogen", 1.0))

                Card(Element(1,1,
                    "H", "水素", "Hydrogen", 1.0))

                Card(Element(1,1,
                    "H", "水素", "Hydrogen", 1.0))
            }

            Text(
                text = "残り\n20枚",
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}