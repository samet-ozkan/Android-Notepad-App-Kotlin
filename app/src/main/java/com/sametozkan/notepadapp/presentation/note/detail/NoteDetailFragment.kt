package com.sametozkan.notepadapp.presentation.note.detail

import android.os.Bundle
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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.sametozkan.notepadapp.R
import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteEntity
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteWithLabels
import com.sametozkan.notepadapp.databinding.FragmentNoteDetailBinding
import com.sametozkan.notepadapp.presentation.note.NoteViewModel
import com.sametozkan.notepadapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class NoteDetailFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentNoteDetailBinding
    val viewModel: NoteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMenuProvider()

        viewModel.noteWithLabels.value?.let {
            setNote(it.note)
            setLabels(it.labels)
        }
    }

    private fun setMenuProvider(){
        var menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this)
    }

    private fun setNote(note: NoteEntity) {
        binding.apply {
            title.text = note.title
            content.text = note.text
            datetime.text = note.timestamp.toString()
        }
    }

    private fun setLabels(labels: List<LabelEntity>) {
        val rvAdapter = LabelListAdapter(labels)
        binding.labelRecyclerView.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.note_detail_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.edit -> viewModel.changeFragment.value = Constants.NOTE_EDIT
            else -> return false
        }
        return true
    }
}