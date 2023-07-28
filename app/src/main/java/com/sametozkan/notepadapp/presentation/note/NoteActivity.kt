package com.sametozkan.notepadapp.presentation.note

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteWithLabels
import com.sametozkan.notepadapp.databinding.ActivityNoteBinding
import com.sametozkan.notepadapp.presentation.note.detail.NoteDetailFragment
import com.sametozkan.notepadapp.presentation.note.edit.NoteEditFragment
import com.sametozkan.notepadapp.util.Constants
import com.sametozkan.notepadapp.util.getSerializable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding
    val viewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        getExtras()
        setFragment(NoteDetailFragment())
        setObserver()
    }

    private fun getExtras() {
        intent.extras?.let {
            intent.apply {
                viewModel.noteWithLabels.value =
                    getSerializable(Constants.NOTE_ENTITY, NoteWithLabels::class.java)
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameLayout.id, fragment)
            commit()
        }
    }

    private fun setObserver(){
        viewModel.changeFragment.observe(this@NoteActivity){
            when(it){
                Constants.NOTE_DETAIL -> setFragment(NoteDetailFragment())
                Constants.NOTE_EDIT -> setFragment(NoteEditFragment())
            }
        }
    }

}