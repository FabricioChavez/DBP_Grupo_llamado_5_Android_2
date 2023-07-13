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
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONObject
import java.io.IOException

class Tipulalogin : AppCompatActivity() {

    private lateinit var moveButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var imageButton: ImageButton
    private val url = "http://18.235.183.107:8000/login" // URL de la API

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

            // Cambio a MediaType para JSON y formamos el cuerpo de la solicitud
            val JSON = "application/json; charset=utf-8".toMediaType()
            val body = RequestBody.create(JSON, "{\"email\":\"$email\",\"password\":\"$password\"}")

            val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body?.string()

                    if (responseData != null) {
                        val jsonResponse = JSONObject(responseData)

                        // Verificamos si la respuesta tiene la llave 'error'
                        if(jsonResponse.has("error")) {
                            val errorMsg = jsonResponse.getString("error")
                            runOnUiThread {
                                println("Error: $errorMsg")
                            }
                        } else {
                            // En caso de éxito, redirigimos a la siguiente activity
                            runOnUiThread {
                                val intent = Intent(this@Tipulalogin, prueba::class.java)
                                startActivity(intent)
                            }
                        }
                    }
                }
            })
        }
    }
}
