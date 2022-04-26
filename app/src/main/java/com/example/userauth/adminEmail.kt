package com.example.userauth

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.stream.IntStream.range

class adminEmail : Fragment() {

    //Declare class variables.
    private val myRef = FirebaseDatabase.getInstance().getReference("emailActivity")
    private lateinit var databaseListener: ValueEventListener
    private var clubNumber = -2
    private var currentClub = 0
    private var clubList = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_admin_email, container, false)
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_admin_email, container, false)
    }

    @OptIn(InternalCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStart(){
        super.onStart()
        //Import xml attributes as variables.
        val etclubName1 = requireView().findViewById<TextView>(R.id.etEmailName1)
        val etclubName2 = requireView().findViewById<TextView>(R.id.etEmailName2)
        val etclubName3 = requireView().findViewById<TextView>(R.id.etEmailName3)
        val etroomNumber1 = requireView().findViewById<TextView>(R.id.etRoomNumber1)
        val etroomNumber2 = requireView().findViewById<TextView>(R.id.etRoomNumber2)
        val etroomNumber3 = requireView().findViewById<TextView>(R.id.etRoomNumber3)
        val etteacherName1 = requireView().findViewById<TextView>(R.id.etTeacherName1)
        val etteacherName2 = requireView().findViewById<TextView>(R.id.etTeacherName2)
        val etteacherName3 = requireView().findViewById<TextView>(R.id.etTeacherName3)
        val etmeetingTime1 = requireView().findViewById<TextView>(R.id.etClassName1)
        val etmeetingTime2 = requireView().findViewById<TextView>(R.id.etClassName2)
        val etmeetingTime3 = requireView().findViewById<TextView>(R.id.etClassName3)
        val backButton = requireView().findViewById<Button>(R.id.btnBack)
        val nextButton = requireView().findViewById<Button>(R.id.btnNext)
        val deleteButton1 = requireView().findViewById<Button>(R.id.delete_button)
        val deleteButton2 = requireView().findViewById<Button>(R.id.delete_button2)
        val deleteButton3 = requireView().findViewById<Button>(R.id.delete_button3)
        val saveButton1 = requireView().findViewById<Button>(R.id.save_button)
        val saveButton2 = requireView().findViewById<Button>(R.id.save_button2)
        val saveButton3 = requireView().findViewById<Button>(R.id.save_button3)
        val mainView = requireView().findViewById<ConstraintLayout>(R.id.mainView)
        val popupView = requireView().findViewById<LinearLayout>(R.id.emailPopUpLayout)
        val add_button = requireView().findViewById<Button>(R.id.add_button)
        val etTeacherNamePopup = requireView().findViewById<EditText>(R.id.etTeacherNamePopup)
        val etclassPopup = requireView().findViewById<EditText>(R.id.etClassPopup)
        val etRoomNumberPopup = requireView().findViewById<EditText>(R.id.etRoomNumberPopup)
        val etEmailPopup = requireView().findViewById<EditText>(R.id.etEmailPopup)
        val saveButtonPopup = requireView().findViewById<Button>(R.id.saveButtonPopup)
        val backButtonPopup = requireView().findViewById<Button>(R.id.backButtonPopup)
        var funValue:Boolean

        //create a one time listener for funValue.
        myRef.child("funValue").addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(clubNumbersnapshot: DataSnapshot) {
                funValue = clubNumbersnapshot.getValue<Boolean>()!!

                //displays the add an email layout on clicking the add a club button.
                add_button.setOnClickListener{
                    mainView.visibility = View.INVISIBLE
                    popupView.visibility = View.VISIBLE
                }

                //adds save button for the add a club popup functionality
                saveButtonPopup.setOnClickListener{
                    if (etclassPopup.text.toString() != "" && etEmailPopup.toString() != "" && etRoomNumberPopup.text.toString() != "" && etRoomNumberPopup.text.toString()!= "" ) {
                        myRef.child("emailNumber")
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(clubNumbersnapshot: DataSnapshot) {
                                    //creates a new club based on the values provided in the add a club layout.
                                    val value = clubNumbersnapshot.getValue<Int>()!! + 1
                                    myRef.child("emailNumber$value").child("class")
                                        .setValue(etclassPopup.text.toString())
                                    myRef.child("emailNumber$value").child("email")
                                        .setValue(etEmailPopup.text.toString())
                                    myRef.child("emailNumber$value").child("room")
                                        .setValue(etRoomNumberPopup.text.toString())
                                    myRef.child("emailNumber$value").child("teacherName")
                                        .setValue(etTeacherNamePopup.text.toString())
                                    myRef.child("emailNumber$value").child("visible").setValue(true)
                                    myRef.child("emailNumber").setValue(value)

                                    //resets the text values in the popup and then returns back to the main screen.
                                    etclassPopup.setText("")
                                    etRoomNumberPopup.setText("")
                                    etEmailPopup.setText("")
                                    etTeacherNamePopup.setText("")
                                    mainView.visibility = View.VISIBLE
                                    popupView.visibility = View.INVISIBLE

                                    //updates fun value
                                    if (funValue == true) {
                                        funValue = false
                                    } else {
                                        funValue = true
                                    }
                                    myRef.child("funValue").setValue(funValue)
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Toast.makeText(
                                        activity,
                                        "Failed to read club data. Check your network connection or restart the app.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Log.w("joe", "Failed to read value.", error.toException())
                                }

                            })
                    } else {
                        //Does not allow you to input a blank value.
                        Toast.makeText(
                            activity,
                            "You cannot leave a value blank",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                //returns you to the main screen from the popup.
                //clears all attributes of the popup screen.
                backButtonPopup.setOnClickListener{
                    etclassPopup.setText("")
                    etRoomNumberPopup.setText("")
                    etEmailPopup.setText("")
                    etTeacherNamePopup.setText("")
                    mainView.visibility = View.VISIBLE
                    popupView.visibility = View.INVISIBLE
                }

                //saves any changes to the first email and updates the funValue.
                saveButton1.setOnClickListener{
                    val clubUid = clubList[((currentClub-(currentClub%3))+1)-1]
                    myRef.child("emailNumber$clubUid").child("class").setValue(etclubName1.text.toString().trim())
                    myRef.child("emailNumber$clubUid").child("email").setValue(etmeetingTime1.text.toString().trim())
                    myRef.child("emailNumber$clubUid").child("room").setValue(etroomNumber1.text.toString().trim())
                    myRef.child("emailNumber$clubUid").child("teacherName").setValue(etteacherName1.text.toString().trim())
                    if (funValue == true){
                        funValue = false
                    } else {
                        funValue = true
                    }
                    myRef.child("funValue").setValue(funValue)
                }

                //saves any changes to the second email and updates the funValue.
                saveButton2.setOnClickListener(){
                    val clubUid = clubList[((currentClub-(currentClub%3))+2)-1]
                    myRef.child("emailNumber$clubUid").child("class").setValue(etclubName2.text.toString().trim())
                    myRef.child("emailNumber$clubUid").child("email").setValue(etmeetingTime2.text.toString().trim())
                    myRef.child("emailNumber$clubUid").child("room").setValue(etroomNumber2.text.toString().trim())
                    myRef.child("emailNumber$clubUid").child("teacherName").setValue(etteacherName2.text.toString().trim())
                    if (funValue == true){
                        funValue = false
                    } else {
                        funValue = true
                    }
                    myRef.child("funValue").setValue(funValue)
                }

                //saves any changes to the third email and updates the funValue.
                saveButton3.setOnClickListener(){
                    val clubUid = currentClub
                    myRef.child("emailNumber$clubUid").child("class").setValue(etclubName3.text.toString().trim())
                    myRef.child("emailNumber$clubUid").child("email").setValue(etmeetingTime3.text.toString().trim())
                    myRef.child("emailNumber$clubUid").child("room").setValue(etroomNumber3.text.toString().trim())
                    myRef.child("emailNumber$clubUid").child("teacherName").setValue(etteacherName3.text.toString().trim())
                    if (funValue == true){
                        funValue = false
                    } else {
                        funValue = true
                    }
                    myRef.child("funValue").setValue(funValue)
                }

                //changes the visibility of the first email to false and updates the funValue
                deleteButton1.setOnClickListener{
                    val clubUid = clubList[((currentClub-(currentClub%3))+1)-1]
                    if ((currentClub+1)-1 != 0) {
                        myRef.child("emailNumber$clubUid").child("visible").setValue(false)
                    } else {
                        Toast.makeText(activity, "Can't have 0 clubs.", Toast.LENGTH_SHORT)
                    }
                    if (funValue == true){
                        funValue = false
                    } else {
                        funValue = true
                    }
                    myRef.child("funValue").setValue(funValue)
                }

                //changes the visibility of the second email to false and updates the funValue
                deleteButton2.setOnClickListener(){
                    val clubUid = clubList[((currentClub-(currentClub%3))+2)-1]
                    if ((currentClub+1)-1 != 0) {
                        myRef.child("emailNumber$clubUid").child("visible").setValue(false)
                    } else {
                        Toast.makeText(activity, "Can't have 0 clubs.", Toast.LENGTH_SHORT)
                    }
                    if (funValue == true){
                        funValue = false
                    } else {
                        funValue = true
                    }
                    myRef.child("funValue").setValue(funValue)
                }

                //changes the visibility of the third email to false and updates the funValue
                deleteButton3.setOnClickListener(){
                    val clubUid = clubList[currentClub]
                    if ((currentClub+1)-1 != 0) {
                        myRef.child("emailNumber$clubUid").child("visible").setValue(false)
                    } else {
                        Toast.makeText(activity, "Can't have 0 clubs.", Toast.LENGTH_SHORT)
                    }
                    if (funValue == true){
                        funValue = false
                    } else {
                        funValue = true
                    }
                    myRef.child("funValue").setValue(funValue)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    activity,
                    "Failed to read club data. Check your network connection or restart the app.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.w("joe", "Failed to read value.", error.toException())
            }
        })

        //clears the 4 sub-attributes of a email
        fun clearScreen(clubName: TextView, roomNumber: TextView, meetingTime: TextView, teacherName: TextView) {
            clubName.text = " "
            roomNumber.text = " "
            meetingTime.text = " "
            teacherName.text = " "
        }

        //Displays and get the 4 sub-attributes of an email.
        fun getValueFromDatabase(uidNumber: Int, clubName: TextView, roomNumber: TextView, meetingTime: TextView, teacherName: TextView){
            val databaseSearchReference = myRef.child("emailNumber$uidNumber")
            val clubNameDB = databaseSearchReference.child("class")
            val meetingTimeDB = databaseSearchReference.child("room")
            val room = databaseSearchReference.child("email")
            val teacherNameDB = databaseSearchReference.child("teacherName")
            clubNameDB.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = dataSnapshot.getValue<String>()
                    if (value == null) {
                        clubName.text = " "
                    } else {
                        clubName.text = " $value"
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
                        meetingTime.text = " $value"
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
                        roomNumber.text = " $value"
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
                        teacherName.text = " $value"
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

        //This function is called to update a certain amount of emails.
        //Passing a 1 results in 3 emails being displayed, 2 2, and 1 3.
        fun getVals(num:Int){
            clearScreen(etclubName1, etroomNumber1, etmeetingTime1, etteacherName1)
            clearScreen(etclubName2, etroomNumber2, etmeetingTime2, etteacherName2)
            clearScreen(etclubName3, etroomNumber3, etmeetingTime3, etteacherName3)
            saveButton1.isClickable = true
            saveButton2.isClickable = true
            saveButton3.isClickable = true
            deleteButton1.isClickable = true
            deleteButton2.isClickable = true
            deleteButton3.isClickable = true
            if (num == 1){
                getValueFromDatabase(clubList[currentClub-2], etclubName1, etroomNumber1, etmeetingTime1, etteacherName1)
                getValueFromDatabase(clubList[currentClub-1], etclubName2, etroomNumber2, etmeetingTime2, etteacherName2)
                getValueFromDatabase(clubList[currentClub], etclubName3, etroomNumber3, etmeetingTime3, etteacherName3)
            } else if (num == 2){
                getValueFromDatabase(clubList[currentClub-1], etclubName1, etroomNumber1, etmeetingTime1, etteacherName1)
                getValueFromDatabase(clubList[currentClub], etclubName2, etroomNumber2, etmeetingTime2, etteacherName2)
                clearScreen(etclubName3, etroomNumber3, etmeetingTime3, etteacherName3)
                saveButton3.isClickable = false
                deleteButton3.isClickable = false
            } else {
                getValueFromDatabase(clubList[currentClub], etclubName1, etroomNumber1, etmeetingTime1, etteacherName1)
                clearScreen(etclubName2, etroomNumber2, etmeetingTime2, etteacherName2)
                clearScreen(etclubName3, etroomNumber3, etmeetingTime3, etteacherName3)
                saveButton2.isClickable = false
                saveButton3.isClickable = false
                deleteButton2.isClickable = false
                deleteButton3.isClickable = false
            }
        }

        //Creates a listener for change in the funValue.
        databaseListener = myRef.child("funValue").addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onDataChange(myRefSnapshot: DataSnapshot) {
                    myRef.addListenerForSingleValueEvent(object: ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            //the code below creates a local copy of the database and filters it into variables.
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

                            //The code below determines how many clubs to display, if any.
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
                                    saveButton1.isClickable = false
                                    saveButton2.isClickable = false
                                    saveButton3.isClickable = false
                                    deleteButton1.isClickable = false
                                    deleteButton2.isClickable = false
                                    deleteButton3.isClickable = false
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
        //updates the currenClub and displays the next emails in the list.
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

        //updates currentClub and displays the previous emails in the list.
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