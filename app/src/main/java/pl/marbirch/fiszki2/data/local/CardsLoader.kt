package pl.marbirch.fiszki2.data.local

import android.content.Context
import kotlinx.serialization.json.Json
import pl.marbirch.fiszki2.R
import pl.marbirch.fiszki2.data.model.Card
import pl.marbirch.fiszki2.data.model.Vocabulary

class CardsLoader(private val context: Context) {

    private var vocabulary: Vocabulary? = Vocabulary(emptyList())
    private val json = Json {
        ignoreUnknownKeys = true
    }

    fun load(resId: Int = R.raw.vocabulary): Boolean{
        /*
            1. Załadować stringa z pliku
            2. Sparsować Stringa na obiekt
            3. Przypisać obiekt do zmiennej
         */

        val jString = loadJsonFromRaw(context, resId) ?: return false
        val parsedJson = json.decodeFromString<Vocabulary>(jString)
        vocabulary = parsedJson
        return true
    }

    private fun loadJsonFromRaw(context: Context, resId: Int): String? {
        return try {
            context.resources.openRawResource(resId).use { inputSteam ->
                val bytes = inputSteam.readBytes()
                String(bytes)
            }
        }catch (e: Exception){
            e.printStackTrace()
            null
        }

    }
    fun get(): List<Card>{
        return vocabulary?.vocabulary ?: emptyList()
    }

}