# Apple Auth for Android App


```kotlin
val appleAuth = Retrofit.Builder()
  .baseUrl("https://appleid.apple.com/")
  .build()
  .create<AppleAuth>()

launch {
    val appleAuthToken = appleAuth.getAuthToken(AuthPayload(
	    clientId = clientId,
	    clientSecret = clientSecret,
	    grantType = "authorization_code",
	    code = code,
	    redirectUri = redirectUri,
	))
}
```

```sh
curl -v POST "https://appleid.apple.com/auth/token" \
  -H 'content-type: application/x-www-form-urlencoded' \
  -d 'client_id=CLIENT_ID' \
  -d 'client_secret=CLIENT_SECRET' \
  -d 'code=CODE' \
  -d 'grant_type=authorization_code' \
  -d 'redirect_uri=REDIRECT_URI'
```

## References

 * See Also: https://developer.apple.com/documentation/sign_in_with_apple/generate_and_validate_tokens
 * See Also: https://developer.apple.com/documentation/sign_in_with_apple/sign_in_with_apple_rest_api/authenticating_users_with_sign_in_with_apple
 * See Also: https://developer.apple.com/documentation/sign_in_with_apple/sign_in_with_apple_rest_api/verifying_a_user
