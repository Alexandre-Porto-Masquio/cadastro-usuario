package com.apmasquio.cadastrousuario.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.apmasquio.cadastrousuario.data.dao.UserDao
import com.apmasquio.cadastrousuario.data.models.User

@Database(
    entities = [
        User::class
    ],
    version = 2,
    exportSchema = true
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var db: AppDatabase? = null
        fun dbInstance(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "usuario-expressa.db"
            )
                .build().also {
                    db = it
                }
        }
    }
}