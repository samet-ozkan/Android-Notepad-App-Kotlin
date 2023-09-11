package com.sametozkan.notepadapp.presentation.createlabel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity
import com.sametozkan.notepadapp.databinding.BottomSheetCreateLabelBinding
import java.lang.Exception

class CreateLabelBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetCreateLabelBinding
    private lateinit var createLabelClickListener: CreateLabelClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            createLabelClickListener = context as CreateLabelClickListener
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetCreateLabelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCreateButtonClickListener()
    }

    private fun setCreateButtonClickListener() {
        binding.createLabelButton.setOnClickListener { view ->
            createLabelClickListener.onCreateButtonClicked(
                LabelEntity(name = binding.labelTextInput.text.toString())
            )
            dismiss()
        }
    }
}