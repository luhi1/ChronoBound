package com.example.userauth

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.util.stream.IntStream.range

class emailFragment : Fragment() {

    private val myRef = FirebaseDatabase.getInstance().getReference("emailActivity")
    private lateinit var databaseListener: ValueEventListener
    private var clubNumber = -2
    private var currentClub = 0
    private var clubList = mutableListOf<Int>()
    private var mapOfNames = mutableMapOf<String?, Int?>()
    private var listOfNames = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_email, container, false)
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_email, container, false)
    }

    override fun onStart(){
        super.onStart()
        val etclubName1 = requireView().findViewById<TextView>(R.id.tvEmailName1)
        val etclubName2 = requireView().findViewById<TextView>(R.id.tvEmailName2)
        val etclubName3 = requireView().findViewById<TextView>(R.id.tvEmailName3)
        val etroomNumber1 = requireView().findViewById<TextView>(R.id.tvRoomNumber1)
        val etroomNumber2 = requireView().findViewById<TextView>(R.id.tvRoomNumber2)
        val etroomNumber3 = requireView().findViewById<TextView>(R.id.tvRoomNumber3)
        val etteacherName1 = requireView().findViewById<TextView>(R.id.tvTeacherName1)
        val etteacherName2 = requireView().findViewById<TextView>(R.id.tvTeacherName2)
        val etteacherName3 = requireView().findViewById<TextView>(R.id.tvTeacherName3)
        val etmeetingTime1 = requireView().findViewById<TextView>(R.id.tvClassName1)
        val etmeetingTime2 = requireView().findViewById<TextView>(R.id.tvClassName2)
        val etmeetingTime3 = requireView().findViewById<TextView>(R.id.tvClassName3)
        val backButton = requireView().findViewById<Button>(R.id.btnBackMain)
        val nextButton = requireView().findViewById<Button>(R.id.btnNext)
        val emailButton1 =  requireView().findViewById<Button>(R.id.email_button)
        val emailButton2 =  requireView().findViewById<Button>(R.id.email_button2)
        val emailButton3 =  requireView().findViewById<Button>(R.id.email_button3)
        val emailBox =  requireView().findViewById<LinearLayout>(R.id.emailLayout)
        val mainEmailScreen =  requireView().findViewById<ConstraintLayout>(R.id.mainEmailScreen)
        val btnSend =  requireView().findViewById<Button>(R.id.btnSend)
        val btnBackEmail =  requireView().findViewById<Button>(R.id.btnBackEmail)
        var emailNumber = 0
        var email = arrayOf("")
        var subject = ""
        var message = ""
        var emailNum = 0

        fun clearScreen(clubName: TextView, roomNumber: TextView, meetingTime: TextView, teacherName: TextView) {
            clubName.text = " "
            roomNumber.text = " "
            meetingTime.text = " "
            teacherName.text = " "
        }

        fun getValueFromDatabase(uidNumber: Int, clubName: TextView, roomNumber: TextView, meetingTime: TextView, teacherName: TextView){
            val databaseSearchReference = myRef.child("emailNumber$uidNumber")
            val clubNameDB = databaseSearchReference.child("class")
            val room = databaseSearchReference.child("room")
            val email = databaseSearchReference.child("email")
            val teacherNameDB = databaseSearchReference.child("teacherName")
            clubNameDB.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = dataSnapshot.getValue<String>()
                    if (value == null) {
                        clubName.text = " "
                    } else {
                        clubName.text = "Class Name: $value"
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

            email.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = dataSnapshot.getValue<String>()
                    if (value == null) {
                        meetingTime.text = " "
                    } else {
                        meetingTime.text = "Email: $value"
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
                        roomNumber.text = "Room Number: $value"
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
                        teacherName.text = "Teacher Name: $value"
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

        fun getVals(num:Int){
            clearScreen(etclubName1, etroomNumber1, etmeetingTime1, etteacherName1)
            clearScreen(etclubName2, etroomNumber2, etmeetingTime2, etteacherName2)
            clearScreen(etclubName3, etroomNumber3, etmeetingTime3, etteacherName3)
            emailButton1.isClickable = true
            emailButton2.isClickable = true
            emailButton3.isClickable = true
            if (num == 1){
                getValueFromDatabase(clubList[currentClub-2], etclubName1, etroomNumber1, etmeetingTime1, etteacherName1)
                getValueFromDatabase(clubList[currentClub-1], etclubName2, etroomNumber2, etmeetingTime2, etteacherName2)
                getValueFromDatabase(clubList[currentClub], etclubName3, etroomNumber3, etmeetingTime3, etteacherName3)
            } else if (num == 2){
                getValueFromDatabase(clubList[currentClub-1], etclubName1, etroomNumber1, etmeetingTime1, etteacherName1)
                getValueFromDatabase(clubList[currentClub], etclubName2, etroomNumber2, etmeetingTime2, etteacherName2)
                clearScreen(etclubName3, etroomNumber3, etmeetingTime3, etteacherName3)
                emailButton3.isClickable = false
            } else {
                getValueFromDatabase(clubList[currentClub], etclubName1, etroomNumber1, etmeetingTime1, etteacherName1)
                clearScreen(etclubName2, etroomNumber2, etmeetingTime2, etteacherName2)
                clearScreen(etclubName3, etroomNumber3, etmeetingTime3, etteacherName3)
                emailButton2.isClickable = false
                emailButton3.isClickable = false
            }
        }
        databaseListener = myRef.child("funValue").addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onDataChange(myRefSnapshot: DataSnapshot) {
                myRef.addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
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
                        //figure out how to get variable! DELETE IS CAUSING ERRORS FIX IT
                        var visibleList = mutableListOf<Int>()
                        for (i in range(2,mapOfChildren.size+2)){
                            var positionInMap = mapOfChildren[i]
                            if (positionInMap!![0]!! != null){
                                if (positionInMap!![0]  == "true"){
                                    visibleList.add(i-1)
                                }
                            }
                        }
                        var emailMap =mutableMapOf<Int, String>()
                        for (i in range(2,mapOfChildren.size+2)){
                            var positionInMap = mapOfChildren[i]
                            if (positionInMap!![1]!! != null){
                                emailMap[i-1] = positionInMap!![1]!!
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
                                clearScreen(etclubName1, etroomNumber1, etmeetingTime1, etteacherName1)
                                clearScreen(etclubName2, etroomNumber2, etmeetingTime2, etteacherName2)
                                clearScreen(etclubName3, etroomNumber3, etmeetingTime3, etteacherName3)
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
        emailButton1.setOnClickListener {
            mainEmailScreen.visibility = View.INVISIBLE
            emailBox.visibility = View.VISIBLE
            requireView().findViewById<EditText>(R.id.txtSub).setText("")
            requireView().findViewById<EditText>(R.id.txtMsg).setText("")
            requireView().findViewById<TextView>(R.id.txtTo).text = requireView().findViewById<TextView>(R.id.tvClassName1).text.split("Email: ")[1].toString().filter { !it.isWhitespace() }
        }
        emailButton2.setOnClickListener {
            mainEmailScreen.visibility = View.INVISIBLE
            emailBox.visibility = View.VISIBLE
            requireView().findViewById<EditText>(R.id.txtSub).setText("")
            requireView().findViewById<EditText>(R.id.txtMsg).setText("")
            requireView().findViewById<TextView>(R.id.txtTo).text = requireView().findViewById<TextView>(R.id.tvClassName2).text.split("Email: ")[1].toString().filter { !it.isWhitespace() }
        }
        emailButton3.setOnClickListener {
            mainEmailScreen.visibility = View.INVISIBLE
            emailBox.visibility = View.VISIBLE
            requireView().findViewById<EditText>(R.id.txtSub).setText("")
            requireView().findViewById<EditText>(R.id.txtMsg).setText("")
            requireView().findViewById<TextView>(R.id.txtTo).text = requireView().findViewById<TextView>(R.id.tvClassName3).text.split("Email: ")[1].toString().filter { !it.isWhitespace() }
        }
        btnBackEmail.setOnClickListener {
            mainEmailScreen.visibility = View.VISIBLE
            emailBox.visibility = View.INVISIBLE
        }
        btnSend.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(requireView().findViewById<TextView>(R.id.txtTo).text.toString()))
            intent.putExtra(Intent.EXTRA_SUBJECT, requireView().findViewById<EditText>(R.id.txtSub).text.toString())
            intent.putExtra(Intent.EXTRA_TEXT, requireView().findViewById<EditText>(R.id.txtMsg).text.toString())
            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent, "Select email"))
            mainEmailScreen.visibility = View.VISIBLE
            emailBox.visibility = View.INVISIBLE
        }
    }

    override fun onStop(){
        super.onStop()
        myRef.removeEventListener(databaseListener)
    }
}