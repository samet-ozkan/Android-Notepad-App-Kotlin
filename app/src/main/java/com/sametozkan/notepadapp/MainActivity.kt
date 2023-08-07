package com.sametozkan.notepadapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sametozkan.notepadapp.databinding.ActivityMainBinding
import com.sametozkan.notepadapp.presentation.home.HomeFragment
import com.sametozkan.notepadapp.presentation.note.add.AddNoteActivity
import com.sametozkan.notepadapp.presentation.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var homeFragment : HomeFragment

    @Inject
    lateinit var searchFragment: SearchFragment

    lateinit var binding : ActivityMainBinding

    enum class Items(val id: Int){
        HOME(R.id.home),
        SEARCH(R.id.search),
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setBottomNavigation()
        setFragment(homeFragment)

    }

    private fun setBottomNavigation(){
        binding.bottomNav.apply {
            selectedItemId = Items.HOME.id
            setOnItemSelectedListener {
                when(it.itemId){
                    Items.HOME.id -> setFragment(homeFragment)
                    Items.SEARCH.id -> setFragment(searchFragment)
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