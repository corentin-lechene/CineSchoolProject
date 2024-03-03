package com.cineschoolproject.view.bottomSheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.cineschoolproject.R
import com.cineschoolproject.exceptions.MovieSeenExceptionMessages
import com.cineschoolproject.models.movie_model.MovieSeen
import com.cineschoolproject.models.movie_model.dto.RegisterMovieSeenRequest
import com.cineschoolproject.view.ActionBottomSheet
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
class FormViewMovieSeenBottomSheet (
    private var movieSeen: MovieSeen,
    private var actionBottomSheet: ActionBottomSheet
) : BottomSheetDialogFragment() {

    private lateinit var  titleModalTv: TextView
    private lateinit var  dateInputEditText: EditText
    private lateinit var  noteInputEditText: EditText
    private lateinit var  commentInputEditText: EditText
    private lateinit var  saveMovieSeenButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.form_register_movie_seen, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.titleModalTv = view.findViewById(R.id.title_modal_tv)
        this.dateInputEditText = view.findViewById(R.id.date_input_et)
        this.noteInputEditText = view.findViewById(R.id.note_input_et)
        this.commentInputEditText = view.findViewById(R.id.comment_input_et)
        this.saveMovieSeenButton = view.findViewById(R.id.save_movie_seen_bt)

        this.titleModalTv.text = "Infos"
        this.dateInputEditText.setText(movieSeen.registerMovieSeenRequest.dateSeen)
        this.noteInputEditText.setText(movieSeen.registerMovieSeenRequest.note.toString())
        this.commentInputEditText.setText(movieSeen.registerMovieSeenRequest.comment)

        this.saveMovieSeenButton.setOnClickListener{
            update(movieSeen)
        }
    }

    private fun update(movieSeen: MovieSeen) {
        val date = dateInputEditText.text.toString()
        val noteEditText = noteInputEditText.text.toString()
        val commentEditText = commentInputEditText.text.toString()

        if (date.isBlank() || noteEditText.isBlank() || commentEditText.isBlank()) {
            showError(MovieSeenExceptionMessages.EMPTY_FIELDS.message)
        } else {
            val registerMovieSeenRequest = RegisterMovieSeenRequest (
                date,
                noteEditText.toInt(),
                commentEditText
            )

            movieSeen.registerMovieSeenRequest = registerMovieSeenRequest

            try {
                this.actionBottomSheet.updateMovie(this.movieSeen)
                this.dismiss()

            } catch (e: RuntimeException) {
                e.message?.let { showError(it) }
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
