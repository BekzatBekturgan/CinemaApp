package com.example.cinema.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cinema.R
import com.example.cinema.RetrofitService
import com.example.cinema.TOKEN_KEY
import com.example.cinema.api.model.AccountDetails
import com.example.cinema.api.model.MoviesData
import com.example.cinema.pref
import kotlinx.android.synthetic.main.fragment_profiles.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class ProfileFragment: Fragment(), CoroutineScope {
    private lateinit var rootView: View
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var sessionId: String? = null
    private var textViewName: TextView? = null
    private var textViewUsername: TextView? = null

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sessionId = pref.getString("sessionID", "empty")

        rootView = inflater.inflate(R.layout.fragment_profiles, container, false)
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayoutProfile)
        getAccountDetailsCoroutine()
        swipeRefreshLayout.setOnRefreshListener {
            getAccountDetailsCoroutine()
        }
        bindViews(rootView)
        return rootView
    }

    private fun bindViews(rootView: View) {
        textViewName = rootView.findViewById(R.id.textViewProfileName)
        textViewUsername = rootView.findViewById(R.id.textViewProfileEmail)

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
/*
    private fun getAccountDetails() {
        swipeRefreshLayout.isRefreshing = true
        RetrofitService.getMovieApi().getAccountDetails(sessionId)
            ?.enqueue(object : Callback<AccountDetails?> {
                override fun onFailure(call: Call<AccountDetails?>, t: Throwable) {
                    Log.e("Error", "Error")
                }
                override fun onResponse(
                    call: Call<AccountDetails?>,
                    response: Response<AccountDetails?>
                ) {
                    Log.d("Error", "Error on response")
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d("Check", responseBody?.name ?: "google")
                        if (responseBody != null) {
                            textViewName?.setText(responseBody.name)
                            Log.d("name", textViewName?.text.toString())
                            textViewUsername?.setText(responseBody.username)
                        }
                    }

                    else{
                        Log.d("Response fail", "response failed")
                    }
                    swipeRefreshLayout.isRefreshing = false
                }
            })
    }
*/
    private fun getAccountDetailsCoroutine() {
        launch {
            swipeRefreshLayout.isRefreshing = true
            val response = RetrofitService.getMovieApi().getAccountDetailsCoroutine(sessionId)
            if (response.isSuccessful) {
                val responseBody = response.body()
                textViewName?.setText(responseBody?.name)
                textViewUsername?.setText(responseBody?.username)
            } else {
                //Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
            }
            swipeRefreshLayout.isRefreshing = false
        }
    }
}