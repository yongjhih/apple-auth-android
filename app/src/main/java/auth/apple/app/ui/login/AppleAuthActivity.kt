package auth.apple.app.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import auth.apple.app.R
import auth.apple.app.databinding.ActivityAppleAuthBinding

class AppleAuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppleAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppleAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.webView.webViewClient = WebViewClient()

        binding.webView.loadUrl("file:///android_asset/apple_auth.html")

        binding.webView.settings.javaScriptEnabled = true
    }
}