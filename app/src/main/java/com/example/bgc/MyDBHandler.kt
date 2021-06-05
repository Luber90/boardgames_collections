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
        val create = ("create table test(name varchar2, orgname varchar2, id bigint, year varchar2, desc varchar2);")
        db.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists test")
        onCreate(db)
    }

    fun addGame(id: Long, name: String, orgname: String, year: String, desc: String){
        val values = ContentValues()
        values.put("name", name)
        values.put("id", id)
        values.put("orgname", orgname)
        values.put("year", year)
        values.put("desc", desc)
        val db = this.writableDatabase
        db.insert("test", null, values)
        db.close()
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