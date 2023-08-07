package com.sametozkan.notepadapp.presentation.color

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sametozkan.notepadapp.databinding.BottomSheetColorSelectionBinding

class ColorSelectionBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetColorSelectionBinding
    private lateinit var colorSelection: ColorSelection

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            colorSelection = context as ColorSelection
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetColorSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnSelectedListener()
    }

    private fun setOnSelectedListener() {
        binding.apply {
            blue.setOnClickListener {
                colorSelection.onColorSelected(ColorEnum.BLUE)
            }
            green.setOnClickListener {
                colorSelection.onColorSelected(ColorEnum.GREEN)
            }
            orange.setOnClickListener {
                colorSelection.onColorSelected(ColorEnum.ORANGE)
            }
            pink.setOnClickListener {
                colorSelection.onColorSelected(ColorEnum.PINK)
            }
            red.setOnClickListener {
                colorSelection.onColorSelected(ColorEnum.RED)
            }
            yellow.setOnClickListener {
                colorSelection.onColorSelected(ColorEnum.YELLOW)
            }
        }
    }
}