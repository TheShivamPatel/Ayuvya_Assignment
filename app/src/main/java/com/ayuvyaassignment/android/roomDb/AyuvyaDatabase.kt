package com.ayuvyaassignment.android.roomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartData::class], version = 1, exportSchema = false)
abstract class AyuvyaDatabase : RoomDatabase() {

    abstract fun cartDao() : CartDao

    companion object {
        @Volatile
        private var INSTANCE: AyuvyaDatabase? = null

        fun getDatabase(context: Context): AyuvyaDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AyuvyaDatabase::class.java,
                    "ayuvya_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}