package com.sametozkan.notepadapp.presentation.note.edit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sametozkan.notepadapp.R
import com.sametozkan.notepadapp.databinding.FragmentNoteEditBinding
import com.sametozkan.notepadapp.presentation.note.NoteViewModel
import com.sametozkan.notepadapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteEditFragment @Inject constructor() : Fragment(), MenuProvider {

    private val TAG = "NoteEditFragment"

    private lateinit var binding: FragmentNoteEditBinding
    val viewModel: NoteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMenu()
        setNote()
    }

    private fun setMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this)
    }

    private fun setNote() {
        Log.d(TAG, "setNote: " + viewModel.noteWithLabels.value)
        viewModel.noteWithLabels.value?.let { noteWithLabels ->

            Log.d(TAG, "setNote: binding")
            binding.apply {
                title.setText(noteWithLabels.note.title)
                content.setText(noteWithLabels.note.text)
            }

        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.note_edit_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.done -> {
                viewModel.noteWithLabels.value?.let { noteWithLabels ->
                    noteWithLabels.note.apply {
                        title = binding.title.text.toString()
                        text = binding.content.text.toString()
                    }
                    viewModel.updateNote()
                    viewModel.changeFragment.value = Constants.NOTE_DETAIL
                }
            }

            else -> return false
        }
        return true
    }

}