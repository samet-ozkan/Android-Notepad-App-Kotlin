package com.sametozkan.notepadapp

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.sametozkan.notepadapp.data.datasource.local.AppDatabase
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteEntity
import com.sametozkan.notepadapp.databinding.ActivityMainBinding
import com.sametozkan.notepadapp.domain.usecase.AddNotesUseCase
import com.sametozkan.notepadapp.presentation.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var homeFragment : HomeFragment

    @Inject
    lateinit var addNote : AddNotesUseCase

    private lateinit var binding : ActivityMainBinding

    enum class Items(val id: Int){
        HOME(R.id.home),
        SEARCH(R.id.search),
        ADD(R.id.add),
        FAVORITES(R.id.favorites),
        SETTINGS(R.id.settings)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setBottomNavigation()
        setFragment(homeFragment)
        GlobalScope.launch(Dispatchers.IO) {
            addNote(NoteEntity(title = "Deneme", text = "Icerik",
                timestamp = Calendar.getInstance().timeInMillis,
                isFavorite = true),
                NoteEntity(title = "Deadnemttttttttttttttttttte", text = "Iqwecerrrrrrrrrrik",
                    timestamp = Calendar.getInstance().timeInMillis,
                    isFavorite = true),
                NoteEntity(title = "Deweqqqnrrrreme", text = "Ice123rik",
                    timestamp = Calendar.getInstance().timeInMillis,
                    isFavorite = false),
                NoteEntity(title = "Drty tr yrt yrt ytr yrty rt ytr y e21neme", text = "I232cerik",
                    timestamp = Calendar.getInstance().timeInMillis,
                    isFavorite = true),
                NoteEntity(title = "Desssneme", text = "Icerty tyrt yt rtyrt ytr yrt y t ssrik",
                    timestamp = Calendar.getInstance().timeInMillis,
                    isFavorite = true))
        }
    }

    private fun setBottomNavigation(){
        binding.bottomNav.apply {
            selectedItemId = Items.HOME.id
            setOnItemSelectedListener {
                when(it.itemId){
                    Items.HOME.id -> setFragment(homeFragment)
                }
                true
            }
        }
    }

    private fun setFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }

}