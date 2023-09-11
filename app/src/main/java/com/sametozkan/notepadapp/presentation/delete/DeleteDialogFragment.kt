package com.sametozkan.notepadapp.presentation.delete

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sametozkan.notepadapp.databinding.DialogFragmentDeleteBinding

class DeleteDialogFragment : DialogFragment() {

    private lateinit var binding: DialogFragmentDeleteBinding
    private lateinit var deleteDialogListener: DeleteDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            deleteDialogListener = context as DeleteDialogListener
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentDeleteBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.deleteButton.setOnClickListener {
            deleteDialogListener.onClickDelete()
        }
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }


}