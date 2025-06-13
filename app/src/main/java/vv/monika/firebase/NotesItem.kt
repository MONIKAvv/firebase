package vv.monika.firebase

import android.icu.text.CaseMap.Title

data class NotesItem(val title : String, val description: String, val noteId:String){
    constructor():this("", "","")

}
