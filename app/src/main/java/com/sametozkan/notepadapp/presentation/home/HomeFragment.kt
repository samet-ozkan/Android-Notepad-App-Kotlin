package com.sametozkan.notepadapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sametozkan.notepadapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment @Inject constructor() : Fragment() {

    private lateinit var viewModel : HomeViewModel
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : NoteListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModel()
        setRecyclerView()
        setObserver()
    }

    private fun setViewModel(){
        viewModel = ViewModelProvider(this@HomeFragment).get(HomeViewModel::class.java)
    }

    private fun setObserver(){
        viewModel.fetchNotesWithLabels().observe(viewLifecycleOwner){
            it?.let {
                viewModel.notesWithLabels = it
                adapter.noteList = it
            }
        }
    }

    private fun setRecyclerView(){
        adapter = NoteListAdapter(viewModel.notesWithLabels)
        binding.homeRecyclerView.apply {
            adapter = this@HomeFragment.adapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}