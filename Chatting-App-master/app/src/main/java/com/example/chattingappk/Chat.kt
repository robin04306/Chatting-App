package com.example.chattingappk

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Chat : AppCompatActivity() {

    private lateinit var mrecyclerview: RecyclerView
    private lateinit var messagebox: EditText
    private lateinit var sendmessage: ImageView
    private lateinit var messageList: ArrayList<Messages>
    private lateinit var messagesAdapter: MessagesAdapter

    private lateinit var dbRef: DatabaseReference

    var senderRoom:String?= null
    var receiverRoom:String?= null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        dbRef =FirebaseDatabase.getInstance().getReference()

        val name = intent.getStringExtra("name")
        val receiveUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom= receiveUid +senderUid
        receiverRoom= senderUid+receiveUid

        supportActionBar?.title= name



        mrecyclerview=findViewById(R.id.chat_recycler_view_id)
        messagebox=findViewById(R.id.messagebox_id)
        sendmessage=findViewById(R.id.send_message_id)
        messageList= ArrayList()
        messagesAdapter = MessagesAdapter(this, messageList)

        mrecyclerview.layoutManager=LinearLayoutManager(this)
        mrecyclerview.adapter= messagesAdapter

        dbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()
                    for (postsnapshot in snapshot.children)
                    {
                        val message = postsnapshot.getValue(Messages::class.java)
                        messageList.add(message!!)
                    }
                    messagesAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


        sendmessage.setOnClickListener{
        val message = messagebox.text.toString()
            val messageObject = Messages(message, senderUid)

            dbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    dbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messagebox.setText("")
        }


    }
}