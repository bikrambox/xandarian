package com.example.xandar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

var clickCount = 0

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)



        button.setOnClickListener {
            val intent = Intent(this, Main2Activity::class.java)
            // start your next activity
            startActivity(intent)
        }


        button2.setOnClickListener {

            if (clickCount++>4) {

                val intent = Intent(this, Special::class.java)
                // start your next activity
                startActivity(intent)
            }


        }


    }
}
