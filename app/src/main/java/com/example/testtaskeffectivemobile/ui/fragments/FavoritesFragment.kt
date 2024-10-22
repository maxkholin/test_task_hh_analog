package com.example.testtaskeffectivemobile.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskeffectivemobile.R
import com.example.testtaskeffectivemobile.ui.adapters.VacancyAdapter
import com.example.testtaskeffectivemobile.viewmodel.MainViewModel

class FavoritesFragment : Fragment() {

    private lateinit var vacancyRecyclerView: RecyclerView
    private lateinit var vacancyAdapter: VacancyAdapter

    private lateinit var mainViewModel: MainViewModel

    private lateinit var favoriteVacanciesCount: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация ViewModel
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        // Настройка RecyclerView для вакансий
        vacancyRecyclerView = view.findViewById(R.id.vacanciesRecyclerView)
        vacancyAdapter = VacancyAdapter(mainViewModel)
        vacancyRecyclerView.adapter = vacancyAdapter

        // Инициализация, установка текста кнопки
        favoriteVacanciesCount = view.findViewById(R.id.favoriteVacanciesCount)

        mainViewModel.favoriteVacanciesCount.observe(viewLifecycleOwner) { text ->
            favoriteVacanciesCount.text = text
        }

        // Подписка на обновления списка избранных вакансий
        mainViewModel.vacancies.observe(viewLifecycleOwner) { vacancies ->
            vacancyAdapter.vacancies = vacancies.filter { it.isFavorite }
        }
    }
}