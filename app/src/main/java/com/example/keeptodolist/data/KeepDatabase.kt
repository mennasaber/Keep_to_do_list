package com.example.keeptodolist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Task::class, TasksList::class],version = 3,exportSchema = false)
abstract class KeepDatabase : RoomDatabase() {
    abstract fun keepDao():KeepDao
    companion object{
        @Volatile
        var instance: KeepDatabase? = null
        fun getDatabase(context: Context):KeepDatabase{
            val tempInstance = instance
            if (tempInstance!=null)
                return tempInstance
            synchronized(this){
                instance = Room.databaseBuilder(context,KeepDatabase::class.java,"keep_database").build()
                return instance as KeepDatabase
            }
        }
    }
}