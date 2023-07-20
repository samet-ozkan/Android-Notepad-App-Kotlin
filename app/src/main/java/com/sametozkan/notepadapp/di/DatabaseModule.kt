package com.sametozkan.notepadapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sametozkan.notepadapp.data.datasource.local.AppDatabase
import com.sametozkan.notepadapp.data.datasource.local.dao.LabelDao
import com.sametozkan.notepadapp.data.datasource.local.dao.NoteDao
import com.sametozkan.notepadapp.data.datasource.local.dao.NoteLabelXRefDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : AppDatabase {
        return AppDatabase(context)
    }

    @Provides
    @Singleton
    fun provideLabelDao(database : AppDatabase) : LabelDao {
        return database.labelDao()
    }

    @Provides
    @Singleton
    fun provideNoteDao(database : AppDatabase) : NoteDao {
        return database.noteDao()
    }

    @Provides
    @Singleton
    fun provideNoteLabelXRefDao(database : AppDatabase) : NoteLabelXRefDao{
        return database.noteLabelXRefDao()
    }


}