package com.sametozkan.notepadapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sametozkan.notepadapp.data.datasource.local.entities.LabelEntity
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteEntity
import com.sametozkan.notepadapp.data.datasource.local.entities.NoteLabelXRef
import com.sametozkan.notepadapp.databinding.ActivityMainBinding
import com.sametozkan.notepadapp.domain.usecase.AddLabelsUseCase
import com.sametozkan.notepadapp.domain.usecase.AddNoteLabelXRefUseCase
import com.sametozkan.notepadapp.domain.usecase.AddNotesUseCase
import com.sametozkan.notepadapp.presentation.home.HomeFragment
import com.sametozkan.notepadapp.presentation.note.add.AddNoteActivity
import com.sametozkan.notepadapp.presentation.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var homeFragment: HomeFragment

    @Inject
    lateinit var searchFragment: SearchFragment

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var addNotesUseCase: AddNotesUseCase

    @Inject
    lateinit var addLabelsUseCase: AddLabelsUseCase

    @Inject
    lateinit var addNoteLabelUseCase: AddNoteLabelXRefUseCase

    enum class Items(val id: Int) {
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
        init()
    }

    private fun init() {
        GlobalScope.launch(Dispatchers.IO) {

            // Not 1
            val note1 = NoteEntity(
                uid = 1,
                title = "Tatil Planları",
                text = "Bu yaz tatil için planlar yapmalıyım. Muhtemel destinasyonlar: İspanya, Yunanistan, Bali.",
                timestamp = getTimestampForDate(2023, Calendar.MARCH, 20),
                isFavorite = false,
                color = R.drawable.background_blue
            )
            addNotesUseCase(note1)

            // Etiketler
            val etiket1 = LabelEntity(uid = 1, name = "Tatil")
            val etiket2 = LabelEntity(uid = 2, name = "Planlar")

            addLabelsUseCase(etiket1)
            addLabelsUseCase(etiket2)

            // Not ile etiketler arasında ilişki kurma
            addNoteLabelUseCase(NoteLabelXRef(note_id = 1, label_id = 1))
            addNoteLabelUseCase(NoteLabelXRef(note_id = 1, label_id = 2))

            // Not 2
            val note2 = NoteEntity(
                uid = 2,
                title = "Proje Toplantısı",
                text = "Proje toplantısı için hazırlıklara başlamalıyım. Sunum ve dokümantasyon gözden geçirilmeli.",
                timestamp = getTimestampForDate(2023, Calendar.APRIL, 5),
                isFavorite = true,
                color = R.drawable.background_green
            )
            addNotesUseCase(note2)

            // Etiketler
            val etiket3 = LabelEntity(uid = 3, name = "İş")
            val etiket4 = LabelEntity(uid = 4, name = "Toplantı")

            addLabelsUseCase(etiket3)
            addLabelsUseCase(etiket4)

            // Not ile etiketler arasında ilişki kurma
            addNoteLabelUseCase(NoteLabelXRef(note_id = 2, label_id = 3))
            addNoteLabelUseCase(NoteLabelXRef(note_id = 2, label_id = 4))

            // Not 3
            val note3 = NoteEntity(
                uid = 3,
                title = "Evcil Hayvan Bakımı",
                text = "Köpeğimin veteriner randevusu var ve kedimin mama bitmek üzere.",
                timestamp = getTimestampForDate(2023, Calendar.MAY, 10),
                isFavorite = false,
                color = R.drawable.background_pink
            )
            addNotesUseCase(note3)

            // Etiketler
            val etiket5 = LabelEntity(uid = 5, name = "Evcil Hayvan")
            val etiket6 = LabelEntity(uid = 6, name = "Randevu")

            addLabelsUseCase(etiket5)
            addLabelsUseCase(etiket6)

            // Not ile etiketler arasında ilişki kurma
            addNoteLabelUseCase(NoteLabelXRef(note_id = 3, label_id = 5))
            addNoteLabelUseCase(NoteLabelXRef(note_id = 3, label_id = 6))

            // Devam eden notlar için aynı şablonu kullanarak ekleyebilirsiniz.
            // Not 4
            val note4 = NoteEntity(
                uid = 4,
                title = "Spor Antrenmanı",
                text = "Bugün akşam spor salonuna gitmeliyim. Kardiyo ve ağırlık antrenmanları yapılacak.",
                timestamp = getTimestampForDate(2023, Calendar.JUNE, 8),
                isFavorite = true,
                color = R.drawable.background_yellow
            )
            addNotesUseCase(note4)

// Etiketler
            val etiket7 = LabelEntity(uid = 7, name = "Spor")
            val etiket8 = LabelEntity(uid = 8, name = "Sağlık")

            addLabelsUseCase(etiket7)
            addLabelsUseCase(etiket8)

// Not ile etiketler arasında ilişki kurma
            addNoteLabelUseCase(NoteLabelXRef(note_id = 4, label_id = 7))
            addNoteLabelUseCase(NoteLabelXRef(note_id = 4, label_id = 8))

// Not 5
            val note5 = NoteEntity(
                uid = 5,
                title = "Yemek Tarifleri",
                text = "Akşam yemeği için farklı bir tarif denemeliyim. Sushi tarifi araştırmalıyım.",
                timestamp = getTimestampForDate(2023, Calendar.JUNE, 25),
                isFavorite = false,
                color = R.drawable.background_orange
            )
            addNotesUseCase(note5)

// Etiketler
            val etiket9 = LabelEntity(uid = 9, name = "Yemek")
            val etiket10 = LabelEntity(uid = 10, name = "Tarif")

            addLabelsUseCase(etiket9)
            addLabelsUseCase(etiket10)

// Not ile etiketler arasında ilişki kurma
            addNoteLabelUseCase(NoteLabelXRef(note_id = 5, label_id = 9))
            addNoteLabelUseCase(NoteLabelXRef(note_id = 5, label_id = 10))
        }


    }

    private fun getTimestampForDate(year: Int, month: Int, day: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.timeInMillis
    }

    private fun setBottomNavigation() {
        binding.bottomNav.apply {
            selectedItemId = Items.HOME.id
            setOnItemSelectedListener {
                when (it.itemId) {
                    Items.HOME.id -> setFragment(homeFragment)
                    Items.SEARCH.id -> setFragment(searchFragment)
                }
                true
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }

}