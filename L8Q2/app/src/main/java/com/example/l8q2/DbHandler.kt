package com.example.l8q2


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val DATABASE_NAME = "contacts.db"
val DATABASE_VERSION = 1
val TABLE_NAME = "contacts"
val COL_ID = "id"
val COL_NAME = "name"
val COL_PHONENO = "phoneNo"
val COL_EMAIL = "email"

data class Note(val id: Int, val name: String, val phoneNo: String, val email: String)

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAME TEXT, $COL_PHONENO TEXT, $COL_EMAIL)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(name: String, phoneNo: String, email: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, name)
        contentValues.put(COL_PHONENO, phoneNo)
        contentValues.put(COL_EMAIL, email)
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

    fun updateData(id: Int, name: String, phoneNo: String, email: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL_NAME, name)
            put(COL_PHONENO, phoneNo)
            put(COL_EMAIL, email)
        }
        return db.update(TABLE_NAME, contentValues, "$COL_ID=?", arrayOf(id.toString()))
    }

    fun getNoteById(id: Int): Note? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COL_ID, COL_NAME, COL_PHONENO, COL_EMAIL),
            "$COL_ID=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var note: Note? = null
        cursor.use { c ->
            if (c.moveToFirst()) {
                val name = c.getString(c.getColumnIndex(COL_NAME))
                val phoneNo = c.getString(c.getColumnIndex(COL_PHONENO))
                val email = c.getString(c.getColumnIndex(COL_EMAIL))
                note = Note(id, name, phoneNo, email)
            }
        }
        return note
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