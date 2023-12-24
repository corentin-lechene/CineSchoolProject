package com.cineschoolproject.view.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.cineschoolproject.R
import com.cineschoolproject.viewModel.MovieSeenViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FormRegisterMovieSeenBottomSheet : BottomSheetDialogFragment() {
    private val movieSeenViewModel : MovieSeenViewModel by viewModel()
    private lateinit var  saveMovieSeenButton: Button
    private lateinit var  dateInputEditText: EditText
    private lateinit var  noteInputEditText: EditText
    private lateinit var  commentInputEditText: EditText


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.form_register_movie_seen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.saveMovieSeenButton = view.findViewById(R.id.save_movie_seen_bt)
        this.dateInputEditText = view.findViewById(R.id.date_input_et)
        this.noteInputEditText = view.findViewById(R.id.note_input_et)
        this.commentInputEditText = view.findViewById(R.id.comment_input_et)

        saveMovieSeenButton.setOnClickListener {
            dismiss() // Cela ferme le BottomSheet
        }
    }
}