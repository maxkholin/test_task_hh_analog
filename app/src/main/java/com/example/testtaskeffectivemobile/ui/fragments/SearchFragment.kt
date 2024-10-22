package com.example.testtaskeffectivemobile.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskeffectivemobile.R
import com.example.testtaskeffectivemobile.data.model.Offer
import com.example.testtaskeffectivemobile.ui.adapters.OfferAdapter
import com.example.testtaskeffectivemobile.ui.adapters.VacancyAdapter
import com.example.testtaskeffectivemobile.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var offersRecyclerView: RecyclerView
    private lateinit var offerAdapter: OfferAdapter

    private lateinit var vacancyRecyclerView: RecyclerView
    private lateinit var vacancyAdapter: VacancyAdapter

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var moreVacanciesButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Настройка RecyclerView для офферов
        offersRecyclerView = view.findViewById(R.id.offersRecyclerView)
        offerAdapter = OfferAdapter()
        offersRecyclerView.adapter = offerAdapter

        // Настройка RecyclerView для вакансий
        vacancyRecyclerView = view.findViewById(R.id.vacanciesRecyclerView)
        vacancyAdapter = VacancyAdapter()
        vacancyRecyclerView.adapter = vacancyAdapter

        // Инициализация ViewModel, общая для Activity
        searchViewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]
        searchViewModel.loadData()

        moreVacanciesButton = view.findViewById(R.id.moreVacanciesButton)

        // Установка текста кнопки
        searchViewModel.moreVacanciesButtonText.observe(viewLifecycleOwner) { text ->
            moreVacanciesButton.text = text
        }

        // Подписка на сообщения об ошибках
        searchViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                searchViewModel.errorHandled()
            }
        }

        // Подписка на обновления списка офферов
        searchViewModel.offers.observe(viewLifecycleOwner) { offers ->
            offerAdapter.offers = offers
        }

        // Подписка на обновления списка вакансий
        searchViewModel.vacancies.observe(viewLifecycleOwner) { vacancies ->
            vacancyAdapter.vacancies = vacancies
        }

        // Обработка кликов на офферы
        offerAdapter.setOnOfferClickListener(object : OfferAdapter.OnOfferClickListener {
            override fun onOfferCLick(offer: Offer) {
                offer.link?.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                }
            }
        })

    }
}