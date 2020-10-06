package com.android.testwork.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.android.testwork.Note
import com.android.testwork.R
import com.android.testwork.NotesViewModel


import kotlinx.android.synthetic.main.fragment_note_screen.view.*


class NoteScreenFragment : Fragment() {

    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var titleEditText: EditText
    lateinit var noteTextEditText: EditText

    lateinit var note: Note

    val noteScreenArgs: NoteScreenFragmentArgs by navArgs()

    val viewModel: NotesViewModel by lazy { ViewModelProvider(this).get(NotesViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note_screen, container, false)

        toolbar = view.noteScreenToolbar
        titleEditText = view.addNoteTitleEditText
        noteTextEditText = view.addNoteTextEditText

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!noteScreenArgs.creatingNote) {
            note = viewModel.getNoteById(noteScreenArgs.noteId)

            titleEditText.setText(note.title)
            noteTextEditText.setText(note.noteText)
        } else note = Note()

        titleEditText.addTextChangedListener {
            note.title = titleEditText.text.toString()
        }

        noteTextEditText.addTextChangedListener {
            note.noteText = noteTextEditText.text.toString()
        }

        toolbar.setupWithNavController(findNavController())

    }

    override fun onStop() {
        super.onStop()

        if(noteScreenArgs.creatingNote) {
            if(note.title.isNotEmpty() || note.noteText.isNotEmpty())
                viewModel.insertNote(note)
        } else {
            if(note.title.isNotEmpty() || note.noteText.isNotEmpty())
                viewModel.updateNote(note)
            else viewModel.deleteNote(note)
        }


    }

}