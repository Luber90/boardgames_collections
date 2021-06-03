package com.example.bgc

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHandler(context: Context, name: String?,
                  factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context,
                    DATABASE_NAME, factory, DATABASE_VERSION)  {
    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "gamesDB.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val create = ("create table test(cos varchar2);")
        db.execSQL(create)
        val values = ContentValues()
        values.put("cos", "tescik")
        db.insert("test", null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists test")
        onCreate(db)
    }

    fun getTest():String{
        val query = "select * from test"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        var result = ""
        if(cursor.moveToFirst()){
            result = cursor.getString(0)
            cursor.close()
        }
        db.close()
        return result
    }

}