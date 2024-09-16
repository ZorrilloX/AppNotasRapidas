package com.example.appnotas.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appnotas.R
import com.example.appnotas.models.Note


class NoteAdapter(
    private val noteList: ArrayList<Note>,
    private val onNoteClick: (Note) -> Unit, // para seleccionar una nota
    private val onDeleteClick: (Note) -> Unit // para eliminar una nota
): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).
                inflate(R.layout.note_item_layout, parent, false)
        return NoteViewHolder(view, onNoteClick, onDeleteClick)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
            holder.bind(noteList[position])
    }



    //EL VIEWHOLDER
    class NoteViewHolder(
        itemView: View,
        private val onNoteClick: (Note) -> Unit,
        private val onDeleteClick: (Note) -> Unit
    ): RecyclerView.ViewHolder(itemView){
        private var lblNoteContent = itemView.findViewById<TextView>(R.id.lblNoteContent)
        private var imgDeleteNote = itemView.findViewById<ImageView>(R.id.imgDeleteNote)
        fun bind(note: Note){
            lblNoteContent.text = note.content
            itemView.setBackgroundColor(note.color) // cambiamos el color del fondo

            itemView.setOnClickListener {
                onNoteClick(note)
            }
            imgDeleteNote.setOnClickListener {
                onDeleteClick(note) // llamamos al método para eliminar la nota
            }
        }
    }


}