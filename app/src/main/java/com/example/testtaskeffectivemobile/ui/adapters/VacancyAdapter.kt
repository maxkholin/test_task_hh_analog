package com.example.testtaskeffectivemobile.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskeffectivemobile.R
import com.example.testtaskeffectivemobile.data.model.Vacancy
import java.text.SimpleDateFormat
import java.util.Locale

class VacancyAdapter() :
    RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder>() {

    class VacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val peopleLookingTextView = itemView.findViewById<TextView>(R.id.peopleLookingTextView)
        val vacancyTitleTextView = itemView.findViewById<TextView>(R.id.vacancyTitleTextView)
        val townTextView = itemView.findViewById<TextView>(R.id.townTextView)
        val companyTextView = itemView.findViewById<TextView>(R.id.companyTextView)
        val experienceTextView = itemView.findViewById<TextView>(R.id.experienceTextView)
        val dateTextView = itemView.findViewById<TextView>(R.id.dateTextView)

        val favoriteButton = itemView.findViewById<ImageView>(R.id.favoriteButton)
    }

    interface OnFavoriteButtonClickListener {
        fun onFavoriteButtonClick()
    }

    private var _vacancies = listOf<Vacancy>()
    var vacancies: List<Vacancy>
        get() = _vacancies
        set(value) {
            _vacancies = value
            notifyDataSetChanged()
        }

//    var onFavoriteButtonClickListener: OnFavoriteButtonClickListener? = null
//
//    fun setOnFavoriteButtonClickListener(onFavoriteButtonClickListener: OnFavoriteButtonClickListener) {
//        this.onFavoriteButtonClickListener = onFavoriteButtonClickListener
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.vacancy_item,
            parent,
            false
        )

        return VacancyViewHolder(view)
    }

    override fun getItemCount(): Int = _vacancies.size

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        val vacancy = _vacancies[position]

        with(holder) {
            val peopleWatching = vacancy.lookingNumber?.let { parseLookingPeople(it) }
            peopleLookingTextView.text = peopleWatching

            vacancyTitleTextView.text = vacancy.title

            townTextView.text = vacancy.address?.town

            companyTextView.text = vacancy.company

            experienceTextView.text = vacancy.experience?.previewText

            val date = vacancy.publishedDate?.let { parseDate(it) }
            dateTextView.text = date

            favoriteButton.setImageResource(
                if (vacancy.isFavorite == true) R.drawable.ic_favorite_selected
                else R.drawable.ic_favorite_unselected
            )

//            favoriteButton.setOnClickListener {
//                onFavoriteButtonClickListener?.onFavoriteButtonClick()
//            }
        }
    }

    private fun parseLookingPeople(count: Int): String {
        val suffix = when {
            count % 100 in 11..19 -> "человек"
            count % 10 == 1 -> "человек"
            count % 10 in 2..4 -> "человека"
            else -> "человек"
        }
        return "Сейчас просматривает $count $suffix"
    }

    private fun parseDate(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d MMMM", Locale.getDefault())

        val parsedDate = inputFormat.parse(date)
        return parsedDate?.let { "Опубликовано ${outputFormat.format(it)}" } ?: ""
    }
}