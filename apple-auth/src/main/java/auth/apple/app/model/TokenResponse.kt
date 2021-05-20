package auth.apple.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    /**
     * A token used to access allowed data, such as generating and exchanging transfer identifiers during user migration. For more information, see Transferring Your Apps and Users to Another Team and Bringing New Apps and Users into Your Team.
     */
    @SerialName("access_token")
    val accessToken: String,

    /**
     * The amount of time, in seconds, before the access token expires.
     */
    @SerialName("expires_in")
    val expiresIn: Int,

    /**
     * A JSON Web Token (JWT) that contains the user’s identity information.
     */
    @SerialName("id_token")
    val idToken: String,

    /**
     * The refresh token used to regenerate new access tokens. Store this token securely on your server.
     */
    @SerialName("refresh_token")
    val refreshToken: String,

    /**
     * The type of access token, which is always bearer.
     */
    @SerialName("token_type")
    val tokenType: String,
)