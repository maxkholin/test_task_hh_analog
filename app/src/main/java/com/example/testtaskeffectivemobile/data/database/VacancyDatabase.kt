package com.example.testtaskeffectivemobile.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testtaskeffectivemobile.data.model.Vacancy

@Database(entities = [Vacancy::class], version = 1, exportSchema = false)
abstract class VacancyDatabase: RoomDatabase() {

    companion object {
        private var instance: VacancyDatabase? = null
        private const val DB_NAME = "vacancy.db"

        fun getInstance(application: Application): VacancyDatabase {
            if (instance == null) {
                synchronized(VacancyDatabase::class) {
                    instance = buildDataBase(application)
                }
            }
            return instance!!
        }

        private fun buildDataBase(application: Application): VacancyDatabase {
            return Room.databaseBuilder(
                application.applicationContext,
                VacancyDatabase::class.java,
                DB_NAME
            ).build()
        }
    }

    abstract fun vacancyDao(): VacancyDao

}