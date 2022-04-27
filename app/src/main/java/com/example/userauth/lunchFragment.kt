package com.example.userauth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class lunchFragment : Fragment() {

    //declare class variables
    private lateinit var databaseListener: ValueEventListener
    private val myRef = FirebaseDatabase.getInstance().getReference("lunchActivity")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_lunch, container, false)

        super.onCreate(savedInstanceState)

        //creates a listener for change in the database.
        databaseListener = myRef.addValueEventListener(object: ValueEventListener {
               override fun onDataChange(snapshot: DataSnapshot) {
                //updates all values of the xml to match the database.
                view?.let { getValueFromDatabase(listOf("Monday", "Main"), "lunchActivity", it.findViewById(R.id.monday_main)) }
                view?.let { getValueFromDatabase(listOf("Monday", "Side"), "lunchActivity", it.findViewById(R.id.monday_side)) }
                view?.let { getValueFromDatabase(listOf("Monday", "Drink"), "lunchActivity", it.findViewById(R.id.monday_drink)) }
                view?.let { getValueFromDatabase(listOf("Tuesday", "Main"), "lunchActivity", it.findViewById(R.id.tuesday_main)) }
                view?.let { getValueFromDatabase(listOf("Tuesday", "Side"), "lunchActivity", it.findViewById(R.id.tuesday_side)) }
                view?.let { getValueFromDatabase(listOf("Tuesday", "Drink"), "lunchActivity", it.findViewById(R.id.tuesday_drink)) }
                view?.let { getValueFromDatabase(listOf("Wednesday", "Main"), "lunchActivity", it.findViewById(R.id.wednesday_main)) }
                view?.let { getValueFromDatabase(listOf("Wednesday", "Side"), "lunchActivity", it.findViewById(R.id.wednesday_side)) }
                view?.let { getValueFromDatabase(listOf("Wednesday", "Drink"), "lunchActivity", it.findViewById(R.id.wednesday_drink)) }
                view?.let { getValueFromDatabase(listOf("Thursday", "Main"), "lunchActivity", it.findViewById(R.id.thursday_main)) }
                view?.let { getValueFromDatabase(listOf("Thursday", "Side"), "lunchActivity", it.findViewById(R.id.thursday_side)) }
                view?.let { getValueFromDatabase(listOf("Thursday", "Drink"), "lunchActivity", it.findViewById(R.id.thursday_drink)) }
                view?.let { getValueFromDatabase(listOf("Friday", "Main"), "lunchActivity", it.findViewById(R.id.friday_main)) }
                view?.let { getValueFromDatabase(listOf("Friday", "Side"), "lunchActivity", it.findViewById(R.id.friday_side)) }
                view?.let { getValueFromDatabase(listOf("Friday", "Drink"), "lunchActivity", it.findViewById(R.id.friday_drink)) }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    activity,
                    "Failed to read lunch menu. Check your network connection or restart the app.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        return inflater.inflate(R.layout.fragment_lunch, container, false)
    }

    //get a single value from the database and display it on the screen.
    fun getValueFromDatabase(path: List<String>, reference: String, tvid: TextView){
        var myRef = FirebaseDatabase.getInstance().getReference(reference)
        for (i in path.indices){
            myRef = myRef.child(path[i])
        }
        //the code below was taken from the firebase documentation
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                tvid.text = "$value"
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("joe", "Failed to read value.", error.toException())
            }
        })
    }
    override fun onStop(){
        super.onStop()
        //Snap
        myRef.removeEventListener(databaseListener)
    }
}