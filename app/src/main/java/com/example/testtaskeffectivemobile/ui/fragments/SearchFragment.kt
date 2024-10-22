package com.example.testtaskeffectivemobile.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskeffectivemobile.R
import com.example.testtaskeffectivemobile.data.api.RetrofitClient
import com.example.testtaskeffectivemobile.data.model.Offer
import com.example.testtaskeffectivemobile.data.model.Vacancy
import com.example.testtaskeffectivemobile.ui.adapters.OfferAdapter
import com.example.testtaskeffectivemobile.ui.adapters.VacancyAdapter
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var offersRecyclerView: RecyclerView
    private lateinit var offerAdapter: OfferAdapter

    private lateinit var vacancyRecyclerView: RecyclerView
    private lateinit var vacancyAdapter: VacancyAdapter

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

        offersRecyclerView = view.findViewById(R.id.offersRecyclerView)
        offerAdapter = OfferAdapter()
        offersRecyclerView.adapter = offerAdapter

        vacancyRecyclerView = view.findViewById(R.id.vacanciesRecyclerView)
        vacancyAdapter = VacancyAdapter()
        vacancyRecyclerView.adapter = vacancyAdapter

        var testOffers: List<Offer>
        var testVacancies: List<Vacancy>
        lifecycleScope.launch {
            val dataFromApi = RetrofitClient.instance.loadData()
            testOffers = dataFromApi.offers
            testVacancies = dataFromApi.vacancies
            Log.d("SearchFragment", testOffers.toString())
            Log.d("SearchFragment", testVacancies.toString())
            offerAdapter.offers = testOffers
            vacancyAdapter.vacancies = testVacancies.take(3)
        }

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