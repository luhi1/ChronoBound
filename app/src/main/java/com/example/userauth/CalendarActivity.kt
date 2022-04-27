package com.example.userauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inflates layout
        setContentView(R.layout.activity_calendar)

        //declares variables
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragmentStorage)
        val fragmentLayout = findViewById<View>(R.id.fragmentStorage)
        val bugButton = findViewById<ImageView>(R.id.bugButton)
        val saveBugButton = findViewById<Button>(R.id.saveBugButton)
        val backBugButton = findViewById<Button>(R.id.backBugButton)
        val exitButton = findViewById<ImageView>(R.id.exitButton)
        val bugLayout = findViewById<LinearLayout>(R.id.bugLayout)
        val bugText = findViewById<EditText>(R.id.etBug)

        //gets bottom bar navigation working.
        bottomNavigationView.setupWithNavController(navController)

        //creates logout button functionality
        exitButton.setOnClickListener(){
            finish()
            Firebase.auth.signOut()
            startActivity(Intent(this@CalendarActivity, LoginActivity::class.java))
        }

        //displays the report a bug screen.
        bugButton.setOnClickListener(){
            bugText.setText("")
            fragmentLayout.visibility = View.INVISIBLE
            bugLayout.visibility = View.VISIBLE
            bugButton.visibility = View.INVISIBLE
        }

        //saves the bug typed in the report a bug screen. It hides the popup and returns to the main screen afterwards.
        saveBugButton.setOnClickListener(){
            FirebaseDatabase.getInstance().getReference("bugs").child("bugNumber").addListenerForSingleValueEvent(object:
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val intValue = snapshot.getValue<Int>()
                    val value = (intValue!! +1)
                    FirebaseDatabase.getInstance().getReference("bugs").child("bug$value").setValue(bugText.text.toString())
                    FirebaseDatabase.getInstance().getReference("bugs").child("bugNumber").setValue(intValue+1)
                    fragmentLayout.visibility = View.VISIBLE
                    bugLayout.visibility = View.INVISIBLE
                    bugButton.visibility = View.VISIBLE

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CalendarActivity, "Failed to connect to the database. Check your network connection.", Toast.LENGTH_LONG)
                    Log.d("joe", "Failed to read data.")
                }

            })
        }

        //hides the bug popup and returns to the main screen.
        backBugButton.setOnClickListener(){
            fragmentLayout.visibility = View.VISIBLE
            bugLayout.visibility = View.INVISIBLE
            bugButton.visibility = View.VISIBLE
        }
    }
}