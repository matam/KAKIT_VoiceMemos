package edu.zut.wi.kakit.voicememos.ui.notes

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.zut.wi.kakit.voicememos.model.NoteModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import kotlin.collections.ArrayList

class NotesViewModel: ViewModel() {
    //private var _dataNotes = ArrayList<NoteModel>()
    var dataNotes = MutableLiveData<ArrayList<NoteModel>>()
    fun getDataNotes(): LiveData<ArrayList<NoteModel>> {
        return dataNotes
    }


    private fun reformatDate(valueDays: Long) : String
    {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDateTime.now().minusDays(valueDays).format(formatter)

    }
    fun addData( dataItem: ArrayList<String>)
    {
        dataNotes.value?.add(NoteModel(dataNotes.value!!.size+1, reformatDate(0) ,concatAnswer(dataItem)))
        println("has observer" + dataNotes.hasObservers())
        //dataNotes.postValue()
    }
    private fun concatAnswer(data : java.util.ArrayList<String>) : String
    {
        val sb = StringBuilder()
        data.forEach{
            sb.append(it + "\n")
        }
        return sb.toString()
    }
}