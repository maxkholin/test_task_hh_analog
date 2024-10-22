package com.example.testtaskeffectivemobile.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskeffectivemobile.R
import com.example.testtaskeffectivemobile.ui.adapters.VacancyAdapter
import com.example.testtaskeffectivemobile.viewmodel.MainViewModel

class MatchingVacanciesFragment : Fragment() {

    private lateinit var vacancyRecyclerView: RecyclerView
    private lateinit var vacancyAdapter: VacancyAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var vacanciesCount: TextView
    private lateinit var backArrowButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_matching_vacancies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        setupRecyclerView(view)
        setupVacanciesCount(view)
        setupBackArrowButton(view)

        observeVacancies()
    }

    /** Настраивает RecyclerView для отображения вакансий. */
    private fun setupRecyclerView(view: View) {
        vacancyRecyclerView = view.findViewById(R.id.vacanciesRecyclerView)
        vacancyAdapter = VacancyAdapter(mainViewModel)
        vacancyRecyclerView.adapter = vacancyAdapter
    }

    /** Настраивает отображение количества вакансий. */
    private fun setupVacanciesCount(view: View) {
        vacanciesCount = view.findViewById(R.id.title)
        mainViewModel.vacanciesCount.observe(viewLifecycleOwner) { text ->
            vacanciesCount.text = text
        }
    }

    /** Настраивает кнопку "назад". */
    private fun setupBackArrowButton(view: View) {
        backArrowButton = view.findViewById(R.id.backArrowButton)
        backArrowButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    /** Подписка на обновления списка вакансий. */
    private fun observeVacancies() {
        mainViewModel.vacancies.observe(viewLifecycleOwner) { vacancies ->
            vacancyAdapter.vacancies = vacancies
        }
    }
}
