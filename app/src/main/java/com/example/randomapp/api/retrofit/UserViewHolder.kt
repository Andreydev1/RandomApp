package com.example.randomapp.api.retrofit

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.randomapp.R
import com.example.randomapp.api.model.UserModel

class UserViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    private val userSmallPicture: ImageView = itemView.findViewById(R.id.image_picture_card)
    private val userName: TextView = itemView.findViewById(R.id.user_name_card)
    private val userSurName: TextView = itemView.findViewById(R.id.user_surname_card)
    private val userAge:TextView = itemView.findViewById(R.id.user_age)

    fun bind(model: UserModel){
        userAge.text = model.dob.age.toString() + " y.o."
        userName.text = model.name.first
        userSurName.text = model.name.last
        Glide.with(itemView)
            .load(model.picture.thumbnail)
            .transform(CircleCrop())
            .into(userSmallPicture)
    }
}