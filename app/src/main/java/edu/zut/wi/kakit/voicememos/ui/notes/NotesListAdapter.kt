package edu.zut.wi.kakit.voicememos.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.zut.wi.kakit.voicememos.R
import edu.zut.wi.kakit.voicememos.model.NoteModel

class NotesListAdapter ( private val listNotes: List<NoteModel>): RecyclerView.Adapter<NotesListAdapter.ViewHolder>()
{
    class ViewHolder(view : View): RecyclerView.ViewHolder(view) {
        val numberNote : TextView
        val dateNote : TextView
        val textNote : TextView
        init {
            numberNote = view.findViewById(R.id.numberNote)
            dateNote = view.findViewById(R.id.dateNote)
            textNote = view.findViewById(R.id.textNote)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

//
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.notes_item
                , parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return listNotes.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noteItem = listNotes[position]
        holder.textNote.text = noteItem.noteText
        holder.dateNote.text = noteItem.date
        holder.numberNote.text = noteItem.number.toString()
    }
}