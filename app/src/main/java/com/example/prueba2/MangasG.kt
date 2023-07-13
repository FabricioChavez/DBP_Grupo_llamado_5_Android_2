package com.example.prueba2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text
import java.io.IOException
import java.util.concurrent.TimeUnit
class MangasG : AppCompatActivity() {

    private lateinit var mangas_ver_btn : Button
    private lateinit var manga_ver_btn : ImageButton
    private lateinit var manga_text : TextView
    private val url = "http://18.235.183.107:8000/manga"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mangas_g)

        manga_ver_btn = findViewById(R.id.manga_ver_btn)
        mangas_ver_btn = findViewById(R.id.mangas_ver_btn)
        manga_text = findViewById(R.id.manga_text)

        mangas_ver_btn.setOnClickListener {
            sendGetRquest()
        }

        manga_ver_btn.setOnClickListener{
            val intent = Intent(this@MangasG , prueba::class.java)
            startActivity(intent)
        }
    }

    private fun sendGetRquest() {
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        val request = Request.Builder()
            .url(url)
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
                        val jsonArray = JSONArray(responseData)

                        val mainLayout = findViewById<LinearLayout>(R.id.main_layout)

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val nombre = jsonObject.getString("nombre")
                            val imageUrl = jsonObject.getString("link")

                            val linearLayout = LinearLayout(this@MangasG)
                            linearLayout.orientation = LinearLayout.VERTICAL

                            val imageButton = ImageButton(this@MangasG)
                            Picasso.get().load(imageUrl).into(imageButton)
                            linearLayout.addView(imageButton)

                            val textView = TextView(this@MangasG)
                            textView.text = nombre
                            linearLayout.addView(textView)

                            mainLayout.addView(linearLayout)
                        }
                    }
                }
            }
        })
    }
}




