package com.example.prueba2
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.widget.ImageButton
import com.squareup.picasso.Picasso

class Tipulalogin : AppCompatActivity() {

    private lateinit var imageButton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipulalogin)

        imageButton = findViewById(R.id.imageButton)

        Picasso.get().load("https://static.wikia.nocookie.net/bocchi-the-rock/images/b/b5/Hitori_Gotoh_Dise%C3%B1o.png/revision/latest/scale-to-width-down/204?cb=20230119235405&path-prefix=es")
            .into(imageButton)

        imageButton.setOnClickListener{
            val intent = Intent(this@Tipulalogin , MangasG::class.java)
            startActivity(intent)

        }


        val moveButton = findViewById<Button>(R.id.ingresar)
        moveButton.setOnClickListener {
            val intent = Intent(this@Tipulalogin, prueba::class.java)
            startActivity(intent)
        }


    }
}

