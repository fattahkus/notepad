package com.fatkus.notepad

class Note {
    var noteId:Int?=null
    var noteNama:String?=null
    var noteDes:String?=null

    constructor(noteid:Int,noteNama:String,noteDes:String){
        this.noteId=noteId
        this.noteNama=noteNama
        this.noteDes=noteDes
    }
}