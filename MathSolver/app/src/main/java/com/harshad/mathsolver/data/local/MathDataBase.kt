package com.harshad.mathsolver.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ResultEntity::class], version = 1, exportSchema = true)
abstract class MathDataBase : RoomDatabase() {
    abstract fun getMathDao(): MathDao

    companion object {
        private var INSTANCE: MathDataBase? = null

        fun getMathDatabaseInstance(context: Context): MathDataBase {
            return if (INSTANCE == null) {
                val builder = Room.databaseBuilder(
                    context.applicationContext, MathDataBase::class.java, "MathDB"
                )
                builder.fallbackToDestructiveMigration()
                INSTANCE = builder.build()
                INSTANCE!!
            } else {
                INSTANCE!!
            }
        }
    }

}