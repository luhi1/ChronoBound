package com.example.userauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

//code basically taken from https://www.youtube.com/watch?v=8I5gCLaS25w, some if was my own work tho.
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val tv_register = findViewById<TextView>(R.id.tv_login)
        val btn_login = findViewById<Button>(R.id.btn_register)
        val et_login_email = findViewById<EditText>(R.id.til_register_email)
        val et_login_password = findViewById<EditText>(R.id.til_register_password)

        tv_register.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        btn_login.setOnClickListener{
            when{
                TextUtils.isEmpty(et_login_email.text.toString().trim { it <= ' '}) ->{
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(et_login_password.text.toString().trim { it <= ' '}) ->{
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else ->{
                    val email: String = et_login_email.text.toString().trim { it <= ' '}
                    val password: String = et_login_password.text.toString().trim { it <= ' '}
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->

                        if (task.isSuccessful) {

                            Toast.makeText(
                                this@LoginActivity,
                                "You are logged in successfully.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent: Intent
                            if (FirebaseAuth.getInstance().currentUser!!.uid == "YWvInin6lpZrhQ5DS9sWAOQQ8ty1"){
                                intent =
                                    Intent(this@LoginActivity, adminCalendarActivity::class.java)
                            } else {
                                intent =
                                    Intent(this@LoginActivity, CalendarActivity::class.java)
                            }
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                            intent.putExtra("email_id", email)
                            startActivity(intent)
                            finish()
                        } else {

                            Toast.makeText(
                                this@LoginActivity,
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