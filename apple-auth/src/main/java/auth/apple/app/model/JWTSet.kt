package auth.apple.app.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class JWTSet(
    @SerialName("keys")
    val keys: Keys,
)