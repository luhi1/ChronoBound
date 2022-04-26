package com.example.userauth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class scheduleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onStart(){
        super.onStart()
        //adds all the different elements to the code
        val wholeLn = requireView().findViewById<LinearLayout>(R.id.wholeLn)
        val prevBt = requireView().findViewById<Button>(R.id.prevBt)
        val dayTx = requireView().findViewById<TextView>(R.id.dayTx)
        val nextBt = requireView().findViewById<Button>(R.id.nextBt)
        val periodEdList = arrayOf(
            requireView().findViewById<EditText>(R.id.period1Ed),
            requireView().findViewById<EditText>(R.id.period2Ed),
            requireView().findViewById<EditText>(R.id.period3Ed),
            requireView().findViewById<EditText>(R.id.period4Ed),
            requireView().findViewById<EditText>(R.id.period5Ed),
            requireView().findViewById<EditText>(R.id.period6Ed),
            requireView().findViewById<EditText>(R.id.period7Ed),
            requireView().findViewById<EditText>(R.id.period8Ed)
        )
        val b4ScoolEd = requireView().findViewById<EditText>(R.id.b4ScoolEd)
        val afScoolEd = requireView().findViewById<EditText>(R.id.afScoolEd)
        val homeWrkEd = requireView().findViewById<EditText>(R.id.homeWrkEd)
        val sbmtScedBt = requireView().findViewById<Button>(R.id.sbmtScedBt)
        val goBackBt = requireView().findViewById<Button>(R.id.goBackBt)



        val rowsLn = requireView().findViewById<LinearLayout>(R.id.rowsLn)

        val mondTx = requireView().findViewById<TextView>(R.id.mondTx)
        val tuesTx = requireView().findViewById<TextView>(R.id.tuesTx)
        val wednTx = requireView().findViewById<TextView>(R.id.wednTx)
        val thurTx = requireView().findViewById<TextView>(R.id.thurTx)
        val fridTx = requireView().findViewById<TextView>(R.id.fridTx)

        //val p1Tx = requireView().findViewById<TextView>(R.id.p1Tx)
        //holds all of the scheduler text views in a organized fashion
        val rowList = arrayOf(
            arrayOf(
                requireView().findViewById<TextView>(R.id.p1MoTx),
                requireView().findViewById<TextView>(R.id.p1TuTx),
                requireView().findViewById<TextView>(R.id.p1WeTx),
                requireView().findViewById<TextView>(R.id.p1ThTx),
                requireView().findViewById<TextView>(R.id.p1FrTx)
            ),
            arrayOf(
                requireView().findViewById<TextView>(R.id.p2MoTx),
                requireView().findViewById<TextView>(R.id.p2TuTx),
                requireView().findViewById<TextView>(R.id.p2WeTx),
                requireView().findViewById<TextView>(R.id.p2ThTx),
                requireView().findViewById<TextView>(R.id.p2FrTx)
            ),
            arrayOf(
                requireView().findViewById<TextView>(R.id.p3MoTx),
                requireView().findViewById<TextView>(R.id.p3TuTx),
                requireView().findViewById<TextView>(R.id.p3WeTx),
                requireView().findViewById<TextView>(R.id.p3ThTx),
                requireView().findViewById<TextView>(R.id.p3FrTx)
            ),
            arrayOf(
                requireView().findViewById<TextView>(R.id.p4MoTx),
                requireView().findViewById<TextView>(R.id.p4TuTx),
                requireView().findViewById<TextView>(R.id.p4WeTx),
                requireView().findViewById<TextView>(R.id.p4ThTx),
                requireView().findViewById<TextView>(R.id.p4FrTx)
            ),
            arrayOf(
                requireView().findViewById<TextView>(R.id.p5MoTx),
                requireView().findViewById<TextView>(R.id.p5TuTx),
                requireView().findViewById<TextView>(R.id.p5WeTx),
                requireView().findViewById<TextView>(R.id.p5ThTx),
                requireView().findViewById<TextView>(R.id.p5FrTx)
            ),
            arrayOf(
                requireView().findViewById<TextView>(R.id.p6MoTx),
                requireView().findViewById<TextView>(R.id.p6TuTx),
                requireView().findViewById<TextView>(R.id.p6WeTx),
                requireView().findViewById<TextView>(R.id.p6ThTx),
                requireView().findViewById<TextView>(R.id.p6FrTx)
            ),
            arrayOf(
                requireView().findViewById<TextView>(R.id.p7MoTx),
                requireView().findViewById<TextView>(R.id.p7TuTx),
                requireView().findViewById<TextView>(R.id.p7WeTx),
                requireView().findViewById<TextView>(R.id.p7ThTx),
                requireView().findViewById<TextView>(R.id.p7FrTx)
            ),
            arrayOf(
                requireView().findViewById<TextView>(R.id.p8MoTx),
                requireView().findViewById<TextView>(R.id.p8TuTx),
                requireView().findViewById<TextView>(R.id.p8WeTx),
                requireView().findViewById<TextView>(R.id.p8ThTx),
                requireView().findViewById<TextView>(R.id.p8FrTx)
            )
        )









        val dayTextList = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
        var day = 0
        //holds user id
        val user = FirebaseAuth.getInstance().uid

        //function adds to firebase
        fun addtoDatabase(path: List<String>, reference: String, inpu: String){
            var myRef = FirebaseDatabase.getInstance().getReference(reference)
            //allows list to be used for the path
            for (i in path.indices){
                myRef = myRef.child(path[i])
            }

            //the code below was taken from the firebase documentation
            myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    //taken code segment end
                    myRef.setValue(inpu)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Toast.makeText(activity, "error " + "Failed to write value." + error.toException(), Toast.LENGTH_LONG).show()
                }
            })
        }
        //function for getting info from firebase
        fun getSingleValueFromDatabase(path: List<String>, reference: String, tvid: TextView){
            var myRef = FirebaseDatabase.getInstance().getReference(reference)
            //allows list to be used for the path
            for (i in path.indices){
                myRef = myRef.child(path[i])
            }

            //the code below was taken from the firebase documentation
            myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    val value = dataSnapshot.getValue<String>()
                    //taken code segment end
                    //makes sure null isnt returned if there is nothing in the database
                    if (value == null){
                        tvid.setText("")
                    } else {

                        tvid.setText("$value")
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Toast.makeText(activity, "joe" + "Failed to read value." + error.toException(), Toast.LENGTH_LONG).show()
                }
            })
        }

        //fills the text entry fields with the text that had been put before
        fun fillIn(){
            dayTx.text = dayTextList[day]
            for (i in 1..periodEdList.size) {
                getSingleValueFromDatabase(listOf(user.toString(), dayTextList[day], "period" + i.toString()), "schedActivity", periodEdList[i - 1])
            }
            getSingleValueFromDatabase(listOf(user.toString(), dayTextList[day], "b4Scool"), "schedActivity", b4ScoolEd)
            getSingleValueFromDatabase(listOf(user.toString(), dayTextList[day], "afScool"), "schedActivity", afScoolEd)
            getSingleValueFromDatabase(listOf(user.toString(), dayTextList[day], "homeWrk"), "schedActivity", homeWrkEd)
        }

        //fills the grid of periods/days
        fun colorIn(){
            for (i in 1..rowList.size) {
                for (ele in 0..rowList[i - 1].size-1){
                    getSingleValueFromDatabase(listOf(user.toString(), dayTextList[ele], "period" + i.toString()), "schedActivity", rowList[i - 1][ele])
                    rowList[i - 1][ele].setOnClickListener {
                        day = ele
                        rowsLn.isVisible = false
                        wholeLn.isVisible = true
                        fillIn()
                    }

                }
            }


        }


        wholeLn.isVisible = false
        colorIn()
        
        //when submit is clicked evewrything in the text fields is added to firebase
        sbmtScedBt.setOnClickListener {
            for (i in 1..periodEdList.size) {
                addtoDatabase(listOf(user.toString(), dayTextList[day], "period" + i.toString()), "schedActivity", periodEdList[i - 1].getText().toString())
            }
            addtoDatabase(listOf(user.toString(), dayTextList[day], "b4Scool"), "schedActivity", b4ScoolEd.getText().toString())
            addtoDatabase(listOf(user.toString(), dayTextList[day], "afScool"), "schedActivity", afScoolEd.getText().toString())
            addtoDatabase(listOf(user.toString(), dayTextList[day], "homeWrk"), "schedActivity", homeWrkEd.getText().toString())
            Toast.makeText(activity, "Plans added.", Toast.LENGTH_SHORT).show()

        }
        
        //makes all of the weekdays labels clickable, so you can enter that days view
        mondTx.setOnClickListener {
            day = 0
            rowsLn.isVisible = false
            wholeLn.isVisible = true
            fillIn()
        }
        tuesTx.setOnClickListener {
            day = 1
            rowsLn.isVisible = false
            wholeLn.isVisible = true
            fillIn()
        }
        wednTx.setOnClickListener {
            day = 2
            rowsLn.isVisible = false
            wholeLn.isVisible = true
            fillIn()
        }
        thurTx.setOnClickListener {
            day = 3
            rowsLn.isVisible = false
            wholeLn.isVisible = true
            fillIn()
        }
        fridTx.setOnClickListener {
            day = 4
            rowsLn.isVisible = false
            wholeLn.isVisible = true
            fillIn()
        }
        goBackBt.setOnClickListener {
            rowsLn.isVisible = true
            wholeLn.isVisible = false
            colorIn()
        }



        //goes to previous day
        prevBt.setOnClickListener {
            day -= 1
            //allows negative numbers
            while (day < 0){
                day += 5
            }

            day %= 5


            fillIn()
        }
        //goes to the day after
        nextBt.setOnClickListener {
            day = (day + 1) % 5


            fillIn()
        }
    }
}
