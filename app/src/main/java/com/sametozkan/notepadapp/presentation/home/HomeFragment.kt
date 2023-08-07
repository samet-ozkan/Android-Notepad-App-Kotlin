package com.sametozkan.notepadapp.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.INVISIBLE
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import com.sametozkan.notepadapp.databinding.FragmentHomeBinding
import com.sametozkan.notepadapp.presentation.filter.FilterDialogFragment
import com.sametozkan.notepadapp.presentation.note.add.AddNoteActivity
import com.sametozkan.notepadapp.presentation.note.detail.LabelListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor() : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: NoteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModel()
        setRecyclerView()
        observeNotesWithLabels()
        setCustomToolbar()
        observeFilter()
    }

    private fun setCustomToolbar() {
        binding.filter.setOnClickListener {
            val filterDialog = FilterDialogFragment()
            filterDialog.show(childFragmentManager, "Filter")
        }

        binding.add.setOnClickListener {
            startActivity(Intent(context, AddNoteActivity::class.java))
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private fun observeNotesWithLabels() {
        viewModel.fetchNotesWithLabels().observe(viewLifecycleOwner) {
            it?.let {
                viewModel.notesWithLabels = it
                adapter.noteList = it
            }
        }
    }

    private fun observeFilter() {
        viewModel.getSelectedLabels().observe(viewLifecycleOwner) { selectedLabels ->
            if (!viewModel.selectedIdList.value!!.isEmpty()) {
                binding.labelRv.apply {
                    visibility = VISIBLE
                    adapter = LabelListAdapter(selectedLabels)
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                }
                val filteredNoteList = adapter.noteList.filter { noteWithLabels ->
                    selectedLabels.any { labelEntity ->
                        noteWithLabels.labels.contains(labelEntity)
                    }
                }
                adapter.noteList = filteredNoteList
            } else {
                binding.labelRv.visibility = View.GONE
                adapter.noteList = viewModel.notesWithLabels
            }
        }
    }

    private fun setRecyclerView() {
        adapter = NoteListAdapter(viewModel.notesWithLabels)
        binding.homeRecyclerView.apply {
            adapter = this@HomeFragment.adapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }
}