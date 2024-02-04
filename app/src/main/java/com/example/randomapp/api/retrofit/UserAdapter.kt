package com.example.randomapp.api.retrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.randomapp.R
import com.example.randomapp.api.model.UserModel

class UserAdapter():RecyclerView.Adapter<UserViewHolder>() {
    var onItemClick:((UserModel)-> Unit)? = null

    fun setUsers(newUsers : List<UserModel>?){
        usersList.clear()
        if (!newUsers.isNullOrEmpty()){
            usersList.addAll(newUsers)
        }
        notifyDataSetChanged()
    }
    var usersList = mutableListOf<UserModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item_card, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int  = usersList.size


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(usersList[position])
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(usersList[position])
        }
    }

}