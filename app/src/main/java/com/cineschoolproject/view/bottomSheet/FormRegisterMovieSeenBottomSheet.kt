package com.cineschoolproject.view.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cineschoolproject.R
import com.cineschoolproject.exceptions.MovieSeenExceptionMessages
import com.cineschoolproject.models.movie_model.dto.RegisterMovieSeenRequest
import com.cineschoolproject.viewModel.MovieSeenViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale

private const val MIN_THRESHOLD_NOTE = 0
private const val MAX_THRESHOLD_NOTE = 5
private const val FORMAT_DATE = "dd-MM-yyyy"

class FormRegisterMovieSeenBottomSheet(
    private val movieSeenRegisteredListener: OnMovieSeenRegisteredListener? = null
) : BottomSheetDialogFragment() {

    private val movieSeenViewModel : MovieSeenViewModel by viewModel()
    private lateinit var  saveMovieSeenButton: Button
    private lateinit var  dateInputEditText: EditText
    private lateinit var  noteInputEditText: EditText
    private lateinit var  commentInputEditText: EditText
    private var movieTitleTextView: String? = null
    private var moviePosterImageView: String? = null
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieTitleTextView = it.getString("movieTitle")
            moviePosterImageView = it.getString("movieImageUrl")
            movieId = it.getInt("movieId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO : find a way for keyboard
        return inflater.inflate(R.layout.form_register_movie_seen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.saveMovieSeenButton = view.findViewById(R.id.save_movie_seen_bt)
        this.dateInputEditText = view.findViewById(R.id.date_input_et)
        this.noteInputEditText = view.findViewById(R.id.note_input_et)
        this.commentInputEditText = view.findViewById(R.id.comment_input_et)

        this.saveMovieSeenButton.setOnClickListener {
            this.addMovieSeen()
        }
    }

    private fun addMovieSeen() {
        val date = dateInputEditText.text.toString()
        val noteEditText = noteInputEditText.text.toString()
        val commentEditText = commentInputEditText.text.toString()

        if (checkForm(date, noteEditText, commentEditText)) {
            val registerMovieSeenRequest = RegisterMovieSeenRequest(
                date,
                noteEditText.toInt(),
                commentEditText
            )

            val movieTitle = movieTitleTextView ?: "No title"
            val movieImageUrl = moviePosterImageView ?: "No Image"
            movieSeenViewModel.addMovieSeen(registerMovieSeenRequest, movieTitle, movieImageUrl, movieId)
            movieSeenRegisteredListener?.onMovieSeenRegistered()
        }
    }

    private fun checkForm(date: String, noteEditText: String, commentEditText: String): Boolean {
        if (date.isBlank() || noteEditText.isBlank() || commentEditText.isBlank()) {
            showError(MovieSeenExceptionMessages.EMPTY_FIELDS.message)
            return false
        }

        val noteFormattedInt = try {
            noteEditText.toInt()
        } catch (e: NumberFormatException) {
            showError(MovieSeenExceptionMessages.INVALID_NOTE.message)
            return false
        }

        if(noteFormattedInt < MIN_THRESHOLD_NOTE || noteFormattedInt > MAX_THRESHOLD_NOTE) {
            showError(MovieSeenExceptionMessages.INVALID_THRESHOLD_NOTE.message)
            return false
        }

        return try {
            val dateFormat = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
            dateFormat.parse(date)
            true
        } catch (e: Exception) {
            showError(MovieSeenExceptionMessages.INVALID_FORMAT_DATE.message)
            false
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}

interface OnMovieSeenRegisteredListener {
    fun onMovieSeenRegistered()
    fun onMovieSeenDeleted()
}


