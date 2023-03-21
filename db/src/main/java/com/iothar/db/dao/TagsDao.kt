package com.iothar.db.dao

import androidx.room.*
import com.iothar.db.entity.Tag
import com.iothar.db.model.TagWithNotes
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface TagsDao {
    @Query("SELECT * FROM tags")
    fun getAll(): Single<List<Tag>>

    @Query("SELECT * FROM tags WHERE tagId =:tagId")
    fun find(tagId: Int): Single<Tag>

    @Transaction
    @Query("SELECT * FROM tags")
    fun getTagsWithTags(): Single<List<TagWithNotes>>

    @Transaction
    @Query("SELECT * FROM tags WHERE tagId =:tagId")
    fun findWithTags(tagId: Int): Single<TagWithNotes>

    @Insert
    fun insertTag(tag: Tag): Completable

    @Update
    fun updateTag(tag: Tag): Completable

    @Delete
    fun deleteTag(tag: Tag): Completable
}