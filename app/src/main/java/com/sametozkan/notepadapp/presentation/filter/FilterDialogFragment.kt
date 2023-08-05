package com.sametozkan.notepadapp.presentation.filter

import android.app.ActionBar.LayoutParams
import android.app.Dialog
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
import com.sametozkan.notepadapp.presentation.label.LabelItemClickListener
import com.sametozkan.notepadapp.presentation.label.LabelSelectionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterDialogFragment : DialogFragment(), LabelItemClickListener {

    private val TAG = "FilterDialogFragment"
    val selectedIdList: ArrayList<Long> = ArrayList()
    val viewModel: HomeViewModel by viewModels(ownerProducer = ({ requireParentFragment() }))
    private lateinit var adapter: LabelSelectionAdapter
    private lateinit var binding: DialogFragmentFilterBinding

    override fun onClick(id: Long, isSelected: Boolean) {
        if (isSelected) {
            selectedIdList.add(id)
        } else {
            selectedIdList.remove(id)
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
            adapter.labelList = labels
            Log.d(TAG, "setObserver: fetchLabels" + labels)
        }
    }

    private fun setButtons() {
        binding.filterButton.setOnClickListener {
            viewModel.selectedIdList.value = this.selectedIdList
            dismiss()
        }
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }
}