package com.example.userauth

import android.os.Build
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class calendarFragment : Fragment() {
    private var myRef = FirebaseDatabase.getInstance().getReference("calActivity")
    private lateinit var myListener:ValueEventListener
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart(){
        super.onStart()

        //adds all the different elements to the code
        val ppupTx = requireView().findViewById<TextView>(R.id.ppupTx)
        val popupV = requireView().findViewById<LinearLayout>(R.id.popupV)
        val calenV = requireView().findViewById<LinearLayout>(R.id.calenV)
        val bktcBt = requireView().findViewById<Button>(R.id.bktcBt)
        val backBt = requireView().findViewById<Button>(R.id.backBt)
        val nextBt = requireView().findViewById<Button>(R.id.nextBt)
        val mnthTx = requireView().findViewById<TextView>(R.id.mnthTx)
        val pdayTx = requireView().findViewById<TextView>(R.id.pdayTx)
        val predBt = requireView().findViewById<Button>(R.id.predBt)
        val nexdBt = requireView().findViewById<Button>(R.id.nexdBt)
        val des1Tx = requireView().findViewById<TextView>(R.id.des1Tx)
        val des2Tx = requireView().findViewById<TextView>(R.id.des2Tx)
        val des3Tx = requireView().findViewById<TextView>(R.id.des3Tx)
        val des4Tx = requireView().findViewById<TextView>(R.id.des4Tx)
        val evn1Tx = requireView().findViewById<TextView>(R.id.evn1Tx)
        val evn2Tx = requireView().findViewById<TextView>(R.id.evn2Tx)
        val evn3Tx = requireView().findViewById<TextView>(R.id.evn3Tx)
        val evn4Tx = requireView().findViewById<TextView>(R.id.evn4Tx)

        //adds specifically all of the buttons of the days of the month
        val dayBtList = arrayOf(
            requireView().findViewById<Button>(R.id.day1Bt),
            requireView().findViewById<Button>(R.id.day2Bt),
            requireView().findViewById<Button>(R.id.day3Bt),
            requireView().findViewById<Button>(R.id.day4Bt),
            requireView().findViewById<Button>(R.id.day5Bt),
            requireView().findViewById<Button>(R.id.day6Bt),
            requireView().findViewById<Button>(R.id.day7Bt),
            requireView().findViewById<Button>(R.id.day8Bt),
            requireView().findViewById<Button>(R.id.day9Bt),
            requireView().findViewById<Button>(R.id.day10Bt),
            requireView().findViewById<Button>(R.id.day11Bt),
            requireView().findViewById<Button>(R.id.day12Bt),
            requireView().findViewById<Button>(R.id.day13Bt),
            requireView().findViewById<Button>(R.id.day14Bt),
            requireView().findViewById<Button>(R.id.day15Bt),
            requireView().findViewById<Button>(R.id.day16Bt),
            requireView().findViewById<Button>(R.id.day17Bt),
            requireView().findViewById<Button>(R.id.day18Bt),
            requireView().findViewById<Button>(R.id.day19Bt),
            requireView().findViewById<Button>(R.id.day20Bt),
            requireView().findViewById<Button>(R.id.day21Bt),
            requireView().findViewById<Button>(R.id.day22Bt),
            requireView().findViewById<Button>(R.id.day23Bt),
            requireView().findViewById<Button>(R.id.day24Bt),
            requireView().findViewById<Button>(R.id.day25Bt),
            requireView().findViewById<Button>(R.id.day26Bt),
            requireView().findViewById<Button>(R.id.day27Bt),
            requireView().findViewById<Button>(R.id.day28Bt),
            requireView().findViewById<Button>(R.id.day29Bt),
            requireView().findViewById<Button>(R.id.day30Bt),
            requireView().findViewById<Button>(R.id.day31Bt),
            requireView().findViewById<Button>(R.id.day32Bt),
            requireView().findViewById<Button>(R.id.day33Bt),
            requireView().findViewById<Button>(R.id.day34Bt),
            requireView().findViewById<Button>(R.id.day35Bt),
            requireView().findViewById<Button>(R.id.day36Bt),
            requireView().findViewById<Button>(R.id.day37Bt),
            requireView().findViewById<Button>(R.id.day38Bt),
            requireView().findViewById<Button>(R.id.day39Bt),
            requireView().findViewById<Button>(R.id.day40Bt),
            requireView().findViewById<Button>(R.id.day41Bt),
            requireView().findViewById<Button>(R.id.day42Bt)
        )

        //adds all the buttons relativelayouts (allows for better background)
        val dayRlList = arrayOf(
            requireView().findViewById<RelativeLayout>(R.id.day1Rl),
            requireView().findViewById<RelativeLayout>(R.id.day2Rl),
            requireView().findViewById<RelativeLayout>(R.id.day3Rl),
            requireView().findViewById<RelativeLayout>(R.id.day4Rl),
            requireView().findViewById<RelativeLayout>(R.id.day5Rl),
            requireView().findViewById<RelativeLayout>(R.id.day6Rl),
            requireView().findViewById<RelativeLayout>(R.id.day7Rl),
            requireView().findViewById<RelativeLayout>(R.id.day8Rl),
            requireView().findViewById<RelativeLayout>(R.id.day9Rl),
            requireView().findViewById<RelativeLayout>(R.id.day10Rl),
            requireView().findViewById<RelativeLayout>(R.id.day11Rl),
            requireView().findViewById<RelativeLayout>(R.id.day12Rl),
            requireView().findViewById<RelativeLayout>(R.id.day13Rl),
            requireView().findViewById<RelativeLayout>(R.id.day14Rl),
            requireView().findViewById<RelativeLayout>(R.id.day15Rl),
            requireView().findViewById<RelativeLayout>(R.id.day16Rl),
            requireView().findViewById<RelativeLayout>(R.id.day17Rl),
            requireView().findViewById<RelativeLayout>(R.id.day18Rl),
            requireView().findViewById<RelativeLayout>(R.id.day19Rl),
            requireView().findViewById<RelativeLayout>(R.id.day20Rl),
            requireView().findViewById<RelativeLayout>(R.id.day21Rl),
            requireView().findViewById<RelativeLayout>(R.id.day22Rl),
            requireView().findViewById<RelativeLayout>(R.id.day23Rl),
            requireView().findViewById<RelativeLayout>(R.id.day24Rl),
            requireView().findViewById<RelativeLayout>(R.id.day25Rl),
            requireView().findViewById<RelativeLayout>(R.id.day26Rl),
            requireView().findViewById<RelativeLayout>(R.id.day27Rl),
            requireView().findViewById<RelativeLayout>(R.id.day28Rl),
            requireView().findViewById<RelativeLayout>(R.id.day29Rl),
            requireView().findViewById<RelativeLayout>(R.id.day30Rl),
            requireView().findViewById<RelativeLayout>(R.id.day31Rl),
            requireView().findViewById<RelativeLayout>(R.id.day32Rl),
            requireView().findViewById<RelativeLayout>(R.id.day33Rl),
            requireView().findViewById<RelativeLayout>(R.id.day34Rl),
            requireView().findViewById<RelativeLayout>(R.id.day35Rl),
            requireView().findViewById<RelativeLayout>(R.id.day36Rl),
            requireView().findViewById<RelativeLayout>(R.id.day37Rl),
            requireView().findViewById<RelativeLayout>(R.id.day38Rl),
            requireView().findViewById<RelativeLayout>(R.id.day39Rl),
            requireView().findViewById<RelativeLayout>(R.id.day40Rl),
            requireView().findViewById<RelativeLayout>(R.id.day41Rl),
            requireView().findViewById<RelativeLayout>(R.id.day42Rl)
        )



        //used to get help get the time
        val current = LocalDateTime.now()

        var year = Integer.parseInt(current.format(DateTimeFormatter.ofPattern("YYYY")))
        var month = Integer.parseInt(current.format(DateTimeFormatter.ofPattern("MM"))) - 1
        var day = Integer.parseInt(current.format(DateTimeFormatter.ofPattern("dd")))
        val monthlist = arrayOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
        //used to see how far we need to shift the days in the calendar for the month to line up w/ week
        val shiftList = arrayOf(0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4)
        var mLenList = arrayOf(31,28,31,30,31,30,31,31,30,31,30,31)
        
        //essential function that gives every day its appropriate number, and hides days that aren't used
        fun hideNFixDayNums() {
            mnthTx.text = monthlist[month] + " " + year.toString()
            //traverses day relativelayouts in order to give them their appropriate background
            for (i in dayRlList) {
                //i.text = ""
                i.setBackground(activity?.let { ContextCompat.getDrawable(it, R.drawable.dark_blue_square) })

                i.isVisible = false
            }
            //clears day buttons text so no cross-day interference occurs
            for (i in dayBtList) {
                i.text = ""
                //i.setBackground(ContextCompat.getDrawable(this, R.color.transparent))

                //i.isVisible = false
            }


            //includes leap day
            if (year % 4 == 0){
                mLenList[1] = 29
            }

            //temporary variable for calculating the shift
            var year2 = year

            if (month < 2) {
                year2 -= 1
            }
            var shift = (year2 + year2/4 - year2/100 + year2/400 + shiftList[month] + 1) % 7

            var monthlength = mLenList[month]
            //traverses to set the correct days visible and give them the correct number
            for (i in 1..monthlength) {
                dayRlList[(i + shift - 1)].isVisible = true
                dayBtList[(i + shift - 1)].isVisible = true

                dayBtList[(i + shift - 1)].text = i.toString()
                //colors current day
                if (i == Integer.parseInt(current.format(DateTimeFormatter.ofPattern("dd"))) && month == Integer.parseInt(current.format(
                        DateTimeFormatter.ofPattern("MM"))) - 1) {
                    dayRlList[(i + shift - 1)].setBackground(activity?.let {
                        ContextCompat.getDrawable(
                            it, R.drawable.teal_square)
                    })
                    //dayBtList[(i + shift - 1)].setBackgroundColor(Color.BLUE)

                }
            }
        }

        //code for getting value from firebase
        fun getValueFromDatabase(path: List<String>, reference: String, tvid: TextView){

            myRef = FirebaseDatabase.getInstance().getReference(reference)

            for (i in path.indices){
                myRef = myRef.child(path[i]);
            }

            //the code below was taken from the firebase documentation
            myListener = myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    val value = dataSnapshot.getValue<String>()
                    //taken code segment end
                    if (value == null){
                        tvid.text = ""
                    } else if (tvid == ppupTx) {
                        tvid.text = HtmlCompat.fromHtml("$value", HtmlCompat.FROM_HTML_MODE_LEGACY)
                        tvid.movementMethod = LinkMovementMethod.getInstance()
                    } else {
                        tvid.text = "$value"
                    }}


                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Toast.makeText(activity, "joe" + "Failed to read value." + error.toException(), Toast.LENGTH_LONG).show()
                }
            })
        }

        //makes daily view/pop up invisible
        popupV.isVisible = false


        hideNFixDayNums()


        backBt.setOnClickListener {
            //goes to previous year if on first month
            if (month == 0){
                year -= 1
            }

            month -= 1
            
            //allows negative numbers to work
            while (month < 0){
                month += 12
            }

            month %= 12

            mnthTx.text = monthlist[month]

            hideNFixDayNums()
        }


        nextBt.setOnClickListener {
            //goes to previous year if on first month
            if (month == 11){
                year += 1
            }

            month = (month + 1) % 12

            mnthTx.text = monthlist[month]

            hideNFixDayNums()
        }

        //fills in daily view events
        fun fillintext(){
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "note"), "calActivity", ppupTx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event1", "description"), "calActivity", des1Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event1", "name"), "calActivity", evn1Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event2", "description"), "calActivity", des2Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event2", "name"), "calActivity", evn2Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event3", "description"), "calActivity", des3Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event3", "name"), "calActivity", evn3Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event4", "description"), "calActivity", des4Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event4", "name"), "calActivity", evn4Tx)

            pdayTx.text = monthlist[month] + " " + day.toString()
        }
        //sets up all the listeners for day buttons
        for (i in dayBtList) {

            i.setOnClickListener {
                popupV.isVisible = true

                calenV.isVisible = false

                day = Integer.parseInt(i.text.toString())

                fillintext()





            }
        }

        //goes to day before
        predBt.setOnClickListener {
            //if its the first day go to previous month
            if (day == 1){
                //if its the first month go to previous year
                if (month == 0){
                    year -= 1
                }
                month -= 1
                //allows negative numbers
                while (month < 0){
                    month += 12
                }
                month %= 12
                mnthTx.text = monthlist[month]
                hideNFixDayNums()

                day = mLenList[month]

            }else{
                day -= 1
            }

            getValueFromDatabase(listOf(monthlist[month], day.toString(), "note"), "calActivity", ppupTx)

            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event1", "description"), "calActivity", des1Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event1", "name"), "calActivity", evn1Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event2", "description"), "calActivity", des2Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event2", "name"), "calActivity", evn2Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event3", "description"), "calActivity", des3Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event3", "name"), "calActivity", evn3Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event4", "description"), "calActivity", des4Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event4", "name"), "calActivity", evn4Tx)

            pdayTx.text = monthlist[month] + " " + day.toString()
        }


        nexdBt.setOnClickListener {
            //if its the last day go to next month
            if (day == mLenList[month]){
                //if its the last month go to next year
                if (month == 11){
                    year += 1
                }
                month = (month + 1) % 12
                mnthTx.text = monthlist[month]
                hideNFixDayNums()

                day = 1

            }else{
                day += 1
            }

            getValueFromDatabase(listOf(monthlist[month], day.toString(), "note"), "calActivity", ppupTx)

            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event1", "description"), "calActivity", des1Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event1", "name"), "calActivity", evn1Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event2", "description"), "calActivity", des2Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event2", "name"), "calActivity", evn2Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event3", "description"), "calActivity", des3Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event3", "name"), "calActivity", evn3Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event4", "description"), "calActivity", des4Tx)
            getValueFromDatabase(listOf(monthlist[month], day.toString(), "event4", "name"), "calActivity", evn4Tx)

            pdayTx.text = monthlist[month] + " " + day.toString()
        }

        //goes back to monthly view
        bktcBt.setOnClickListener {
            popupV.isVisible = false

            calenV.isVisible = true

            ppupTx.text = ""

            pdayTx.text = "M D"
        }

    }

    override fun onStop(){
        super.onStop()
        myRef.removeEventListener(myListener)
    }
}
