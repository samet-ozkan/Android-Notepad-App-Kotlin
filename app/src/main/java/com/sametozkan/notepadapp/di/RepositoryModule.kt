package com.sametozkan.notepadapp.di

import com.sametozkan.notepadapp.data.datasource.local.dao.LabelDao
import com.sametozkan.notepadapp.data.datasource.local.dao.NoteDao
import com.sametozkan.notepadapp.data.datasource.local.dao.NoteLabelXRefDao
import com.sametozkan.notepadapp.data.repository.LabelRepositoryImpl
import com.sametozkan.notepadapp.data.repository.NoteLabelXRefRepositoryImpl
import com.sametozkan.notepadapp.data.repository.NoteRepositoryImpl
import com.sametozkan.notepadapp.domain.repository.LabelRepository
import com.sametozkan.notepadapp.domain.repository.NoteLabelXRefRepository
import com.sametozkan.notepadapp.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepositoryImpl(noteDao)
    }

    @Provides
    @Singleton
    fun provideLabelRepository(labelDao: LabelDao): LabelRepository {
        return LabelRepositoryImpl(labelDao)
    }

    @Provides
    @Singleton
    fun provideNoteLabelXRefRepository(noteLabelXRefDao: NoteLabelXRefDao)
            : NoteLabelXRefRepository {
        return NoteLabelXRefRepositoryImpl(noteLabelXRefDao)
    }
}