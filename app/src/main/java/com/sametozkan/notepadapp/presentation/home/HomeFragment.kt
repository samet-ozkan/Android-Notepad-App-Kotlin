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
import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteWithLabels
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
        setClearButton()
    }

    private fun setClearButton() {
        binding.clear.setOnClickListener {
            viewModel.selectedIdList.value = ArrayList()
        }
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
            if (it.isEmpty()) {
                binding.homeRecyclerView.visibility = View.GONE
                binding.empty.emptyState.visibility = View.VISIBLE
            } else {
                it?.let { it ->
                    viewModel.notesWithLabels = it
                    adapter.noteList = it
                    viewModel.selectedIdList.value?.let { selectedIdList ->
                        if (selectedIdList.isNotEmpty()) {
                            adapter.noteList =
                                viewModel.filterByLabelIds(selectedIdList, it)
                        }
                    }
                }
                binding.homeRecyclerView.visibility = View.VISIBLE
                binding.empty.emptyState.visibility = View.GONE
            }

        }
    }

    private fun observeFilter() {
        viewModel.getSelectedLabels().observe(viewLifecycleOwner) { selectedLabels ->
            if (viewModel.selectedIdList.value!!.isNotEmpty()) {
                binding.labelRv.apply {
                    adapter = LabelListAdapter(selectedLabels)
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                }
                adapter.noteList =
                    viewModel.filterByLabelEntities(selectedLabels, viewModel.notesWithLabels)
                binding.filterField.visibility = View.VISIBLE
            } else {
                binding.filterField.visibility = View.GONE
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