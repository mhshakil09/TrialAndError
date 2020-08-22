package com.example.trialanderror

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    var counter = 0
    var counterAgain = 0
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var uniqueID =  UUID.randomUUID().toString()
        Toast.makeText(this, uniqueID, Toast.LENGTH_SHORT).show()

//        auth = Firebase.auth

//    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
        btnCounter.text = "0"
        tvCounter.text = "0"

//    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
        etInput.hint = Calendar.getInstance().time.toString()
        checkAndOutput()

//    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
        tvDisplay.setOnClickListener(){
            checkAndOutput()
        }

//    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
        btnCounter.setOnClickListener(){
            countIncrease()
        }

//    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
        btnPlus.setOnClickListener(){
//            btnPlus.isClickable = false
            btnPlus.isEnabled = false
            btnMinus.isEnabled = false
            counterAgain+=1
            tvCounter(1)
//            tvCounter.text = counterAgain.toString()
        }

//    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
        btnMinus.setOnClickListener(){
//            btnMinus.isClickable = false
            btnPlus.isEnabled = false
            btnMinus.isEnabled = false
            counterAgain-=1
            tvCounter(0)
//            tvCounter.text = counterAgain.toString()
        }
//    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
        btnNext.setOnClickListener(){
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun countIncrease(){
        counter+=1
        smallAnimation()
//        btnCounter.text = counter.toString()  // normal output
//        Toast.makeText(this, ""+counter, Toast.LENGTH_SHORT).show()

//        val animation = AnimationUtils.loadAnimation(this, R.anim.flip) // trying to animate from anim.xml
//        btnCounter.startAnimation(animation)
    }


//    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//                  checking whether the Text View is displaying anything or not
    private fun checkAndOutput(){
        if (tvDisplay.text.isBlank()){
            tvDisplay.text = "Click me!"
        }
        else if(etInput.text.isBlank()){
            tvDisplay.text = "Click me!"
            Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show()
        }
        else{
            tvDisplay.text = "" + etInput.text + " click me"
            Toast.makeText(this, "Displayed " + etInput.text, Toast.LENGTH_SHORT).show()
        }
    }

//    ----------------------------------------------------------------------------------------------
//                                 *******looks like normal flip******
//    private fun smallAnimation(){
//        btnCounter.animate().apply {
//            duration = 200
//            rotationXBy(180f)
//            translationYBy(-200f)
//            rotationBy(-360f)
//        }.withEndAction {
//                btnCounter.animate().apply {
//                    btnCounter.text = counter.toString()
//                    duration = 200
//                    rotationXBy(180f)
//                    translationYBy(200f)
//                }
//        }.start()
//    }

//    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//                          ********almost looks like coin flip********
    private fun smallAnimation(){
        btnCounter.animate().apply {
            duration = 200
            rotationXBy(90f)
            translationYBy(-200f)
        }.withStartAction {
            btnCounter.animate().apply {
                duration = 2000
                rotationYBy(-3600f)
                rotationBy(-3600f)
            }
        }.withEndAction {
            btnCounter.animate().apply {
                rotationXBy(-90f)
                translationYBy(200f)
                if ( counter == 1){
                    btnCounter.text = "head"
                }
                else if (counter == 2){
                    btnCounter.text = "tail"
                }
                else{
                    btnCounter.text = counter.toString()
                }
            }
        }.start()
    }
//    ----------------------------------------------------------------------------------------------


//    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//                      Finally almost work
    private fun tvCounter( temp:Int ){
        tvCounter.animate().apply {
            duration = 200
            rotationXBy(180f)
            translationY(-200f)
            withStartAction {
                if (temp == 1) {
                    duration = 400
                    rotationBy(-360f)
                }
                else{
                    duration = 400
                    rotationBy(360f)
                }

            }
        }.withEndAction {
            tvCounter.animate().apply {
                tvCounter.text = counterAgain.toString()
                duration = 200
                rotationXBy(180f)
                translationY(000f)
                withEndAction {
//                    btnPlus.isClickable = true
                    btnPlus.isEnabled = true
//                    btnMinus.isClickable = false
                    btnMinus.isEnabled = true
                }
            }
        }.start()
    }
//    ----------------------------------------------------------------------------------------------

}