package com.example.prueba2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit
import org.json.JSONObject
class prueba : AppCompatActivity() {

    private lateinit var textField_message: EditText
    private lateinit var button_send_get: Button
    private lateinit var textView_response: TextView
    private lateinit var ImageView :ImageView
    private val url = "http://10.0.2.2:5000/manga/"// URL de la API


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prueba)

        textField_message = findViewById(R.id.txtField_message)
        button_send_get = findViewById(R.id.button_send_get)
        textView_response = findViewById(R.id.textView_response)
        ImageView = findViewById(R.id.imageView)


        button_send_get.setOnClickListener {
            val mangaId = textField_message.text.toString()
            if (mangaId.isEmpty()) {
                textField_message.error = "Este campo no puede estar vacío para una solicitud GET"
            } else {
                sendGetRequestById(mangaId)
            }
        }
    }

    private fun sendGetRequestById(mangaId: String) {
        val fullURL = url + mangaId
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        val request = Request.Builder()
            .url(fullURL)
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()

                runOnUiThread {
                    if (responseData != null) {
                        val jsonObject = JSONObject(responseData)
                        val id = jsonObject.getString("id")
                        val nombre = jsonObject.getString("nombre")
                        val volumen = jsonObject.getString("edicion")
                        val genero = jsonObject.getString("genero")
                        val stock = jsonObject.getInt("cant_stock")
                        val precio = jsonObject.getDouble("precio")
                        val link = jsonObject.getString("link")
                        Picasso.get().load(link).into(ImageView)

                        val responseText  = "Nombre: $nombre \n Genero: $genero\n Volumen: $volumen \n Stock: $stock \n Precio : $precio"

                        textView_response.text=responseText

                    }
                }
            }
        })
    }

    }
