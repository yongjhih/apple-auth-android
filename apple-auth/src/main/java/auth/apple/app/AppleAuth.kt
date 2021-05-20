package auth.apple.app

import auth.apple.app.model.AuthPayload
import auth.apple.app.model.JWTSet
import auth.apple.app.model.TokenResponse
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 *
 * See Also: https://developer.apple.com/documentation/sign_in_with_apple/generate_and_validate_tokens
 * See Also: https://developer.apple.com/documentation/sign_in_with_apple/sign_in_with_apple_rest_api/authenticating_users_with_sign_in_with_apple
 * See Also: https://developer.apple.com/documentation/sign_in_with_apple/sign_in_with_apple_rest_api/verifying_a_user
 *
 * ```kotlin
 * val appleAuth = Retrofit.Builder()
 *   .baseUrl("https://appleid.apple.com/")
 *   .build()
 *   .create<AppleAuth>()
 * ```
 *
 * ```sh
 * curl -v POST "https://appleid.apple.com/auth/token" \
 *   -H 'content-type: application/x-www-form-urlencoded' \
 *   -d 'client_id=CLIENT_ID' \
 *   -d 'client_secret=CLIENT_SECRET' \
 *   -d 'code=CODE' \
 *   -d 'grant_type=authorization_code' \
 *   -d 'redirect_uri=REDIRECT_URI'
 * ```
 */
interface AppleAuth {
    /**
     * Generate and Validate Tokens
     *
     * Validate an authorization grant code delivered to your app to obtain tokens, or validate an existing refresh token.
     */
    @POST("auth/token")
    suspend fun getAuthToken(@Body payload: AuthPayload): TokenResponse

    /**
     * Fetch Apple's Public Key for Verifying Token Signature
     *
     * Fetch Apple’s public key to verify the ID token signature.
     */
    @GET("auth/keys")
    suspend fun getAuthKeys(): JWTSet
}

inline fun <reified T> Retrofit.create(): T = create(T::class.java)