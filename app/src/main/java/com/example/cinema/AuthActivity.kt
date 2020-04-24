package com.example.cinema

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cinema.api.model.Login
import com.example.cinema.api.model.RequestSessionId
import com.example.cinema.api.model.SessionId
import com.example.cinema.api.model.Token
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class AuthActivity : AppCompatActivity(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        pref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        getToken()
        login_button.setOnClickListener {
            login()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
    private fun login() {
        launch {
            var token = pref.getString(TOKEN_KEY, "error")
            if (token != "error") {
                var login: Login =
                    Login(login_view.text.toString(), password_view.text.toString(), token)
                val response = RetrofitService.getMovieApi().login(login)
                if (response.isSuccessful) {
                    var answer: Token? = response.body()
                    if (answer?.success == true) {
                        val intent = Intent(this@AuthActivity, MainActivity::class.java)
                        Log.d("token response", "success")
                        getSessionId()
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@AuthActivity,
                            "ERROR WITH CONNECTION",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        }
    }

    fun getToken() {  //get token from server
        launch {
            val response = RetrofitService.getMovieApi().getToken()
            if (response.isSuccessful) {
                var answer: Token? = response.body()
                val editor = pref.edit()
                editor.putString(
                    TOKEN_KEY,
                    answer?.request_token
                )        // кладем полученный токен в shared preferences
                editor.apply()
                Log.d("get token", pref.getString(TOKEN_KEY, answer?.request_token))
            } else {
                Toast.makeText(this@AuthActivity, "Api key is not correct ", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    fun getSessionId() {
        var token = pref.getString(TOKEN_KEY, "error")
        var requestSessionId: RequestSessionId =
            RequestSessionId(token)
        Log.d("get token on session id", pref.getString(TOKEN_KEY, ""))
        if (token != "error") {
            var call: Call<SessionId> = RetrofitService.getMovieApi().getSessionId(requestSessionId)
            call.enqueue(object : Callback<SessionId> {
                override fun onFailure(call: Call<SessionId>, t: Throwable) {
                    Toast.makeText(this@AuthActivity, "ERROR WITH CONNECTION", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<SessionId>, response: Response<SessionId>) {
                    if (response.body()?.success == true) {
                        Log.d("pusk2", response.body()?.sessionId.toString())
                        val edt = pref.edit()
                        edt.putString("sessionID", response.body()?.sessionId)
                        edt.apply()
                        Log.d("second check", pref.getString("sessionID", "empty 2").toString())
                    } else {
                        Toast.makeText(
                            this@AuthActivity,
                            "Login or password is not correct",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("session id", response.body()?.sessionId)
                    }
                }
            })
        }
    }


}

