package com.android.testwork.fragments


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.testwork.Note
import com.android.testwork.NotesRecyclerAdapter
import com.android.testwork.R
import com.android.testwork.NotesViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_notes_list.view.*


class NotesListFragment : Fragment(), NotesRecyclerAdapter.OnNoteClickListener {

    //region views
    private lateinit var addNoteButton: FloatingActionButton
    private lateinit var notesRecyclerView: RecyclerView
    //endregion


    private val noteViewModel by lazy { ViewModelProvider(this).get(NotesViewModel::class.java) }
    private val notesRecyclerAdapter by lazy { NotesRecyclerAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes_list, container, false)

        addNoteButton = view.addNoteFab
        notesRecyclerView = view.notesRecyclerView

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesRecyclerAdapter.setOnNoteClickListener(this)
        notesRecyclerView.adapter = notesRecyclerAdapter
        noteViewModel.allNotes.observe(viewLifecycleOwner, {
            notesRecyclerAdapter.differ.submitList(it)
        })

        notesRecyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        addNoteButton.setOnClickListener {
            val action = NotesListFragmentDirections.actionFragmentNotesListToNoteScreenFragment(-1, true)
            findNavController().navigate(action)
        }


    }

    override fun onNoteClicked(note: Note) {

        val action = NotesListFragmentDirections.actionFragmentNotesListToNoteScreenFragment(note.id, false)
        findNavController().navigate(action)
    }

    override fun onNoteLongClicked(note: Note) {
        showDeleteDialog(note)
    }

    fun showDeleteDialog(note: Note) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_note_dialog_title))
            .setMessage(getString(R.string.delete_note_dialog_message))
            .setPositiveButton(getString(R.string.delete_text)) { _, _ ->

                noteViewModel.deleteNote(note)
                Snackbar.make(
                    addNoteButton,
                    getString(R.string.note_deleted_text),
                    Snackbar.LENGTH_LONG
                ).apply {

                    anchorView = addNoteButton
                }.show()
            }
            .setNegativeButton(getString(R.string.cancel_text), null)
            .setCancelable(true)
            .show()
    }
}