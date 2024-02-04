package com.example.randomapp.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.randomapp.R
import com.example.randomapp.api.retrofit.UserAdapter
import com.example.randomapp.api.model.UserModel
import com.example.randomapp.api.retrofit.RandomApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var getUserButton: Button
    private lateinit var userRv: RecyclerView
    private lateinit var clearButton: Button
    private val userAdapter = UserAdapter()
    private val translateBaseUrl = "https://randomuser.me/api/"
    private val apiKey = "GRMY-FDTG-ZU9W-HXSM"
    private val retrofit = Retrofit.Builder()
        .baseUrl(translateBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val userService = retrofit.create(RandomApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewByIds()
        setOnClickListeners()
        loadUsers()
        userRv.layoutManager = LinearLayoutManager(this)
        userRv.adapter = userAdapter
    }


    private fun setOnClickListeners() {
        getUserButton.setOnClickListener {
            findUsers()
        }

        userAdapter.onItemClick = {
                userModel -> openDetailedInfo(userModel)
        }
        clearButton.setOnClickListener {
            userAdapter.setUsers(null)
        }
    }

    private fun findViewByIds() {
        clearButton = findViewById(R.id.clear_button)
        getUserButton = findViewById(R.id.getUsers_btn)
        userRv = findViewById(R.id.userRv)
    }

    private fun openDetailedInfo(userModel: UserModel) {
        val openInfo = Intent(this, UserDetailedScreen :: class.java)
        openInfo.putExtra("user", userModel)
        startActivity(openInfo)
    }

    private fun findUsers() {
        val mainScope = MainScope()
        mainScope.launch {
            try {
                val call = withContext(Dispatchers.IO) {
                    userService.getUsers(apiKey, 10).execute()
                }

                if (call.isSuccessful) {
                    val users = call.body()?.results
                    if (!users.isNullOrEmpty()) {
                        userAdapter.setUsers(users)
                    }
                }
            } catch (t: Throwable) {
                Toast.makeText(this@MainActivity, "OnFailure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun saveUsersAfterRestart(users: List<UserModel>){
        val sharedPrefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        val gson = Gson()
        val usersToJson = gson.toJson(users)
        editor.putString("users", usersToJson)
        editor.apply()
    }
    private fun loadUsers(): List<UserModel> {
        val sharedPrefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val usersFromJson = sharedPrefs.getString("users", null)
        val gson = Gson()
        val type = object : TypeToken<List<UserModel>>() {}.type
        val loadedUsers = gson.fromJson<List<UserModel>>(usersFromJson, type) ?: emptyList()

        if (loadedUsers.isNotEmpty()) {
            userAdapter.setUsers(loadedUsers)
        }

        return loadedUsers
    }


    override fun onStop() {
        saveUsersAfterRestart(userAdapter.usersList)
        super.onStop()
    }

    override fun onDestroy() {
        saveUsersAfterRestart(userAdapter.usersList)
        super.onDestroy()
    }

    override fun onPause() {
        saveUsersAfterRestart(userAdapter.usersList)
        super.onPause()
    }
}
