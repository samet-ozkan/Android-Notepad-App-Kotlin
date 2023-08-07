package com.sametozkan.notepadapp.presentation.note.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.sametozkan.notepadapp.R
import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity
import com.sametozkan.notepadapp.databinding.ActivityAddNoteBinding
import com.sametozkan.notepadapp.presentation.color.ColorEnum
import com.sametozkan.notepadapp.presentation.color.ColorSelection
import com.sametozkan.notepadapp.presentation.color.ColorSelectionBottomSheetFragment
import com.sametozkan.notepadapp.presentation.label.LabelSelectionActivity
import com.sametozkan.notepadapp.presentation.note.detail.LabelListAdapter
import com.sametozkan.notepadapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteActivity : AppCompatActivity(), MenuProvider, ColorSelection {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter: LabelListAdapter

    val viewModel: AddNoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
        addMenuProvider(this)
        setLabelRecyclerView()
        setObserver()
        setResultLauncher()
        setTextChangedListener()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setBackgroundResource(viewModel.color)
    }

    private fun setTextChangedListener() {
        binding.apply {
            title.addTextChangedListener { text ->
                viewModel.title = text.toString()
            }
            content.addTextChangedListener { text ->
                viewModel.content = text.toString()
            }
        }
    }

    private fun setResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    result.data?.let { data ->
                        data.extras?.let {
                            val labelIdList =
                                it.getLongArray(Constants.LABEL_ID_LIST)?.toCollection(ArrayList())
                            viewModel.idList.value = labelIdList
                        }
                    }
                }
            }
    }

    private fun setObserver() {
        viewModel.fetchLabelsByIds().observe(this) {
            if (!it.isNullOrEmpty()) {
                adapter.labelList = it
            }
        }
    }

    private fun setLabelRecyclerView() {
        adapter = LabelListAdapter(ArrayList())
        binding.labelRecyclerView.apply {
            adapter = this@AddNoteActivity.adapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.add_note_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.color -> ColorSelectionBottomSheetFragment()
                .show(supportFragmentManager, "Color Selection")

            R.id.done -> {
                viewModel.save()
                finish()
            }

            R.id.selectLabel -> {
                val intent = Intent(this, LabelSelectionActivity::class.java)
                intent.putExtra(Constants.LABEL_ID_LIST, viewModel.idList.value?.toLongArray())
                resultLauncher.launch(intent)
            }

            R.id.favorite -> {
                if (!viewModel.isFavorite) {
                    menuItem.setIcon(R.drawable.baseline_favorite_24)
                    viewModel.isFavorite = true
                } else {
                    menuItem.setIcon(R.drawable.baseline_favorite_border_24)
                    viewModel.isFavorite = false
                }
            }
        }
        return true
    }

    override fun onColorSelected(color: ColorEnum) {
        viewModel.color = color.colorId
        binding.toolbar.setBackgroundResource(color.colorId)
    }

}