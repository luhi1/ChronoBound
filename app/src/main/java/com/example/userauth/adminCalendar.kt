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

class adminCalendar : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_calendar, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart(){
        super.onStart()
        val insuBt = requireView().findViewById<Button>(R.id.insuBt)
        val popupLn = requireView().findViewById<LinearLayout>(R.id.popupLn)
        val calenLn = requireView().findViewById<LinearLayout>(R.id.calenLn)
        val bktcBt = requireView().findViewById<Button>(R.id.bktcBt)
        val backBt = requireView().findViewById<Button>(R.id.backBt)
        val nextBt = requireView().findViewById<Button>(R.id.nextBt)
        val mnthTx = requireView().findViewById<TextView>(R.id.mnthTx)
        val pdayTx = requireView().findViewById<TextView>(R.id.pdayTx)
        val predBt = requireView().findViewById<Button>(R.id.predBt)
        val nexdBt = requireView().findViewById<Button>(R.id.nexdBt)
        val inputEd = requireView().findViewById<EditText>(R.id.inputEd)
        val evni1Ed = requireView().findViewById<EditText>(R.id.evni1Ed)
        val desi1Ed = requireView().findViewById<EditText>(R.id.desi1Ed)
        val evni2Ed = requireView().findViewById<EditText>(R.id.evni2Ed)
        val desi2Ed = requireView().findViewById<EditText>(R.id.desi2Ed)
        val evni3Ed = requireView().findViewById<EditText>(R.id.evni3Ed)
        val desi3Ed = requireView().findViewById<EditText>(R.id.desi3Ed)
        val evni4Ed = requireView().findViewById<EditText>(R.id.evni4Ed)
        val desi4Ed = requireView().findViewById<EditText>(R.id.desi4Ed)


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


        val current = LocalDateTime.now()

        var year = Integer.parseInt(current.format(DateTimeFormatter.ofPattern("YYYY")))
        var month = Integer.parseInt(current.format(DateTimeFormatter.ofPattern("MM"))) - 1
        var day = 1
        val monthlist = arrayOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
        val shiftList = arrayOf(0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4)
        var mLenList = arrayOf(31,28,31,30,31,30,31,31,30,31,30,31)

        fun hideNFixDayNums() {
            mnthTx.text = monthlist[month] + " " + year.toString()

            for (i in dayRlList) {
                //i.text = ""
                i.setBackground(activity?.let { ContextCompat.getDrawable(it, R.drawable.dark_blue_square) })

                i.isVisible = false
            }
            for (i in dayBtList) {
                i.text = ""

                //i.isVisible = false
                //i.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
                //i.background = ContextCompat.getDrawable(this, R.drawable.teal_square)
            }


            if (year % 4 == 0){
                mLenList[1] = 29
            }

            var year2 = year

            if (month < 2) {
                year2 -= 1
            }

            var shift = (year2 + year2/4 - year2/100 + year2/400 + shiftList[month] + 1) % 7

            var monthlength = mLenList[month]

            for (i in 1..monthlength) {
                dayRlList[(i + shift - 1)].isVisible = true
                dayBtList[(i + shift - 1)].isVisible = true

                dayBtList[(i + shift - 1)].text = i.toString()
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
        fun getsingleValueFromDatabase(path: List<String>, reference: String, tvid: TextView){
            var myRef = FirebaseDatabase.getInstance().getReference(reference)

            for (i in path.indices){
                myRef = myRef.child(path[i]);
            }

            //the code below was taken from the firebase documentation
            myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    val value = dataSnapshot.getValue<String>()
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
        fun getValueFromDatabase(path: List<String>, reference: String, tvid: TextView){
            var myRef = FirebaseDatabase.getInstance().getReference(reference)

            for (i in path.indices){
                myRef = myRef.child(path[i]);
            }

            //the code below was taken from the firebase documentation
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    val value = dataSnapshot.getValue<String>()
                    if (value == null){
                        tvid.text = ""
                    } else {
                        tvid.text = HtmlCompat.fromHtml("$value", HtmlCompat.FROM_HTML_MODE_LEGACY)
                        tvid.setMovementMethod(LinkMovementMethod.getInstance());
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Toast.makeText(activity, "joe" + "Failed to read value." + error.toException(), Toast.LENGTH_LONG).show()
                }
            })
        }
        fun addtoDatabase(path: List<String>, reference: String, inpu: String){
            var myRef = FirebaseDatabase.getInstance().getReference(reference)

            for (i in path.indices){
                myRef = myRef.child(path[i])
            }

            //the code below was taken from the firebase documentation
            myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    myRef.setValue(inpu)
                    Toast.makeText(activity, "events added", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Toast.makeText(activity, "joe" + "Failed to write value." + error.toException(), Toast.LENGTH_LONG).show()
                }
            })
        }


        popupLn.isVisible = false


        hideNFixDayNums()


        backBt.setOnClickListener {
            if (month == 0){
                year -= 1
            }

            month -= 1

            while (month < 0){
                month += 12
            }

            month %= 12

            mnthTx.text = monthlist[month]

            hideNFixDayNums()
        }


        nextBt.setOnClickListener {
            if (month == 11){
                year += 1
            }

            month = (month + 1) % 12

            mnthTx.text = monthlist[month]

            hideNFixDayNums()
        }

        fun fillintext(){
            getsingleValueFromDatabase(listOf(monthlist[month], day.toString(), "note"), "calActivity", inputEd)

            getsingleValueFromDatabase(listOf(monthlist[month], day.toString(), "event1", "name"), "calActivity", evni1Ed)
            getsingleValueFromDatabase(listOf(monthlist[month], day.toString(), "event1", "description"), "calActivity", desi1Ed)

            getsingleValueFromDatabase(listOf(monthlist[month], day.toString(), "event2", "name"), "calActivity", evni2Ed)
            getsingleValueFromDatabase(listOf(monthlist[month], day.toString(), "event2", "description"), "calActivity", desi2Ed)

            getsingleValueFromDatabase(listOf(monthlist[month], day.toString(), "event3", "name"), "calActivity", evni3Ed)
            getsingleValueFromDatabase(listOf(monthlist[month], day.toString(), "event3", "description"), "calActivity", desi3Ed)

            getsingleValueFromDatabase(listOf(monthlist[month], day.toString(), "event4", "name"), "calActivity", evni4Ed)
            getsingleValueFromDatabase(listOf(monthlist[month], day.toString(), "event4", "description"), "calActivity", desi4Ed)


            pdayTx.text = monthlist[month] + " " + day.toString()
        }

        for (i in dayBtList) {

            i.setOnClickListener {
                popupLn.isVisible = true

                calenLn.isVisible = false

                day = Integer.parseInt(i.text.toString())

                fillintext()

            }
        }



        insuBt.setOnClickListener {
            addtoDatabase(listOf(monthlist[month], day.toString(), "note"), "calActivity", inputEd.getText().toString())

            addtoDatabase(listOf(monthlist[month], day.toString(), "event1", "name"), "calActivity", evni1Ed.getText().toString())
            addtoDatabase(listOf(monthlist[month], day.toString(), "event1", "description"), "calActivity", desi1Ed.getText().toString())

            addtoDatabase(listOf(monthlist[month], day.toString(), "event2", "name"), "calActivity", evni2Ed.getText().toString())
            addtoDatabase(listOf(monthlist[month], day.toString(), "event2", "description"), "calActivity", desi2Ed.getText().toString())

            addtoDatabase(listOf(monthlist[month], day.toString(), "event3", "name"), "calActivity", evni3Ed.getText().toString())
            addtoDatabase(listOf(monthlist[month], day.toString(), "event3", "description"), "calActivity", desi3Ed.getText().toString())

            addtoDatabase(listOf(monthlist[month], day.toString(), "event4", "name"), "calActivity", evni4Ed.getText().toString())
            addtoDatabase(listOf(monthlist[month], day.toString(), "event4", "description"), "calActivity", desi4Ed.getText().toString())

        }

        predBt.setOnClickListener {
            if (day == 1){
                if (month == 0){
                    year -= 1
                }
                month -= 1
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

            fillintext()
        }


        nexdBt.setOnClickListener {
            if (day == mLenList[month]){
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

            fillintext()
        }


        bktcBt.setOnClickListener {
            popupLn.isVisible = false

            calenLn.isVisible = true

            pdayTx.text = "M D"
        }

    }
}