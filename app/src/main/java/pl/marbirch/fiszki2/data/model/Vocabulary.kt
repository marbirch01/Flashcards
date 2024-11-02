package pl.marbirch.fiszki2.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Vocabulary(
    @SerialName("vocabulary")
    val vocabulary: List<Card>
)