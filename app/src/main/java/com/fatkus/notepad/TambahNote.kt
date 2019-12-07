package com.fatkus.notepad

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_tambah_note.*
import java.lang.Exception

class TambahNote : AppCompatActivity() {

    val dbTable="catatanku"
    var id=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_note)
        try {
            var bundle: Bundle = intent.extras!!
            id=bundle.getInt("id",0)
            if(id!=0){
                etTitle.setText(bundle?.getString("nama"))
                etDes.setText(bundle?.getString("deskripsi"))
            }
        }catch (ex:Exception){}
    }
    fun btnAdd(view: View){
        var dbManager=DbManager(this)

        var values= ContentValues()
        values.put("nama",etTitle.text.toString())
        values.put("deskripsi",etDes.text.toString())

        if(id==0){
            val ID=dbManager.Insert(values)
            if(ID>0){
                Toast.makeText(this,"Catatan Berhasil Ditambahkan", Toast.LENGTH_LONG).show()
                finish()
            }else{
                Toast.makeText(this,"Tidak Bisa Menambahkan Catatan", Toast.LENGTH_LONG).show()
                finish()
            }
        }else{
            var selectionArs= arrayOf(id.toString())
            val ID = dbManager.Update(values,"id=?",selectionArs)
            if (ID>0){
                Toast.makeText(this,"Catatan Berhasil Ditambahkan", Toast.LENGTH_LONG).show()
                finish()
            }else{
                Toast.makeText(this,"Tidak Bisa Menambahkan Catatan", Toast.LENGTH_LONG).show()
            }
        }
    }

}
