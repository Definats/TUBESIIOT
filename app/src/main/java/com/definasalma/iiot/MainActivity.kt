package com.definasalma.iiot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FirebaseScreen()
        }
    }
}

@Composable
fun FirebaseScreen() {

    var suhu by remember { mutableStateOf("0") }
    var kelembaban by remember { mutableStateOf("0") }

    LaunchedEffect(Unit) {

        val database = FirebaseDatabase.getInstance(
            "https://smarts-daycare-default-rtdb.asia-southeast1.firebasedatabase.app/"
        )

        val ref = database.getReference("sensor")

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                suhu = snapshot.child("temperature").getValue(Double::class.java)?.toString() ?: "0"

                kelembaban = snapshot.child("humidity").getValue(Double::class.java)?.toString() ?: "0"
            }

            override fun onCancelled(error: DatabaseError) {
                println("Firebase Error: ${error.message}")
            }
        })
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Smart Daycare",
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "🌡️ Suhu : $suhu °C",
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "💧 Kelembaban : $kelembaban %",
                fontSize = 22.sp
            )
        }
    }
}