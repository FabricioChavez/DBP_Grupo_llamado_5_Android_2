package com.example.prueba2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.view.View
import okhttp3.*
import java.io.IOException
import android.widget.Toast

class Tipulalogin : AppCompatActivity() {

    private val url = "http://10.0.2.2:5000/login/" // URL de la API
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipulalogin)
    }

    fun onIngresarButtonClick(view: View) {
        val requestBody = FormBody.Builder()
            .add("email", "example@example.com") // Reemplaza con el correo electrónico correcto
            .add("password", "password123") // Reemplaza con la contraseña correcta
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Manejo de errores en caso de que la solicitud falle
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        // Manejo de errores en caso de que la respuesta no sea exitosa
                        throw IOException("Unexpected code $response")
                    }

                    // Obtener el cuerpo de la respuesta como cadena de texto
                    val responseBody = response.body?.string()

                    // Verificar si la respuesta contiene los datos del usuario o un error
                    if (responseBody?.contains("error") == true) {
                        // Mostrar mensaje de "Credenciales incorrectas"
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Redireccionar a MainActivity.kt
                        val intent = Intent(this@Tipulalogin, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        })
    }
}
