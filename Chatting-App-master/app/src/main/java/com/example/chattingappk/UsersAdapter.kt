package com.example.chattingappk

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class UsersAdapter(val context: Context, val usersList: ArrayList<Users>):
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {

        val view: View = LayoutInflater.from(context).inflate(R.layout.users_layout, parent, false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {

         val currentUsers= usersList[position]
        holder.name.text=currentUsers.name

        //to go to specify chat
            holder.itemView.setOnClickListener{
                val intent = Intent(context, Chat::class.java)
               intent.putExtra("name", currentUsers.name)
               intent.putExtra("uid", currentUsers.uid)


                context.startActivity(intent)
            }


    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name=itemView.findViewById<TextView>(R.id.name_id)
    }

}