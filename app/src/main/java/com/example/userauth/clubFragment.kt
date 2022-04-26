package com.example.userauth

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.util.stream.IntStream.range

class clubFragment : Fragment() {

    //declares class variables.
    private val myRef = FirebaseDatabase.getInstance().getReference("clubActivity")
    private lateinit var databaseListener: ValueEventListener
    private var clubNumber = -2
    private var currentClub = 0
    private var clubList = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_club, container, false)
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_club, container, false)
    }

    override fun onStart(){
        super.onStart()
        //imports xml attributes as variables
        val clubName1 = requireView().findViewById<TextView>(R.id.tvEmailName1)
        val clubName2 = requireView().findViewById<TextView>(R.id.tvEmailName2)
        val clubName3 = requireView().findViewById<TextView>(R.id.tvEmailName3)
        val roomNumber1 = requireView().findViewById<TextView>(R.id.tvRoomNumber1)
        val roomNumber2 = requireView().findViewById<TextView>(R.id.tvRoomNumber2)
        val roomNumber3 = requireView().findViewById<TextView>(R.id.tvRoomNumber3)
        val teacherName1 = requireView().findViewById<TextView>(R.id.tvTeacherName1)
        val teacherName2 = requireView().findViewById<TextView>(R.id.tvTeacherName2)
        val teacherName3 = requireView().findViewById<TextView>(R.id.tvTeacherName3)
        val meetingTime1 = requireView().findViewById<TextView>(R.id.tvClassName1)
        val meetingTime2 = requireView().findViewById<TextView>(R.id.tvClassName2)
        val meetingTime3 = requireView().findViewById<TextView>(R.id.tvClassName3)
        val backButton = requireView().findViewById<Button>(R.id.btnBack)
        val nextButton = requireView().findViewById<Button>(R.id.btnNext)

        //clears a club from the screen.
        fun clearScreen(clubName: TextView, roomNumber: TextView, meetingTime: TextView, teacherName: TextView) {
            clubName.text = " "
            roomNumber.text = " "
            meetingTime.text = " "
            teacherName.text = " "
        }

        //gets and displays all sub-attributes of a club.
        fun getValueFromDatabase(uidNumber: Int, clubName: TextView, roomNumber: TextView, meetingTime: TextView, teacherName: TextView){
            val databaseSearchReference = myRef.child("clubNumber$uidNumber")
            val clubNameDB = databaseSearchReference.child("clubName")
            val meetingTimeDB = databaseSearchReference.child("meetingTime")
            val room = databaseSearchReference.child("room")
            val teacherNameDB = databaseSearchReference.child("teacherName")
            clubNameDB.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = dataSnapshot.getValue<String>()
                    if (value == null) {
                        clubName.text = " "
                    } else {
                        clubName.text = " Club Name: $value"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Toast.makeText(
                        activity,
                        "Failed to read club data. Check your network connection or restart the app.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.w("joe", "Failed to read value.", error.toException())
                }
            })

            meetingTimeDB.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = dataSnapshot.getValue<String>()
                    if (value == null) {
                        meetingTime.text = " "
                    } else {
                        meetingTime.text = " Meeting Time: $value"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Toast.makeText(
                        activity,
                        "Failed to read club data. Check your network connection or restart the app.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.w("joe", "Failed to read value.", error.toException())
                }
            })

            room.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = dataSnapshot.getValue<String>()
                    if (value == null) {
                        roomNumber.text = " "
                    } else {
                        roomNumber.text = " Room Number: $value"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Toast.makeText(
                        activity,
                        "Failed to read club data. Check your network connection or restart the app.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.w("joe", "Failed to read value.", error.toException())
                }
            })

            teacherNameDB.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = dataSnapshot.getValue<String>()
                    if (value == null) {
                        teacherName.text = " "
                    } else {
                        teacherName.text = " Teacher Name: $value"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Toast.makeText(
                        activity,
                        "Failed to read club data. Check your network connection or restart the app.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.w("joe", "Failed to read value.", error.toException())
                }
            })
        }

        //This function is called to update a certain amount of clubs.
        //Passing a 1 results in 3 clubs being displayed, 2 2, and 1 3.
        fun getVals(num:Int){
            clearScreen(clubName1, roomNumber1, meetingTime1, teacherName1)
            clearScreen(clubName2, roomNumber2, meetingTime2, teacherName2)
            clearScreen(clubName3, roomNumber3, meetingTime3, teacherName3)
            if (num == 1){
                getValueFromDatabase(clubList[currentClub-2], clubName1, roomNumber1, meetingTime1, teacherName1)
                getValueFromDatabase(clubList[currentClub-1], clubName2, roomNumber2, meetingTime2, teacherName2)
                getValueFromDatabase(clubList[currentClub], clubName3, roomNumber3, meetingTime3, teacherName3)
            } else if (num == 2){
                getValueFromDatabase(clubList[currentClub-1], clubName1, roomNumber1, meetingTime1, teacherName1)
                getValueFromDatabase(clubList[currentClub], clubName2, roomNumber2, meetingTime2, teacherName2)
                clearScreen(clubName3, roomNumber3, meetingTime3, teacherName3)
            } else {
                getValueFromDatabase(clubList[currentClub], clubName1, roomNumber1, meetingTime1, teacherName1)
                clearScreen(clubName2, roomNumber2, meetingTime2, teacherName2)
                clearScreen(clubName3, roomNumber3, meetingTime3, teacherName3)
            }
        }

        //listens for change in the database
        databaseListener = myRef.child("funValue").addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onDataChange(myRefSnapshot: DataSnapshot) {
                myRef.addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        //creates a local version of the club database and filters it into variables.
                        clubList = mutableListOf()
                        var joe = snapshot.children
                        var iteratorNum = 0
                        var mapOfChildren = mutableMapOf<Int, List<String>>()
                        for (childsnapshot in joe) {
                            iteratorNum++
                            var listOfChildren =
                                (childsnapshot.value.toString()).replace("{", "").replace("}", "").split(",").toMutableList()
                            for (i in range(0,listOfChildren.size)){
                                listOfChildren[i] = listOfChildren[i].replaceBefore("=","").replace("=", "").toString()
                            }
                            mapOfChildren.put(iteratorNum, listOfChildren)

                        }
                        var emailNumberStringSnapshot: String? = mapOfChildren[1]?.get(0)
                        var emailNumberSnapshot = emailNumberStringSnapshot.toString().toInt()
                        mapOfChildren.remove(mapOfChildren.size)
                        mapOfChildren.remove(1)

                        var visibleList = mutableListOf<Int>()
                        for (i in range(2,mapOfChildren.size+2)){
                            var positionInMap = mapOfChildren[i]
                            if (positionInMap!![1]!! != null){
                                if (positionInMap!![1]  == "true"){
                                    visibleList.add(i-1)
                                }
                            }
                        }
                        var emailMap =mutableMapOf<Int, String>()
                        for (i in range(2,mapOfChildren.size+2)){
                            var positionInMap = mapOfChildren[i]
                            if (positionInMap!![3]!! != null){
                                emailMap[i-1] = positionInMap!![3]!!
                            }
                        }
                        var emailList = mutableListOf<String>()
                        for (i in range(0,visibleList.size)){
                            emailMap[visibleList[i]]?.let { emailList.add(it) }
                        }
                        emailList.sort()
                        var numericalEmailMap = mutableMapOf<String, Int>()
                        for (i in range (1, emailList.size+1)){
                            emailMap.forEach { (k, s) -> if( k == visibleList[i-1] ) {numericalEmailMap.put(emailList[i-1], k)   } }
                        }
                        for (i in range (1, (emailList.size)+1)){
                            numericalEmailMap[emailList[i-1]]?.let { clubList.add(it) }
                        }
                        //determines how many, if any, clubs to display.
                        if (clubNumber+2 == 0){
                            clubNumber = emailNumberSnapshot
                            if (clubList.size >= 3){
                                currentClub = 2
                            } else {
                                currentClub = clubList.size-1
                            }
                        } else {
                            clubNumber = emailNumberSnapshot
                            if (((currentClub+1)%3 != 0) || (clubList.size< 3)){
                                if (clubList.size == 0) {
                                    currentClub == -1
                                    Toast.makeText(
                                        activity,
                                        "No clubs have been created yet or none are in the roster.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else if (currentClub + 1 < clubList.size) {
                                    currentClub++
                                    Toast.makeText(
                                        activity,
                                        "A club has been added to the roster.$currentClub",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (currentClub + 1 > clubList.size) {
                                    currentClub--
                                    Toast.makeText(
                                        activity,
                                        "A club has been removed from the roster.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        if ((currentClub+1)%3 == 0){
                            getVals(1)
                        } else {
                            if ((currentClub+1)%3 == 2){
                                getVals(2)
                            } else if ((currentClub+1) %3 == 1){
                                getVals(3)
                            } else {
                                clearScreen(clubName1, roomNumber1, meetingTime1, teacherName1)
                                clearScreen(clubName2, roomNumber2, meetingTime2, teacherName2)
                                clearScreen(clubName3, roomNumber3, meetingTime3, teacherName3)
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            activity,
                            "Failed to read email data. Check your network connection or restart the app.",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.w("joe", "Failed to read value.", error.toException())
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    activity,
                    "Failed to read email data. Check your network connection or restart the app.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.w("joe", "Failed to read value.", error.toException())
            }
        })

        //updates currentClub and displays the next club(s) in the list.
        nextButton.setOnClickListener{
            if ((((currentClub+1)%3) == 0) && (currentClub+2 <= clubList.size)){
                if (((currentClub+4)%3 == 0) && (currentClub+4 <= clubList.size)){
                    currentClub+=3
                    getVals(1)
                }else if (((currentClub+3)%3 != 0) && ((currentClub+3) <= clubList.size)){
                    currentClub+=2
                    getVals(2)
                }else if (((currentClub+2)%3 != 0) && ((currentClub+2) <= clubList.size)){
                    currentClub+=1
                    getVals(3)
                }
            } else {
                Toast.makeText(
                    activity,
                    "No More Clubs to Load",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        //updates currentClub and displays the previous clubs in the list.
        backButton.setOnClickListener{
            if ((currentClub-3)+1 != 0) {
                if (((currentClub + 1) % 3) != 0) {
                    currentClub -= ((currentClub + 1) % 3)
                    getVals(1)
                } else {
                    currentClub -= 3
                    getVals(1)
                }
            } else {
                Toast.makeText(
                    activity,
                    "No More Clubs to Load",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    override fun onStop(){
        super.onStop()
        myRef.removeEventListener(databaseListener)
    }
}