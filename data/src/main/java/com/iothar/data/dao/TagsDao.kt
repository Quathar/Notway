package com.iothar.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.iothar.data.entity.Tag
import com.iothar.data.model.TagWithNotes
import kotlinx.coroutines.flow.Flow

@Dao
interface TagsDao {

    @Query("SELECT * FROM tags")
    suspend fun getAll(): List<Tag>

    @Query("SELECT * FROM tags WHERE tid = :tid")
    suspend fun find(tid: Int): Tag

    @Transaction
    @Query("SELECT * FROM tags")
    fun getTagsWithNotes(): Flow<List<TagWithNotes>>

    @Transaction
    @Query("SELECT * FROM tags WHERE tid = :tid")
    suspend fun findWithTags(tid: Int): TagWithNotes

    @Insert
    suspend fun insertTag(tag: Tag): Long

    @Insert
    suspend fun insertTags(tags: List<Tag>): List<Long>

    @Update
    suspend fun updateTag(tag: Tag)

    @Delete
    suspend fun deleteTag(tag: Tag)

}