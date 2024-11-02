package pl.marbirch.fiszki2.data.repositories

import android.content.Context
import pl.marbirch.fiszki2.data.local.CardsLoader
import pl.marbirch.fiszki2.data.model.Card

class CardRepository(context: Context) {
    private val cardsLader = CardsLoader(context)

    fun loadCards(): Boolean{
        return cardsLader.load()
    }

    fun getCards(): List<Card>{
        return cardsLader.get()
    }
}