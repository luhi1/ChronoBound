package com.example.userauth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

//code basically taken from https://www.youtube.com/watch?v=8I5gCLaS25w, a lot of it was my own changes too.
//basically, it creates an account if you do not have the same credentials of a user in the database. Otherwise
//it outputs a host of errors (toasts) based on the different conditions met.
//Also, if you click on the login button, it takes you the login screen.
class RegisterActivity : AppCompatActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val btn_register = findViewById<Button>(R.id.btn_register)
        val et_register_email = findViewById<EditText>(R.id.til_register_email)
        val et_register_password = findViewById<EditText>(R.id.til_register_password)
        val tv_login = findViewById<TextView>(R.id.tv_login)

        tv_login.setOnClickListener{
            onBackPressed()
        }

        btn_register.setOnClickListener{
            when{
                TextUtils.isEmpty(et_register_email.text.toString().trim { it <= ' '}) ->{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_register_password.text.toString().trim { it <= ' '}) ->{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else ->{
                    val email: String = et_register_email.text.toString().trim { it <= ' '}
                    val password: String = et_register_password.text.toString().trim { it <= ' '}
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            Toast.makeText(
                                this@RegisterActivity,
                                "You are registered successfully.",
                                Toast.LENGTH_SHORT
                            ).show()
                                                        val intent =
                                Intent(this@RegisterActivity, CalendarActivity::class.java)

                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.uid).child("Period 1").setValue("")
                            FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.uid).child("Period 2").setValue("")
                            FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.uid).child("Period 3").setValue("")
                            FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.uid).child("Period 4").setValue("")
                            FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.uid).child("Period 5").setValue("")
                            FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.uid).child("Period 6").setValue("")
                            FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.uid).child("Period 7").setValue("")
                            FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.uid).child("Period 8").setValue("")
                            FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.uid).child("Monday").setValue("")
                            FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.uid).child("Tuesday").setValue("")
                            FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.uid).child("Wednesday").setValue("")
                            FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.uid).child("Thursday").setValue("")
                            FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.uid).child("Friday").setValue("")
                            FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.uid).child("Saturday").setValue("")
                            FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.uid).child("Sunday").setValue("")

                            intent.putExtra("user_id", firebaseUser.uid)
                            intent.putExtra("email_id", email)
                            startActivity(intent)
                            finish()
                        } else {

                            Toast.makeText(
                                this@RegisterActivity,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }

        }
        }
    }
}