package com.example.bgc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AddDetActivity: AppCompatActivity() {
    private var name: String? = ""
    private var id: Long? = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras ?: return
        name = extras.getString("name")
        id = extras.getLong("id")
        setContentView(R.layout.activity_adddet)

        super.onCreate(savedInstanceState)

    }
}