package com.sametozkan.notepadapp.presentation.note

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sametozkan.notepadapp.databinding.ActivityNoteBinding
import com.sametozkan.notepadapp.presentation.color.ColorEnum
import com.sametozkan.notepadapp.presentation.color.ColorSelectionListener
import com.sametozkan.notepadapp.presentation.delete.DeleteDialogListener
import com.sametozkan.notepadapp.presentation.label.LabelSelectionActivity
import com.sametozkan.notepadapp.presentation.note.detail.NoteDetailFragment
import com.sametozkan.notepadapp.presentation.note.edit.NoteEditFragment
import com.sametozkan.notepadapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint

interface LabelSelection {
    fun startLabelSelectionActivity()
}

@AndroidEntryPoint
class NoteActivity : AppCompatActivity(), LabelSelection, ColorSelectionListener,
    DeleteDialogListener {

    private val TAG = "NoteActivity"
    private lateinit var binding: ActivityNoteBinding
    val viewModel: NoteViewModel by viewModels()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val detailFragment = NoteDetailFragment()
    private val editFragment = NoteEditFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
        getExtras()
        setObserver()
        setResultLauncher()
        changeFragment(detailFragment)
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun setResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    result.data?.let {
                        it.getLongArrayExtra(Constants.LABEL_ID_LIST)?.let {
                            viewModel.resultLabels = it.toCollection(ArrayList())
                            Log.d(TAG, "setResultLauncer: idList" + it.toCollection(ArrayList()))
                            viewModel.updateLabels()
                        }
                    }
                }
            }
    }

    private fun getExtras() {
        intent.extras?.let {
            intent.apply {
                val noteId = this.getLongExtra(Constants.NOTE_ENTITY_ID, 1)
                viewModel.fetchNoteWithLabelsById(noteId).observe(this@NoteActivity) {
                    it?.let {
                        viewModel.noteWithLabels.postValue(it)
                        binding.toolbar.setBackgroundResource(it.note.color)
                    }
                        ?: run {
                            finish()
                        }

                }
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameLayout.id, fragment)
            commit()
        }
    }

    private fun setObserver() {
        viewModel.changeFragment.observe(this@NoteActivity) {
            when (it) {
                Constants.NOTE_DETAIL -> changeFragment(detailFragment)
                Constants.NOTE_EDIT -> changeFragment(editFragment)
            }
        }
    }

    override fun startLabelSelectionActivity() {
        viewModel.noteWithLabels.value?.let {
            val idList = it.labels.map { it.uid }
            val intent = Intent(this, LabelSelectionActivity::class.java)
            intent.putExtra(Constants.LABEL_ID_LIST, idList.toLongArray())
            viewModel.noteWithLabels.value?.let {
                intent.putExtra(Constants.COLOR, it.note.color)
            }
            resultLauncher.launch(intent)
        }
    }

    override fun onColorSelected(color: ColorEnum) {
        viewModel.noteWithLabels.value?.let {
            it.note.color = color.colorId
            viewModel.updateNote()
        }
    }

    override fun onClickDelete() {
        viewModel.deleteNote()
    }

}