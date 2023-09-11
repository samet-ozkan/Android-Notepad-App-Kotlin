package com.sametozkan.notepadapp.presentation.filter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sametozkan.notepadapp.databinding.DialogFragmentFilterBinding
import com.sametozkan.notepadapp.presentation.home.HomeViewModel
import com.sametozkan.notepadapp.presentation.label.LabelClickListener
import com.sametozkan.notepadapp.presentation.label.LabelSelectionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterDialogFragment : DialogFragment(), LabelClickListener {

    private val TAG = "FilterDialogFragment"
    var selectedIdList: ArrayList<Long> = ArrayList()
    val viewModel: HomeViewModel by viewModels(ownerProducer = ({ requireParentFragment() }))
    private lateinit var adapter: LabelSelectionAdapter
    private lateinit var binding: DialogFragmentFilterBinding

    override fun onLabelClicked(id: Long, isSelected: Boolean) {
        if (isSelected) {
            selectedIdList.add(id)
        } else {
            selectedIdList.remove(id)
        }
        println("Selected id: " + selectedIdList)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.selectedIdList.value?.let {
            this.selectedIdList = it as ArrayList<Long>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFragmentFilterBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setObserver()
        setButtons()
    }

    private fun setRecyclerView() {
        adapter = LabelSelectionAdapter(ArrayList(), viewModel.selectedIdList.value!!, this)
        binding.labelsRecyclerView.apply {
            adapter = this@FilterDialogFragment.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setObserver() {
        viewModel.fetchLabels().observe(viewLifecycleOwner) { labels ->
            if (labels.isEmpty()) {
                binding.labelsRecyclerView.visibility = View.GONE
                binding.empty.emptyState.visibility = View.VISIBLE
            } else {
                adapter.labelList = labels
                binding.labelsRecyclerView.visibility = View.VISIBLE
                binding.empty.emptyState.visibility = View.GONE
            }
        }
    }

    private fun setButtons() {
        binding.filterButton.setOnClickListener {
            println("Filter button: " + viewModel.selectedIdList.value)
            viewModel.selectedIdList.value = this.selectedIdList
            dismiss()
        }
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }
}