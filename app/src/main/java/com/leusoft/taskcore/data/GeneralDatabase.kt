package com.leusoft.taskcore.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class, Task::class,Event::class], version = 1, exportSchema = false)
abstract class GeneralDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun taskDao(): TaskDao
    abstract fun eventDao(): EventDao
    companion object {
        @Volatile
        private var INSTANCE: GeneralDatabase? = null

        fun getDatabase(context: Context): GeneralDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){ /// Protected from concurent execution
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GeneralDatabase::class.java,
                    "general_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}