package com.iothar.android.di

import android.content.Context
import androidx.room.Room
import com.iothar.data.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private const val DATABASE_NAME = "notes-database"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, NotesDatabase::class.java, DATABASE_NAME)
            .build()

    @Provides
    fun provideNotesDao(db: NotesDatabase) = db.notesDao()

    @Provides
    fun provideTagsDao(db: NotesDatabase) = db.tagsDao()

    @Provides
    fun provideNotesWithTagsDao(db: NotesDatabase) = db.notesWithTagsDao()

}