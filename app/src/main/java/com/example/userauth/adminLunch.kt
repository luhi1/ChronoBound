package com.example.userauth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class adminLunch : Fragment() {

    //Declare class variables
    private lateinit var databaseListener: ValueEventListener
    private val myRef = FirebaseDatabase.getInstance().getReference("lunchActivity")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_admin_lunch, container, false)

        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_admin_lunch, container, false)
    }
    override fun onStart(){
        super.onStart()
        //import xml attributes as variables
        val monday_main = requireView().findViewById<EditText>(R.id.monday_main)
        val monday_side = requireView().findViewById<EditText>(R.id.monday_side)
        val monday_drink = requireView().findViewById<EditText>(R.id.monday_drink)
        val tuesday_main = requireView().findViewById<EditText>(R.id.tuesday_main)
        val tuesday_side = requireView().findViewById<EditText>(R.id.tuesday_side)
        val tuesday_drink = requireView().findViewById<EditText>(R.id.tuesday_drink)
        val wednesday_main = requireView().findViewById<EditText>(R.id.wednesday_main)
        val wednesday_side = requireView().findViewById<EditText>(R.id.wednesday_side)
        val wednesday_drink = requireView().findViewById<EditText>(R.id.wednesday_drink)
        val thursday_main = requireView().findViewById<EditText>(R.id.thursday_main)
        val thursday_side = requireView().findViewById<EditText>(R.id.thursday_side)
        val thursday_drink = requireView().findViewById<EditText>(R.id.thursday_drink)
        val friday_main = requireView().findViewById<EditText>(R.id.friday_main)
        val friday_side = requireView().findViewById<EditText>(R.id.friday_side)
        val friday_drink = requireView().findViewById<EditText>(R.id.friday_drink)

        val saveButton = requireView().findViewById<TextView>(R.id.save_button)
            //Listeners for change in the lunch Fragment.
            databaseListener = myRef.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //update the screen to reflect the database.
                    getValueFromDatabase(listOf("Monday", "Main"), "lunchActivity", monday_main)
                    getValueFromDatabase(listOf("Monday", "Side"), "lunchActivity", monday_side)
                    getValueFromDatabase(listOf("Monday", "Drink"), "lunchActivity", monday_drink)
                    getValueFromDatabase(listOf("Tuesday", "Main"), "lunchActivity", tuesday_main)
                    getValueFromDatabase(listOf("Tuesday", "Side"), "lunchActivity", tuesday_side)
                    getValueFromDatabase(listOf("Tuesday", "Drink"), "lunchActivity",tuesday_drink)
                    getValueFromDatabase(listOf("Wednesday", "Main"), "lunchActivity", wednesday_main)
                    getValueFromDatabase(listOf("Wednesday", "Side"), "lunchActivity", wednesday_side)
                    getValueFromDatabase(listOf("Wednesday", "Drink"), "lunchActivity", wednesday_drink)
                    getValueFromDatabase(listOf("Thursday", "Main"), "lunchActivity", thursday_main)
                    getValueFromDatabase(listOf("Thursday", "Side"), "lunchActivity", thursday_side)
                    getValueFromDatabase(listOf("Thursday", "Drink"), "lunchActivity", thursday_drink)
                    getValueFromDatabase(listOf("Friday", "Main"), "lunchActivity", friday_main)
                    getValueFromDatabase(listOf("Friday", "Side"), "lunchActivity", friday_side)
                    getValueFromDatabase(listOf("Friday", "Drink"), "lunchActivity", friday_drink)
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        activity,
                        "Failed to read lunch menu. Check your network connection or restart the app.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

            //update the databse to reflect the lunch menu visually displayed.
            saveButton.setOnClickListener(){
                myRef.child("Monday").child("Main").setValue(monday_main.text.toString())
                myRef.child("Monday").child("Side").setValue(monday_side.text.toString())
                myRef.child("Monday").child("Drink").setValue(monday_drink.text.toString())
                myRef.child("Tuesday").child("Main").setValue(tuesday_main.text.toString())
                myRef.child("Tuesday").child("Side").setValue(tuesday_side.text.toString())
                myRef.child("Tuesday").child("Drink").setValue(tuesday_drink.text.toString())
                myRef.child("Wednesday").child("Main").setValue(wednesday_main.text.toString())
                myRef.child("Wednesday").child("Side").setValue(wednesday_side.text.toString())
                myRef.child("Wednesday").child("Drink").setValue(wednesday_drink.text.toString())
                myRef.child("Thursday").child("Main").setValue(thursday_main.text.toString())
                myRef.child("Thursday").child("Side").setValue(thursday_side.text.toString())
                myRef.child("Thursday").child("Drink").setValue(thursday_drink.text.toString())
                myRef.child("Friday").child("Main").setValue(friday_main.text.toString())
                myRef.child("Friday").child("Side").setValue(friday_side.text.toString())
                myRef.child("Friday").child("Drink").setValue(friday_drink.text.toString())
            }

        }

    //gets a value from the database and displays it.
    fun getValueFromDatabase(path: List<String>, reference: String, etid: EditText) {
        var myRef = FirebaseDatabase.getInstance().getReference(reference)
        for (i in path.indices) {
            myRef = myRef.child(path[i])
        }
        //the code below was taken from the firebase documentation
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                etid.setText("$value")
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("joe", "Failed to read value.", error.toException())
            }
        })
    }
    override fun onStop() {
        super.onStop()
        //snaps listeners
        myRef.removeEventListener(databaseListener)
    }
}