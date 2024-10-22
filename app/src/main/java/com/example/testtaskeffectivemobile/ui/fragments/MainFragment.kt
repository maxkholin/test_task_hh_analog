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
        setupRecyclerViews(view)
        setupMoreVacanciesButton(view)
        observeViewModel()
    }

    /**
     * Инициализация и настройка RecyclerView для офферов и вакансий.
     */
    private fun setupRecyclerViews(view: View) {
        offersRecyclerView = view.findViewById(R.id.offersRecyclerView)
        offerAdapter = OfferAdapter()
        offersRecyclerView.adapter = offerAdapter

        vacancyRecyclerView = view.findViewById(R.id.vacanciesRecyclerView)
        vacancyAdapter = VacancyAdapter(mainViewModel)
        vacancyRecyclerView.adapter = vacancyAdapter

        // Обработка кликов на оффер
        offerAdapter.setOnOfferClickListener(object : OfferAdapter.OnOfferClickListener {
            override fun onOfferCLick(offer: Offer) {
                openOfferLink(offer.link)
            }
        })
    }

    /**
     * Установка слушателя клика для кнопки "Еще вакансии" и обновление текста.
     */
    private fun setupMoreVacanciesButton(view: View) {
        moreVacanciesButton = view.findViewById(R.id.moreVacanciesButton)

        mainViewModel.moreVacanciesButtonText.observe(viewLifecycleOwner) { text ->
            moreVacanciesButton.text = text
        }

        moreVacanciesButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_matchingVacanciesFragment)
        }
    }

    /**
     * Подписка на обновления из ViewModel и обработка ошибок.
     */
    private fun observeViewModel() {
        mainViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                showErrorToast(it)
                mainViewModel.errorHandled()
            }
        }

        mainViewModel.offers.observe(viewLifecycleOwner) { offers ->
            offerAdapter.offers = offers
        }

        mainViewModel.vacancies.observe(viewLifecycleOwner) { vacancies ->
            vacancyAdapter.vacancies = vacancies.take(3)
        }
    }

    /**
     * Открытие ссылки оффера в браузере.
     */
    private fun openOfferLink(link: String?) {
        link?.let { url ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    /**
     * Отображение сообщения об ошибке.
     */
    private fun showErrorToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
