package com.sametozkan.notepadapp.presentation.label

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sametozkan.notepadapp.R
import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity
import com.sametozkan.notepadapp.databinding.ActivityLabelSelectionBinding
import com.sametozkan.notepadapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint

interface LabelItemClickListener {
    fun onClick(id: Long, isSelected: Boolean)
}

@AndroidEntryPoint
class LabelSelectionActivity : AppCompatActivity(), MenuProvider, LabelItemClickListener {

    private val TAG = "LabelSelectionActivity"

    override fun onClick(id: Long, isSelected: Boolean) {
        when (isSelected) {
            true -> viewModel.selectedLabelList.add(id)
            false -> viewModel.selectedLabelList.remove(id)
        }
        adapter.selectedLabelIdList = viewModel.selectedLabelList
        Log.d(TAG, "onClick: selectedLabelList " + viewModel.selectedLabelList)
    }

    private lateinit var binding: ActivityLabelSelectionBinding
    val viewModel: LabelSelectionViewModel by viewModels()
    private lateinit var adapter: LabelSelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLabelSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        addMenuProvider(this)
        getExtras()
        setRecyclerView()
        setObserver()
    }

    private fun getExtras() {
        if (viewModel.selectedLabelList.isEmpty()) {
            intent.extras?.let { bundle ->
                bundle.getLongArray(Constants.LABEL_ID_LIST)?.let {
                    viewModel.selectedLabelList = it.toCollection(ArrayList())
                }
            }
        }
    }

    private fun setRecyclerView() {
        adapter = LabelSelectionAdapter(ArrayList(), ArrayList(), this@LabelSelectionActivity)
        binding.labelListRv.apply {
            adapter = this@LabelSelectionActivity.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setObserver() {
        viewModel.fetchLabelsUseCase().observe(this) { labelList ->
            adapter.apply {
                this.labelList = labelList
                this.selectedLabelIdList = viewModel.selectedLabelList
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.label_selection_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.done -> {
                val intent = Intent()
                intent.putExtra(Constants.LABEL_ID_LIST, viewModel.selectedLabelList.toLongArray())
                Log.d(TAG, "onMenuItemSelected: selectedLabelList" + viewModel.selectedLabelList)
                setResult(Activity.RESULT_OK, intent)
                finish()
                return true
            }
        }
        return false
    }


}