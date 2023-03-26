package com.iothar.db.dao

import androidx.room.*
import com.iothar.db.entity.Tag
import com.iothar.db.model.TagWithNotes

@Dao
interface TagsDao {

    @Query("SELECT * FROM tags")
    suspend fun getAll(): List<Tag>

    @Query("SELECT * FROM tags WHERE tid = :tid")
    suspend fun find(tid: Int): Tag

    @Transaction
    @Query("SELECT * FROM tags")
    suspend fun getTagsWithNotes(): List<TagWithNotes>

    @Transaction
    @Query("SELECT * FROM tags WHERE tid = :tid")
    suspend fun findWithTags(tid: Int): TagWithNotes

    @Insert
    suspend fun insertTag(tag: Tag)

    @Insert
    suspend fun insertTags(tags: List<Tag>): List<Long>

    @Update
    suspend fun updateTag(tag: Tag)

    @Delete
    suspend fun deleteTag(tag: Tag)

}