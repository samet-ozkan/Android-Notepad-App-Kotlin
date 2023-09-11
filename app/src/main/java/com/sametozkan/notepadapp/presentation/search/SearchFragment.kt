package com.sametozkan.notepadapp.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sametozkan.notepadapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment @Inject constructor() : Fragment() {

    private val TAG = "SearchFragment"

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchNoteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        setRecyclerView()
        setSearchView()
        setObserver()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@SearchFragment).get(SearchViewModel::class.java)
    }

    private fun setRecyclerView() {
        adapter = SearchNoteListAdapter(viewModel.notesByKeyword)
        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = this@SearchFragment.adapter
        }
    }

    private fun setSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "onQueryTextSubmit: Query text submitted.")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "onQueryTextChange: " + newText)
                newText?.let {
                    if (newText.isEmpty()) {
                        binding.searchRecyclerView.visibility = View.GONE
                        binding.empty.emptyState.visibility = View.VISIBLE
                    } else {
                        viewModel.keyword.value = newText
                        Log.d(TAG, "onQueryTextChange: Keyword value changed")
                        binding.searchRecyclerView.visibility = View.VISIBLE
                        binding.empty.emptyState.visibility = View.GONE
                    }

                    return true
                }
                return false
            }
        })
    }

    private fun setObserver() {
        viewModel.fetchNotesByKeyword().observe(viewLifecycleOwner) {

            it?.let {
                if (it.isEmpty()) {
                    binding.searchRecyclerView.visibility = View.GONE
                    binding.empty.emptyState.visibility = View.VISIBLE
                } else {
                    viewModel.notesByKeyword = it
                    adapter.noteList = it
                    if (binding.searchRecyclerView.visibility != View.VISIBLE && binding.empty.emptyState.visibility != View.GONE) {

                    }
                    binding.searchRecyclerView.visibility = View.VISIBLE
                    binding.empty.emptyState.visibility = View.GONE
                }
            }
        }
    }


}