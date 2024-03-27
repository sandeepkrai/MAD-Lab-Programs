package com.example.androidroom

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val DATABASE_NAME = "notes.db"
val DATABASE_VERSION = 1
val TABLE_NAME = "notes"
val COL_ID = "id"
val COL_TITLE = "title"
val COL_CONTENT = "content"

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_TITLE TEXT, $COL_CONTENT TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(title: String, content: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_TITLE, title)
        contentValues.put(COL_CONTENT, content)
        val result = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return result
    }

    fun getAllData(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun deleteData(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COL_ID=?", arrayOf(id.toString()))
    }
}

@SuppressLint("Range")
fun Cursor.getStringValue(columnName: String): String {
    return this.getString(this.getColumnIndex(columnName))
}

@SuppressLint("Range")
fun Cursor.getIntValue(columnName: String): Int {
    return this.getInt(this.getColumnIndex(columnName))
}

fun Cursor.moveToLastAndGetStringValue(columnName: String): String {
    this.moveToLast()
    return this.getStringValue(columnName)
}