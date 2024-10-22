package com.example.testtaskeffectivemobile.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskeffectivemobile.R
import com.example.testtaskeffectivemobile.ui.adapters.VacancyAdapter
import com.example.testtaskeffectivemobile.viewmodel.MainViewModel

class MatchingVacanciesFragment : Fragment() {

    private lateinit var vacancyRecyclerView: RecyclerView
    private lateinit var vacancyAdapter: VacancyAdapter

    private lateinit var mainViewModel: MainViewModel

    private lateinit var vacanciesCount: TextView
//    private lateinit var

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matching_vacancies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Настройка RecyclerView для вакансий
        vacancyRecyclerView = view.findViewById(R.id.vacanciesRecyclerView)
        vacancyAdapter = VacancyAdapter()
        vacancyRecyclerView.adapter = vacancyAdapter

        // Инициализация ViewModel, общая для Activity
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]


        // Инициализация, установка текста кнопки
        vacanciesCount = view.findViewById(R.id.vacanciesCount)

        mainViewModel.vacanciesCount.observe(viewLifecycleOwner) { text ->
            vacanciesCount.text = text
        }

        // Подписка на обновления списка вакансий
        mainViewModel.vacancies.observe(viewLifecycleOwner) { vacancies ->
            vacancyAdapter.vacancies = vacancies
        }


    }
}