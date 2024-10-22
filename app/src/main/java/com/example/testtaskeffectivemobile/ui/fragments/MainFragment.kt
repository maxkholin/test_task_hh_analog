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
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskeffectivemobile.R
import com.example.testtaskeffectivemobile.data.model.Offer
import com.example.testtaskeffectivemobile.ui.adapters.OfferAdapter
import com.example.testtaskeffectivemobile.ui.adapters.VacancyAdapter
import com.example.testtaskeffectivemobile.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private lateinit var offersRecyclerView: RecyclerView
    private lateinit var offerAdapter: OfferAdapter

    private lateinit var vacancyRecyclerView: RecyclerView
    private lateinit var vacancyAdapter: VacancyAdapter

    private lateinit var mainViewModel: MainViewModel

    private lateinit var moreVacanciesButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainViewModel.loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Настройка RecyclerView для офферов
        offersRecyclerView = view.findViewById(R.id.offersRecyclerView)
        offerAdapter = OfferAdapter()
        offersRecyclerView.adapter = offerAdapter

        // Настройка RecyclerView для вакансий
        vacancyRecyclerView = view.findViewById(R.id.vacanciesRecyclerView)
        vacancyAdapter = VacancyAdapter(mainViewModel)
        vacancyRecyclerView.adapter = vacancyAdapter

        // Инициализация, установка текста кнопки, слушателя клика
        moreVacanciesButton = view.findViewById(R.id.moreVacanciesButton)

        mainViewModel.moreVacanciesButtonText.observe(viewLifecycleOwner) { text ->
            moreVacanciesButton.text = text
        }

        moreVacanciesButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_matchingVacanciesFragment)
        }


        // Подписка на сообщения об ошибках
        mainViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                mainViewModel.errorHandled()
            }
        }

        // Подписка на обновления списка офферов
        mainViewModel.offers.observe(viewLifecycleOwner) { offers ->
            offerAdapter.offers = offers
        }

        // Подписка на обновления списка вакансий
        mainViewModel.vacancies.observe(viewLifecycleOwner) { vacancies ->
            vacancyAdapter.vacancies = vacancies.take(3)
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