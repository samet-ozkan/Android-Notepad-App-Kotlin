package com.sametozkan.notepadapp.presentation.createlabel

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.view.WindowCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.sametozkan.notepadapp.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetCreateLabelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        if (dialog is BottomSheetDialog) {
            dialog.behavior.skipCollapsed = true
            dialog.behavior.state = STATE_EXPANDED
        }
        return dialog
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