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

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        setupRecyclerView(view)
        setupFavoriteVacanciesCount(view)
        observeFavoriteVacancies()
    }

    /** Настраивает RecyclerView для отображения избранных вакансий. */
    private fun setupRecyclerView(view: View) {
        vacancyRecyclerView = view.findViewById(R.id.vacanciesRecyclerView)
        vacancyAdapter = VacancyAdapter(mainViewModel)
        vacancyRecyclerView.adapter = vacancyAdapter
    }

    /** Настраивает отображение количества избранных вакансий. */
    private fun setupFavoriteVacanciesCount(view: View) {
        favoriteVacanciesCount = view.findViewById(R.id.favoriteVacanciesCount)
        mainViewModel.favoriteVacanciesCount.observe(viewLifecycleOwner) { text ->
            favoriteVacanciesCount.text = text
        }
    }

    /** Подписка на обновления списка избранных вакансий. */
    private fun observeFavoriteVacancies() {
        mainViewModel.favoriteVacancies.observe(viewLifecycleOwner) { favoriteVacancies ->
            vacancyAdapter.vacancies = favoriteVacancies
        }
    }
}
