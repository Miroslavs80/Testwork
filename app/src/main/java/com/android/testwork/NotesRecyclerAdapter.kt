package com.android.testwork

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_note_layout.view.*

class NotesRecyclerAdapter : RecyclerView.Adapter<NotesRecyclerAdapter.NoteViewHolder >() {


    private val diffCallback = object : DiffUtil.ItemCallback<Note>() {

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    private lateinit var onClickListener: OnNoteClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.single_note_layout, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = differ.currentList[position]

        holder.title.text = note.title
        holder.noteText.text = note.noteText

        // Handling note clicks

        holder.itemView.setOnClickListener { onClickListener.onNoteClicked(note) }

        holder.deleteButton.setOnClickListener { onClickListener.onNoteLongClicked(note) }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView
        val noteText: TextView
        val deleteButton: ImageButton

        init {
            title = itemView.noteTitleTextView
            noteText = itemView.noteDescriptionTextView
            deleteButton = itemView.deleteNoteButton
        }


    }

    fun setOnNoteClickListener(listener: OnNoteClickListener) {
        onClickListener = listener
    }

    interface OnNoteClickListener {
        fun onNoteClicked(note: Note)
        fun onNoteLongClicked(note: Note)
    }


}