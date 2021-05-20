package auth.apple.app

import auth.apple.app.model.AuthPayload
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import retrofit2.Retrofit
import java.net.HttpURLConnection


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
@ExperimentalSerializationApi
class ExampleUnitTest {
    @Test
    fun test() {
        runBlocking {
            val mockWebServer = MockWebServer()

            val appleAuth = Retrofit.Builder()
                //.baseUrl("https://appleid.apple.com/")
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                .build()
                .create<AppleAuth>()

            mockWebServer.enqueue(MockResponse().apply {
                setResponseCode(HttpURLConnection.HTTP_OK)
                setBody("""{
                    "access_token": "",
                    "expires_in": 0,
                    "id_token": "",
                    "refresh_token": "",
                    "token_type": ""
                    }""".trimMargin()
                )
            })
            val token = appleAuth.getAuthToken(AuthPayload(
                clientId = "",
                clientSecret = "",
                grantType = "password",
            ))
            println(token)

            mockWebServer.enqueue(MockResponse().apply {
                setResponseCode(HttpURLConnection.HTTP_OK)
                setBody("""{
                    "keys": {
                        "alg": "",
                        "e": "",
                        "kid": "",
                        "kty": "",
                        "n": "",
                        "use": ""
                    }
                    }""".trimMargin()
                )
            })
            val keys = appleAuth.getAuthKeys()
            println(keys)
        }
    }
}