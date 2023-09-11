package com.sametozkan.notepadapp.presentation.label

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sametozkan.notepadapp.R
import com.sametozkan.notepadapp.presentation.createlabel.CreateLabelBottomSheetFragment
import com.sametozkan.notepadapp.presentation.createlabel.CreateLabelClickListener
import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity
import com.sametozkan.notepadapp.databinding.ActivityLabelSelectionBinding
import com.sametozkan.notepadapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LabelSelectionActivity : AppCompatActivity(), MenuProvider, LabelClickListener,
    CreateLabelClickListener {

    private val TAG = "LabelSelectionActivity"
    private lateinit var binding: ActivityLabelSelectionBinding
    val viewModel: LabelSelectionViewModel by viewModels()
    private lateinit var adapter: LabelSelectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLabelSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setToolbar()
        addMenuProvider(this)
        getExtras()
        setRecyclerView()
        observeLabels()
        observeToastMessage()
        setCreateLabelButton()
    }

    private fun setToolbar() {
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "Select Label"
        }

    }

    private fun setCreateLabelButton() {
        binding.createLabel.setOnClickListener { it ->
            CreateLabelBottomSheetFragment().show(supportFragmentManager, "Create Label")
        }
    }

    private fun getExtras() {
        if (viewModel.selectedLabelList.isEmpty()) {
            intent.extras?.let { bundle ->
                bundle.apply {
                    getLongArray(Constants.LABEL_ID_LIST)?.let {
                        viewModel.selectedLabelList = it.toCollection(ArrayList())
                    }
                    getInt(Constants.COLOR)?.let {
                        binding.toolbar.setBackgroundResource(it)
                    }
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

    private fun observeLabels() {
        viewModel.fetchLabels().observe(this) { labelList ->
            if (labelList.isEmpty()) {
                binding.labelListRv.visibility = View.GONE
                binding.empty.emptyState.visibility = View.VISIBLE
            } else {
                adapter.apply {
                    this.labelList = labelList
                    this.selectedLabelIdList = viewModel.selectedLabelList
                }
                binding.labelListRv.visibility = View.VISIBLE
                binding.empty.emptyState.visibility = View.GONE
            }

        }
    }

    private fun observeToastMessage() {
        viewModel.toastMessage.observe(this) { message ->
            if (message.isNotEmpty())
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.label_selection_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> finish()
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

    override fun onLabelClicked(id: Long, isSelected: Boolean) {
        when (isSelected) {
            true -> viewModel.selectedLabelList.add(id)
            false -> viewModel.selectedLabelList.remove(id)
        }
        adapter.selectedLabelIdList = viewModel.selectedLabelList
        Log.d(TAG, "onClick: selectedLabelList " + viewModel.selectedLabelList)
    }

    override fun onCreateButtonClicked(labelEntity: LabelEntity) {
        viewModel.addLabels(labelEntity)
    }


}