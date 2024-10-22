package com.example.testtaskeffectivemobile.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.testtaskeffectivemobile.data.model.Vacancy

@Dao
interface VacancyDao {

    @Query("SELECT * FROM favorite_vacancies")
    fun getAllFavoriteVacancies(): LiveData<List<Vacancy>>

    @Query("SELECT * FROM favorite_vacancies WHERE id = :vacancyId")
    suspend fun getFavoriteVacancy(vacancyId: String): Vacancy?

    @Insert
    suspend fun insertVacancyToFavorite(vacancy: Vacancy)

    @Query("DELETE  FROM favorite_vacancies WHERE id = :vacancyId")
    suspend fun removeVacancyFromFavorite(vacancyId: String)
}