package com.example.trialanderror

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.activity_second.btnNext
import java.lang.StringBuilder

class SecondActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance().getReference("userInformation")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


//        database.setValue("hola is testing")


//    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
        btnSave.setOnClickListener() {
            var etName = etName.text.toString()
            var etAddress = etAddress.text.toString()
            var etDay = etDay.text.toString()
            var etMonth = etMonth.text.toString()

            // Set data ---------------------------------------------------------------------------------------------
            // formatting data to send it in Firebase
            var userId = database.push().key.toString()
            var userTemp = userDetailsClass(etName, etAddress, etDay, etMonth)

            // sending the formatted data to Firebase
            database.child(etName).setValue(userTemp)
        }

        // Get data --------------------------------------------------------------------------------------------------
        //
        var getData = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb = StringBuilder()

                // just a random checking
                for (i in snapshot.children) {
                    if (i.key.toString() == "asd"){
                        var tempName = i.child("userName").getValue()
                        var tempAddress = i.child("userAddress").getValue()
                        var tempDay = i.child("userDay").getValue()
                        var tempMonth = i.child("userMonth").getValue()

                        sb.append("${i.key} $tempName $tempAddress $tempDay $tempMonth \n")
                    }
                }

                // printing all data
                for (i in snapshot.children) {
                    var tempName = i.child("userName").getValue()
                    var tempAddress = i.child("userAddress").getValue()
                    var tempDay = i.child("userDay").getValue()
                    var tempMonth = i.child("userMonth").getValue()

                    sb.append("${i.key} $tempName $tempAddress $tempDay $tempMonth \n")
                }

                // displaying all data from stringBuilder
                tvResult.setText(sb)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        database.addValueEventListener(getData)
        database.addListenerForSingleValueEvent(getData)


//    ----------------------------------------------------------------------------------------------

//    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
        btnSearch.setOnClickListener {
            var etFind = etFind.text.toString()

            search(etFind)
        }


//    ----------------------------------------------------------------------------------------------

//    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
        btnBack.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
//    ----------------------------------------------------------------------------------------------
        btnNext.setOnClickListener(){
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
            finish()
        }
        

    }

//    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//      function for user searching
    fun search (etFind: String){
        var getData = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                tvSearch.text = "searching..."
                var sb = StringBuilder()
                var temp = 0
                for (i in snapshot.children){
                    if (i.key.toString() == etFind){
                        var tempName = i.child("userName").getValue()
                        var tempAddress = i.child("userAddress").getValue()
                        var tempDay = i.child("userDay").getValue()
                        var tempMonth = i.child("userMonth").getValue()

                        sb.append("${i.key} $tempName $tempAddress $tempDay $tempMonth \n")
                        temp = 1
                    }
                }
                if (temp == 1)
                    tvSearch.text = "" + sb
                else
                    tvSearch.text = "no result"
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }

        database.addValueEventListener(getData)
        database.addListenerForSingleValueEvent(getData)
    }
}