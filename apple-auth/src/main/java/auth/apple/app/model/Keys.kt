package auth.apple.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An object that defines a single JSON Web Key.
 */
@Serializable
data class Keys(
    /**
     * The encryption algorithm used to encrypt the token.
     */
    @SerialName("alg")
    val algorithm: String,

    /**
     * The exponent value for the RSA public key.
     */
    @SerialName("e")
    val exponent: String,
    /**
     * A 10-character identifier key, obtained from your developer account.
     */
    @SerialName("kid")
    val id: String,
    /**
     * The key type parameter setting. This must be set to "RSA".
     */
    @SerialName("kty")
    val type: String,

    /**
     * The modulus value for the RSA public key.
     */
    @SerialName("n")
    val modulus: String,

    /**
     * The intended use for the public key.
     */
    @SerialName("use")
    val use: String,
)