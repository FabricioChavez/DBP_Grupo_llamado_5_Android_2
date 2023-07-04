import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.prueba2.R
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class MangaDisplay : AppCompatActivity() {
    private lateinit var textField_message: EditText
    private lateinit var button_send_post: Button
    private lateinit var button_send_get: Button
    private lateinit var textView_response: TextView
    private val url = "http://127.0.0.1:5000/autor" // URL de la API
    private val POST = "POST"
    private val GET = "GET"
    private val PUT = "PUT"
    private val DELETE = "DELETE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga_display)

        textField_message = findViewById(R.id.txtField_message)
        button_send_post = findViewById(R.id.button_send_post)
        button_send_get = findViewById(R.id.button_send_get)
        textView_response = findViewById(R.id.textView_response)

        button_send_post.setOnClickListener {
            val text = textField_message.text.toString()
            if (text.isEmpty()) {
                textField_message.error = "Este campo no puede estar vacío para una solicitud POST"
            } else {
                sendRequest(POST, "getname", "name", text)
            }
        }

        button_send_get.setOnClickListener {
            sendRequest(GET, "autor", null, null)
        }
    }

    private fun sendRequest(type: String, method: String, paramname: String?, param: String?) {
        val fullURL = url + if (param == null) "" else "/$param"
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        val request = when (type) {
            POST -> {
                val jsonBody = JSONObject().apply {
                    put(paramname!!, param!!)
                }
                val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), jsonBody.toString())
                Request.Builder()
                    .url(fullURL)
                    .post(requestBody)
                    .build()
            }
            GET, PUT, DELETE -> {
                Request.Builder()
                    .url(fullURL)
                    .method(type, null)
                    .build()
            }
            else -> throw IllegalArgumentException("Tipo de solicitud no válido")
        }

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()

                runOnUiThread {
                    if (responseData != null) {
                        Log.d("API Response", responseData)
                        textView_response.text = responseData
                    }
                }
            }
        })
    }
}
