package com.example.bgc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adding: Button = findViewById(R.id.add)
        adding.setOnClickListener(){
            val i = Intent(this, AddActivity::class.java)
            startActivity(i)
            true
        }
    }
}