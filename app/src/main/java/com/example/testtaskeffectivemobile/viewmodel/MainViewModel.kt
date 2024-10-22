package com.example.testtaskeffectivemobile.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testtaskeffectivemobile.R
import com.example.testtaskeffectivemobile.data.api.RetrofitClient
import com.example.testtaskeffectivemobile.data.model.Offer
import com.example.testtaskeffectivemobile.data.model.Vacancy
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel(application: Application) : AndroidViewModel(application) {
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

    private val _favoriteVacancies = MutableLiveData<List<Vacancy>>()
    val favoriteVacancies: LiveData<List<Vacancy>> = _favoriteVacancies

    private val _favoriteVacanciesCount = MutableLiveData<String?>()
    val favoriteVacanciesCount: LiveData<String?> = _favoriteVacanciesCount

//    fun onClickFavorite(vacancy: Vacancy) {
//        val updatedVacancies = _vacancies.value?.map {
//            if (it.id == vacancy.id) {
//                it.copy(isFavorite = !it.isFavorite)
//            } else {
//                it
//            }
//        } ?: emptyList()
//        _vacancies.postValue(updatedVacancies)
//        _favoriteVacancies.postValue(updatedVacancies.filter { it.isFavorite })
//
//        val count = _favoriteVacancies.value?.size?.let { parseVacanciesCount(it) }
//        _favoriteVacanciesCount.postValue(count)
//    }

    fun errorHandled() {
        _errorMessage.value = null
    }

    fun loadData() {
        viewModelScope.launch {
            try {
                val serverResponse = RetrofitClient.instance.loadData()
                _offers.postValue(serverResponse.offers)
                _vacancies.postValue(serverResponse.vacancies)

                _moreVacanciesButtonText
                    .postValue("Ещё ${parseVacanciesCount(serverResponse.vacancies.size)}")

                _vacanciesCount.postValue(parseVacanciesCount(serverResponse.vacancies.size))

                val favoriteVacanciesCount = parseVacanciesCount(
                    serverResponse.vacancies.filter { it.isFavorite }.size
                )
                _favoriteVacanciesCount.postValue(favoriteVacanciesCount)

            } catch (e: Exception) {
                _errorMessage
                    .postValue(getApplication<Application>().getString(R.string.error_loading))

                Log.d(TAG, e.toString())
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