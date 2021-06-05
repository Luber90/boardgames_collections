package com.example.bgc

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adding: Button = findViewById(R.id.add)
        val input: EditText = findViewById(R.id.input)
        adding.setOnClickListener(){
            val i = Intent(this, AddActivity::class.java)
            i.putExtra("game", input.getText().toString())
            startActivity(i)
            true
        }
    }
}