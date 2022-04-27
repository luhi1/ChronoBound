package com.example.userauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class adminCalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //Inflates Layout
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_calendar)

        //Declares variables
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragmentContainerView)
        val exitButton = findViewById<ImageView>(R.id.exitButton)

        //Adds logout functionality
        exitButton.setOnClickListener(){
            finish()
            Firebase.auth.signOut()
            startActivity(Intent(this@adminCalendarActivity, LoginActivity::class.java))
        }

        //Gets the bottom bar navigation working
        bottomNavigationView.setupWithNavController(navController)

    }
}