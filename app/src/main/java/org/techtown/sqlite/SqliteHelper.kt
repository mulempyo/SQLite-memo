package org.techtown.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(context: Context, name:String,version:Int): SQLiteOpenHelper(context,name,null,version) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val create = "create table memo (" +
                "no integer primar key, " +
                "content text, " +
                "datetime integer" +
                ")"
        p0?.execSQL(create)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
    fun insertMemo(memo: Memo){
        val values = ContentValues()
        values.put("content",memo.content)
        values.put("datetime",memo.datetime)

        val wd = writableDatabase
        wd.insert("memo",null,values)
        wd.close()
    }
    @SuppressLint("Range")
    fun selectMemo():MutableList<Memo>{
        val list = mutableListOf<Memo>()

        val select = "select * from memo"
        val rd = readableDatabase
        val cursor = rd.rawQuery(select,null)
        while(cursor.moveToNext()) {
                    val no = cursor.getLong(cursor.getColumnIndex("no")) /* 컬럼에서 값 꺼내기 */
                    val content = cursor.getString(cursor.getColumnIndex("content")) //컬럼에서 값 꺼내기
                    val datetime = cursor.getLong(cursor.getColumnIndex("datetime")) //컬럼에서 값 꺼내기
                    val memo = Memo(no,content,datetime)

                    list.add(Memo(no, content, datetime))
                }

        cursor.close()
        rd.close()
        return list
    }

    fun updateMemo(memo:Memo){
        val values = ContentValues()
        values.put("content",memo.content)
        values.put("datetime",memo.datetime)

        val wd = writableDatabase
        wd.update("memo",values,"no = ${memo.no}",null)
        wd.close()
    }

    fun deleteMemo(memo:Memo){
        val delete = "delete from memo where no = ${memo.no}"
        val db = writableDatabase
        db.execSQL(delete)
        db.close()
    }
}

data class Memo(var no : Long?,var content:String,var datetime:Long)