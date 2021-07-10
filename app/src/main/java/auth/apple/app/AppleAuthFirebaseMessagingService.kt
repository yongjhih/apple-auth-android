package auth.apple.app

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class AppleAuthFirebaseMessagingService : FirebaseMessagingService() {
    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d("AppleAuthFirebase", "Refreshed token: $token")
    }

    override fun handleIntentOnMainThread(intent: Intent): Boolean {
        val res = super.handleIntentOnMainThread(intent)
        Log.d("AppleAuthFirebase", "handleIntentOnMainThread: $res")
        return res
    }

    // received in background
    // AppleAuthFirebase: handleIntentOnMainThread: false
    // AppleAuthFirebase: handleIntent: intent.action: com.google.android.c2dm.intent.RECEIVE
    override fun handleIntent(intent: Intent) {
        super.handleIntent(intent)

        Log.d("AppleAuthFirebase", "handleIntent: intent.action: ${intent.action}")
        if (setOf(
                "com.google.firebase.MESSAGING_EVENT",
                "com.google.android.c2dm.intent.RECEIVE",
                "com.google.firebase.messaging.RECEIVE_DIRECT_BOOT",
            ).contains(intent.action)
        ) {
            Log.d("AppleAuthFirebase", "handleIntent: received push intent")
        }
    }

}