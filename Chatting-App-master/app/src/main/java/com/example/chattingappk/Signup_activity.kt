package com.example.chattingappk

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Signup_activity : AppCompatActivity() {

    private lateinit var email : EditText
    private lateinit var name : EditText
    private lateinit var password : EditText
    private lateinit var register : Button
    private lateinit var have_account : TextView

    private lateinit var auth: FirebaseAuth
    private  lateinit var  dbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()

        email= findViewById(R.id.register_email_id)
        name= findViewById(R.id.register_name_id)
        password= findViewById(R.id.register_pasword_id)
        register= findViewById(R.id.register_id)
        have_account= findViewById(R.id.have_account_id)
    }

    fun have_account(view: View) {
        val intent = Intent(this , LoginActivity::class.java)
        startActivity(intent)
    }

    fun btn_register(view: View) {
        val rname =name.text.toString()
        val remail =email.text.toString()
        val rpassword =password.text.toString()

        register(rname,remail, rpassword)
    }

    private fun register(rname:String, remail:String, rpassword: String )
    {
        auth.createUserWithEmailAndPassword(remail, rpassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    add_sers_in_database(rname, remail, auth.currentUser?.uid!!)

                    val intent = Intent(this@Signup_activity, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {

                    Toast.makeText(this@Signup_activity, "Error try again!" , Toast.LENGTH_LONG).show()


                }
            }

    }

    private fun add_sers_in_database(rname: String, remail: String, uid: String) {
        dbref=FirebaseDatabase.getInstance().getReference()
        dbref.child("user").child(uid).setValue(Users(rname, remail,uid))

    }
}