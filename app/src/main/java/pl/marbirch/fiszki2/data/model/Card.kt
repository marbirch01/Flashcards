package pl.marbirch.fiszki2.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Card(
    @SerialName("polish")
    val polish: String,
    @SerialName("spanish")
    val spanish: String
)