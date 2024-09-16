package com.example.appnotas.ui.activicies


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appnotas.R
import com.example.appnotas.models.Note
import com.example.appnotas.ui.adapters.NoteAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val noteList = arrayListOf(
        Note("Primera nota"),
        Note("Segunda nota")
    )
    private lateinit var rvNoteList: RecyclerView
    private lateinit var fabAddNote: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvNoteList = findViewById(R.id.rvNoteList)
        fabAddNote = findViewById(R.id.fabAddNote)

        setupRecyclerView()

        fabAddNote.setOnClickListener {
            showAddNoteDialog()
        }
    }

    private fun setupRecyclerView() {
        rvNoteList.adapter = NoteAdapter(noteList, { note ->
            showEditNoteDialog(note)
        }, { note ->
            deleteNote(note)
        })
        rvNoteList.layoutManager = LinearLayoutManager(this).apply {
            orientation = RecyclerView.VERTICAL
        }
    }
    private fun deleteNote(note: Note) {
        noteList.remove(note)
        rvNoteList.adapter?.notifyDataSetChanged()
    }

    private fun showAddNoteDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_add_note, null)
        val editTextNote = dialogView.findViewById<EditText>(R.id.etNewNote)


        builder.setView(dialogView)
        builder.setTitle("Añadir Nota")

        builder.setPositiveButton("Agregar") { dialog, _ ->
            val newNote = editTextNote.text.toString()
            if (newNote.isNotEmpty()) {
                noteList.add(Note(newNote))
                rvNoteList.adapter?.notifyDataSetChanged()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "La nota no puede estar vacía", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }

    //@SuppressLint("MissingInflatedId")
    private fun showEditNoteDialog(note: Note) {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_edit_note, null)
        val editTextNote = dialogView.findViewById<EditText>(R.id.etEditNote)
        val colorPicker = dialogView.findViewById<Spinner>(R.id.colorPicker) // Spinner para seleccionar color

        editTextNote.setText(note.content)
        // Configura el Spinner con los colores
        colorPicker.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.color_options, // Define los colores en `res/values/arrays.xml`
            android.R.layout.simple_spinner_item
        )

        builder.setView(dialogView)
        builder.setTitle("Editar Nota")

        builder.setPositiveButton("Guardar") { dialog, _ ->
            val updatedContent = editTextNote.text.toString()
            val selectedColor = colorPicker.selectedItemPosition // Obtén el color seleccionado
            if (updatedContent.isNotEmpty()) {
                note.content = updatedContent
                note.color = getColorFromPosition(selectedColor) // Obtén el color en base a la posición

                val position = noteList.indexOf(note)
                rvNoteList.adapter?.notifyItemChanged(position)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "La nota no puede estar vacía", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        val dialog = builder.create()
        dialog.show()
    }
    private fun getColorFromPosition(position: Int): Int {
        return when (position) {
            0 -> Color.parseColor("#FF5E5E") // Rojo claro
            1 -> Color.parseColor("#C8E6C9") // Verde claro
            2 -> Color.parseColor("#BBDEFB") // Azul claro
            3 -> Color.parseColor("#FFF9C4") // Amarillo claro
            4 -> Color.parseColor("#E1BEE7") // Morado claro
            5 -> Color.parseColor("#FFCC80") // Naranja claro
            6 -> Color.parseColor("#B2EBF2") // Cian claro
            7 -> Color.parseColor("#F48FB1") // Rosa claro
            8 -> Color.parseColor("#CFD8DC") // Gris claro
            9 -> Color.parseColor("#C869CC") // Lila claro
            else -> Color.parseColor("#FFEBEE") // Color predeterminado
        }
    }

}

