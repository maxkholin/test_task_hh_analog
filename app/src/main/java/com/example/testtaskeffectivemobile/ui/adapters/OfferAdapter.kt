package com.example.testtaskeffectivemobile.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskeffectivemobile.R
import com.example.testtaskeffectivemobile.data.model.Offer

class OfferAdapter : RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {
    enum class OffersImageId {
        near_vacancies, level_up_resume, temporary_job
    }

    interface OnOfferClickListener {
        fun onOfferCLick(offer: Offer)
    }

    private var _offers = listOf<Offer>()
    var offers: List<Offer>
        get() = _offers
        set(value) {
            _offers = value
            notifyDataSetChanged()
        }

    private var onOfferClickListener: OnOfferClickListener? = null
    fun setOnOfferClickListener(onOfferClickListener: OnOfferClickListener) {
        this.onOfferClickListener = onOfferClickListener
    }

    class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val offerIconImageView = itemView.findViewById<ImageView>(R.id.offerIconImageView)
        val offerTitleTextView = itemView.findViewById<TextView>(R.id.offerTitleTextView)
        val offerButtonText = itemView.findViewById<TextView>(R.id.offerButtonText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.offer_item,
            parent,
            false
        )
        return OfferViewHolder(view)
    }

    override fun getItemCount(): Int = _offers.size

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = _offers[position]

        if (offer.imageId != null) {
            holder.offerIconImageView.apply {
                val backgroundImageId = getBackgroundColor(offer.imageId)
                val imageId = getImageId(offer.imageId)

                setImageResource(imageId)
                background = ContextCompat.getDrawable(context ,backgroundImageId)
            }
        }

        holder.offerTitleTextView.text = offer.title?.trim()

        if (offer.button != null) {
            holder.offerTitleTextView.maxLines = 2
            holder.offerButtonText.text = offer.button.text?.trim()
        }

        holder.itemView.setOnClickListener {
            onOfferClickListener?.onOfferCLick(offer)
        }

    }

    private fun getBackgroundColor(imageId: String): Int {
        return when (imageId) {
            OffersImageId.temporary_job.name,
            OffersImageId.level_up_resume.name ->  R.drawable.rounded_background_green

            OffersImageId.near_vacancies.name -> R.drawable.rounded_background_blue

            else -> R.drawable.rounded_background_blue
        }
    }

    private fun getImageId(imageId: String): Int {
        return when (imageId) {
            OffersImageId.temporary_job.name -> android.R.drawable.ic_menu_agenda

            OffersImageId.near_vacancies.name -> android.R.drawable.ic_dialog_map

            OffersImageId.level_up_resume.name -> android.R.drawable.btn_star_big_off

            else -> R.drawable.ic_favorite_selected
        }
    }
}