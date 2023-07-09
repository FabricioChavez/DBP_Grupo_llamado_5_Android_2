package com.example.prueba2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.widget.EditText
import android.widget.ImageButton
import okhttp3.*
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class Tipulalogin : AppCompatActivity() {

    private lateinit var moveButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var imageButton: ImageButton
    private val url = "http://10.0.2.2:5000/login" // URL de la API

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipulalogin)

        moveButton = findViewById(R.id.ingresar)
        emailEditText = findViewById(R.id.editTextTextEmailAddress)
        passwordEditText = findViewById(R.id.editTextTextPassword)
        imageButton = findViewById(R.id.imageButton)

        // Cargar imagen utilizando Picasso
        Picasso.get().load("https://static.wikia.nocookie.net/bocchi-the-rock/images/b/b5/Hitori_Gotoh_Dise%C3%B1o.png/revision/latest/scale-to-width-down/204?cb=20230119235405&path-prefix=es")
            .into(imageButton)

        // Acción al hacer clic en el ImageButton
        imageButton.setOnClickListener{
            val intent = Intent(this@Tipulalogin , MangasG::class.java)
            startActivity(intent)
        }

        moveButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            val client = OkHttpClient()

            val formBody = FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build()

            val request = Request.Builder()
                .url(url)
                .post(formBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body?.string()

                    runOnUiThread {
                        if (responseData == "usuario_encontrado") {
                            val intent = Intent(this@Tipulalogin, MangasG::class.java)
                            startActivity(intent)
                        } else {
                            println("Error: Usuario no encontrado")
                        }
                    }
                }
            })
        }
    }
}
