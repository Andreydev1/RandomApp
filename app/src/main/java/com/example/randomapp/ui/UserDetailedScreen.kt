package com.example.randomapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.randomapp.R
import com.example.randomapp.api.model.UserModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserDetailedScreen : AppCompatActivity() {
    private lateinit var userGender: TextView
    private lateinit var userAddress: TextView
    private lateinit var userId: TextView
    private lateinit var userCoordinates: TextView
    private lateinit var userPicture: ImageView
    private lateinit var arrowBackButton: ImageView
    private lateinit var userName: TextView
    private lateinit var userEmail: TextView
    private lateinit var userPhone: TextView
    private lateinit var userPassword: TextView
    private lateinit var userBirthday: TextView
    private lateinit var currentUser: UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_detailed_info)
        findViewByIds()
        setOnClickListeners()
        loaData()
    }

    private fun setOnClickListeners() {
        arrowBackButton.setOnClickListener {
            finish()
        }
        userEmail.setOnClickListener {
            intentToEmail()
        }
        userPhone.setOnClickListener {
            intentToCall()
        }
        userCoordinates.setOnClickListener {
            intentToMap()
        }
    }


    private fun findViewByIds() {
        userGender = findViewById(R.id.user_gender_data)
        userId = findViewById(R.id.user_id_data)
        userCoordinates = findViewById(R.id.user_coordinates_data)
        userName = findViewById(R.id.name)
        arrowBackButton = findViewById(R.id.arrow_back_button)
        userPicture = findViewById(R.id.user_picture)
        userAddress = findViewById(R.id.user_address_data)
        userEmail = findViewById(R.id.user_email_data)
        userPhone = findViewById(R.id.user_phone_number_data)
        userPassword = findViewById(R.id.user_password_data)
        userBirthday = findViewById(R.id.user_birthday_data)
        currentUser = intent.getSerializableExtra("user") as UserModel
    }

    private fun loaData() {
        Glide.with(applicationContext)
            .load(currentUser.picture.large)
            .transform(CircleCrop())
            .into(userPicture)

        userAddress.text = (currentUser.location.country + " " + currentUser.location.city)
        userEmail.text = currentUser.email
        userPhone.text = currentUser.phone
        userPassword.text = currentUser.login.password
        userBirthday.text = formatDobDate(currentUser.dob.date)
        userName.text =
            currentUser.name.title + "." + currentUser.name.first + " " + currentUser.name.last
        userId.text = currentUser.id.value
        userCoordinates.text =
            currentUser.location.coordinates.latitude + " " + currentUser.location.coordinates.longitude
        userGender.text = currentUser.gender

    }

    private fun formatDobDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date: Date = inputFormat.parse(dateString) ?: return ""

        return outputFormat.format(date)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun intentToCall() {
        val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${userPhone.text}")
        }
        startActivity(phoneIntent)
    }

    private fun intentToEmail() {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${userEmail.text}")
            putExtra(Intent.EXTRA_SUBJECT, "Subject")
            putExtra(Intent.EXTRA_TEXT, "Hello it's")
        }
        startActivity(emailIntent)
    }

    private fun intentToMap() {
        val mapIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("geo:0,0?q=${userAddress.text}")
        }
            startActivity(mapIntent)
        }
    }

