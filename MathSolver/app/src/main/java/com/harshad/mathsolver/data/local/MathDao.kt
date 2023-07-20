package com.harshad.mathsolver.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MathDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveResult(resultEntity: ResultEntity)

    @Query("SELECT * FROM RESULT_TABLE")
    suspend fun getAllResults(): List<ResultEntity>

    @Delete
    suspend fun deleteResultRecord(resultEntity: ResultEntity)

}