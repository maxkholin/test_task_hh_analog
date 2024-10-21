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
import com.example.testtaskeffectivemobile.ui.adapters.OfferAdapter
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var offersRecyclerView: RecyclerView
    private lateinit var offerAdapter: OfferAdapter

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

        var testOffers: List<Offer>
        lifecycleScope.launch {
            testOffers = RetrofitClient.instance.loadData().offers
            Log.d("SearchFragment", testOffers.toString())
            offerAdapter.offers = testOffers
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