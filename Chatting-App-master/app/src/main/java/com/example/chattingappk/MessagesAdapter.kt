package com.example.chattingappk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.dynamic.IFragmentWrapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MessagesAdapter(val context:Context, val messageList:ArrayList<Messages>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){



    val ITEMReceive=1
    val ITEMSENT=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType ==1)
        {
            val view: View = LayoutInflater.from(context).inflate(R.layout.reciever, parent, false)
            return ReceiveViewHolder(view)
        }else
        {
            val view: View = LayoutInflater.from(context).inflate(R.layout.sender, parent, false)
            return SentViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessages = messageList[position]
        if(holder.javaClass == SentViewHolder::class.java)
        {

            val viewHolder = holder as SentViewHolder
            holder.txtSentMessage.text=currentMessages.messages
        }
        else{
            val viewHolder = holder as ReceiveViewHolder
            holder.txtReceiveMessage.text =currentMessages.messages
        }
    }

    override fun getItemViewType(position: Int): Int {
        var currentMessage =  messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.sender))
        {
            return ITEMSENT
        }else{
            return ITEMReceive
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }


    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtSentMessage = itemView.findViewById<TextView>(R.id.sender_text_id)
    }

    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtReceiveMessage = itemView.findViewById<TextView>(R.id.receiver_text_id)

    }


}