package com.example.testtaskeffectivemobile.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testtaskeffectivemobile.R
import com.example.testtaskeffectivemobile.data.api.RetrofitClient
import com.example.testtaskeffectivemobile.data.database.VacancyDatabase
import com.example.testtaskeffectivemobile.data.model.Offer
import com.example.testtaskeffectivemobile.data.model.Vacancy
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val vacancyDao = VacancyDatabase.getInstance(application).vacancyDao()

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _moreVacanciesButtonText = MutableLiveData<String?>()
    val moreVacanciesButtonText: LiveData<String?> = _moreVacanciesButtonText

    private val _vacanciesCount = MutableLiveData<String?>()
    val vacanciesCount: LiveData<String?> = _vacanciesCount

    private val _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>> = _offers

    private val _vacancies = MutableLiveData<List<Vacancy>>()
    val vacancies: LiveData<List<Vacancy>> = _vacancies

    private val _favoriteVacanciesCount = MutableLiveData<String?>()
    val favoriteVacanciesCount: LiveData<String?> = _favoriteVacanciesCount

    val favoriteVacancies = vacancyDao.getAllFavoriteVacancies()


    fun insertVacancy(vacancy: Vacancy) {
        viewModelScope.launch {
            vacancyDao.insertVacancyToFavorite(vacancy)
            updateFavoriteVacancyCount()
        }
    }

    fun removeVacancy(vacancy: Vacancy) {
        viewModelScope.launch {
            vacancyDao.removeVacancyFromFavorite(vacancy.id)
            updateFavoriteVacancyCount()
        }
    }


    fun onClickFavorite(vacancy: Vacancy) {
        viewModelScope.launch {
            val vacancyFromDb = vacancyDao.getFavoriteVacancy(vacancy.id)
            if (vacancyFromDb == null) {
                insertVacancy(vacancy)
            } else {
                removeVacancy(vacancy)
            }
            _vacancies.value = _vacancies.value
        }
    }

    fun errorHandled() {
        _errorMessage.value = null
    }

    fun loadData() {
        viewModelScope.launch {
            try {
                val serverResponse = RetrofitClient.instance.loadData()
                val offers = serverResponse.offers
                val vacancies = serverResponse.vacancies

                _offers.postValue(offers)
                _vacancies.postValue(vacancies)

                syncFavoritesWithDb(vacancies)

                _moreVacanciesButtonText
                    .postValue("Ещё ${parseVacanciesCount(vacancies.size)}")
                _vacanciesCount.postValue(parseVacanciesCount(vacancies.size))

                val favoriteCount = parseVacanciesCount(
                    vacancies.filter { it.isFavorite }.size
                )
                _favoriteVacanciesCount.postValue(favoriteCount)

            } catch (e: Exception) {
                _errorMessage.postValue(getApplication<Application>().getString(R.string.error_loading))
                Log.d(TAG, e.toString())
            }
        }
    }

    private fun updateFavoriteVacancyCount() {
        val favoriteCount = favoriteVacancies.value?.size?.let { parseVacanciesCount(it) }
        _favoriteVacanciesCount.postValue(favoriteCount)
    }

    private suspend fun syncFavoritesWithDb(vacancies: List<Vacancy>) {
        vacancies.forEach { vacancy ->
            val vacancyFromDb = vacancyDao.getFavoriteVacancy(vacancy.id)
            if (vacancy.isFavorite && vacancyFromDb == null) {
                insertVacancy(vacancy)
            }
        }
    }

    private fun parseVacanciesCount(size: Int): String {
        val wordForm = when {
            size % 10 == 1 && size % 100 != 11 -> "вакансия"
            size % 10 in 2..4 && size % 100 !in 12..14 -> "вакансии"
            else -> "вакансий"
        }

        return "$size $wordForm"
    }
}