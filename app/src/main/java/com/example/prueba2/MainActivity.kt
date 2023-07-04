package com.example.prueba2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moveButton = findViewById<Button>(R.id.move)
        moveButton.setOnClickListener {
            val intent = Intent(this@MainActivity, Tipulalogin::class.java)
            startActivity(intent)
        }
    }
}
