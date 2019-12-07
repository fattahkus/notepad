package com.fatkus.notepad

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_note.view.*


class MainActivity : AppCompatActivity() {

    var listNotes = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var listNotes = ArrayList<Note>()

        var myNotesAdapter=MyNotesAdapter(this,listNotes)
        lvNotes.adapter=myNotesAdapter

//        listNotes.add(Note(1, " judul", "detail"))
//        listNotes.add(Note(1, " judul", "detail"))
//        listNotes.add(Note(1, " judul", "detail"))
    LoadQuery("%")
    }
    fun LoadQuery(title:String){
        var dbManager=DbManager(this)
        val projections=arrayOf("id","nama","deskripsi")
        val selectionArgs = arrayOf(title)
        val cursor=dbManager.Query(projections,"nama like?",selectionArgs,"nama")
            listNotes.clear()
            if(cursor.moveToFirst()){
                do{
                    val ID=cursor.getInt(cursor.getColumnIndex("id"))
                    val Title=cursor.getString(cursor.getColumnIndex("nama"))
                    val Description=cursor.getString(cursor.getColumnIndex("deskripsi"))

                    listNotes.add(Note(ID,Title,Description))
                }while (cursor.moveToNext())
            }
        var myNotesAdapter=MyNotesAdapter(this,listNotes)
        lvNotes.adapter=myNotesAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        val sv: SearchView = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val sm= getSystemService(Context.SEARCH_SERVICE) as SearchManager
            sv.setSearchableInfo(sm.getSearchableInfo(componentName))
            sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String):Boolean{
                    Toast.makeText(applicationContext,query,Toast.LENGTH_LONG).show()
                    LoadQuery("%"+query+"%")
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item != null){
            when(item.itemId){
                R.id.addNote->{
                    var intent = Intent(this,TambahNote::class.java)
                    startActivity(intent)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    inner class MyNotesAdapter :BaseAdapter{
        var listNotesAdapter = ArrayList<Note>()
        var context:Context?=null
        constructor(context: Context,listNotesAdapter: ArrayList<Note>):super(){
            this.listNotesAdapter=listNotesAdapter
            this.context=context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.item_note,null)
            var myNote = listNotesAdapter[position]
            myView.tvTitle.text=myNote.noteNama
            myView.tvDes.text=myNote.noteDes

            myView.ivDelete.setOnClickListener {
                var dbManager=DbManager(this.context!!)
                val selectionArgs=arrayOf(myNote.noteId.toString())
                dbManager.Delete("id=?",selectionArgs)
                LoadQuery("%")
            }
            myView.ivEdite.setOnClickListener {
                GoToUpdate(myNote)
            }

            return myView
        }
        fun GoToUpdate(note:Note){
            var intent=Intent(context,TambahNote::class.java)
            intent.putExtra("id",note.noteId)
            intent.putExtra("nama",note.noteNama)
            intent.putExtra("deskripsi",note.noteDes)
            startActivity(intent)
        }

        override fun getItem(position: Int): Any {
            return listNotesAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listNotesAdapter.size
        }
    }

    override fun onResume() {
        super.onResume()
        LoadQuery("%")
        Toast.makeText(this,"onResume",Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this,"onStart",Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this,"onPause",Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this,"onStop",Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this,"onDestroy",Toast.LENGTH_LONG).show()
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this,"onRestart",Toast.LENGTH_LONG).show()
    }
}
