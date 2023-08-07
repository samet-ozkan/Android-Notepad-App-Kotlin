package com.sametozkan.notepadapp.presentation.note.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sametozkan.notepadapp.R
import com.sametozkan.notepadapp.databinding.FragmentNoteDetailBinding
import com.sametozkan.notepadapp.presentation.color.ColorSelectionBottomSheetFragment
import com.sametozkan.notepadapp.presentation.delete.DeleteDialogFragment
import com.sametozkan.notepadapp.presentation.note.LabelSelection
import com.sametozkan.notepadapp.presentation.note.NoteViewModel
import com.sametozkan.notepadapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteDetailFragment : Fragment(), MenuProvider {

    private val TAG = "NoteDetailFragment"

    private lateinit var binding: FragmentNoteDetailBinding

    private lateinit var labelRvAdapter: LabelListAdapter

    private lateinit var labelSelection: LabelSelection

    val viewModel: NoteViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        labelSelection = context as LabelSelection
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMenuProvider()
        setLabelRv()
        setObserver()
    }

    private fun setObserver() {
        viewModel.noteWithLabels.observe(viewLifecycleOwner) {
            binding.apply {
                title.text = it.note.title
                content.text = it.note.text
                datetime.text = it.note.timestamp.toString()
            }
            labelRvAdapter.labelList = it.labels
        }
    }

    private fun setMenuProvider() {
        var menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.note_detail_menu, menu)
        viewModel.noteWithLabels.value?.let {
            if (it.note.isFavorite) {
                menu.findItem(R.id.favorite).setIcon(R.drawable.baseline_favorite_24)
            } else {
                menu.findItem(R.id.favorite).setIcon(R.drawable.baseline_favorite_border_24)
            }
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.delete -> DeleteDialogFragment()
                .show(requireActivity().supportFragmentManager, "Delete Dialog")

            R.id.color -> ColorSelectionBottomSheetFragment()
                .show(requireActivity().supportFragmentManager, "Color Selection")

            R.id.edit -> viewModel.changeFragment.value = Constants.NOTE_EDIT
            R.id.label -> labelSelection.startLabelSelectionActivity()
            R.id.favorite -> {
                viewModel.noteWithLabels.value?.let {
                    if (it.note.isFavorite) {
                        it.note.isFavorite = false
                        menuItem.setIcon(R.drawable.baseline_favorite_border_24)
                        Log.d(TAG, "onMenuItemSelected: isFavorite " + it.note.isFavorite)
                    } else {
                        it.note.isFavorite = true
                        menuItem.setIcon(R.drawable.baseline_favorite_24)
                        Log.d(TAG, "onMenuItemSelected: isFavorite " + it.note.isFavorite)
                    }
                    viewModel.updateNote()
                }
            }

            else -> return false
        }
        return true
    }

    private fun setLabelRv() {
        labelRvAdapter = LabelListAdapter(ArrayList())
        binding.labelRecyclerView.apply {
            adapter = labelRvAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}