package com.fatkus.notepad

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DbManager {
    val dbName="Catatan" // table name on db
    val dbTable="catatanku" // table name on db
    val colID="id" // id column on db
    val colTitle="nama" // judul note
    val colDes="deskripsi" // deskripsi note
    val dbVersion=1 // db version

    val sqlCreateTable="CREATE TABLE IF NOT EXIST "+ dbTable + "("+colID+"INTEGER PRIMARY KEY,"+colTitle+"TEXT,"+colDes+"TEXT);"
    var sqlDB: SQLiteDatabase?=null

    constructor(context: Context){
        var db=DatabaseHelperNotes(context)
        sqlDB=db.writableDatabase
    }

    inner class DatabaseHelperNotes: SQLiteOpenHelper{
        var context:Context?=null
        constructor(context: Context):super(context,dbName,null,dbVersion){
            this.context=context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            Toast.makeText(this.context,"database is created",Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table IF EXIST"+dbTable)
        }
    }
    fun Insert(values: ContentValues):Long{
        val ID=sqlDB!!.insert(dbTable,"",values)
        return ID
    }
    fun Query(projection:Array<String>,selection: String,selectionArgs:Array<String>,sorOrder: String): Cursor{
        val qb=SQLiteQueryBuilder()
        qb.tables=dbTable
        val cursor=qb.query(sqlDB,projection,selection,selectionArgs,null,null,sorOrder)
        return cursor
    }
    fun Delete(selection: String,selectionArgs: Array<String>):Int{
        val count=sqlDB!!.delete(dbTable,selection,selectionArgs)
        return count
    }
    fun Update(values: ContentValues,selection: String,selectionArgs: Array<String>):Int{
        val count=sqlDB!!.update(dbTable,values,selection,selectionArgs)
        return count
    }
}

