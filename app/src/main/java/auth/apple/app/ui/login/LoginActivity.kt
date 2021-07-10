package auth.apple.app.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import auth.apple.app.R
import auth.apple.app.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
                .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            binding.login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                binding.username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                binding.password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            binding.loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        binding.username.afterTextChanged {
            loginViewModel.loginDataChanged(
                    binding.username.text.toString(),
                    binding.password.text.toString()
            )
        }

        binding.password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                        binding.username.text.toString(),
                        binding.password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                                binding.username.text.toString(),
                                binding.password.text.toString()
                        )
                }
                false
            }

            binding.login.setOnClickListener {
                binding.loading.visibility = View.VISIBLE
                loginViewModel.login(binding.username.text.toString(), binding.password.text.toString())
            }

            binding.signInApple.setOnClickListener {
                startActivity(Intent(this@LoginActivity, AppleAuthActivity::class.java))
            }
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Login", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = "Yo"
            Log.d("Login", msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

        Log.d("Login", "onCreate")
        onIntent(intent)
    }

    //Login: onIntent: intent?.extras?.keySet(): android.util.MapCollections$KeySet@564a0026
    //Login: onIntent: intent?.extras?.keySet().forEach: 0: google.delivered_priority
    //Login: onIntent: intent?.extras?.keySet().forEach: 1: google.sent_time
    //Login: onIntent: intent?.extras?.keySet().forEach: 2: google.ttl
    //Login: onIntent: intent?.extras?.keySet().forEach: 3: google.original_priority
    //Login: onIntent: intent?.extras?.keySet().forEach: 4: from
    //Login: onIntent: intent?.extras?.keySet().forEach: 5: google.message_id
    //Login: onIntent: intent?.extras?.keySet().forEach: 6: gcm.n.analytics_data
    //Login: onIntent: intent?.extras?.keySet().forEach: 7: collapse_key
    //Login: onIntent: intent?.action: android.intent.action.MAIN
    //Login: Yo

    private fun onIntent(intent: Intent?) {
        Log.d("Login", "onIntent: intent?.extras?.keySet(): ${intent?.extras?.keySet()}")
        Log.d("Login", "onIntent: intent?.action: ${intent?.action}")
        intent?.extras?.keySet()?.forEachIndexed { i, it ->
            Log.d("Login", "onIntent: intent?.extras?.keySet().forEach: ${i}: ${it}")
        }
        if (intent?.extras?.keySet()?.containsAny(
                "messageId",
                "google.message_id",
                "message_id",
                "notification",
            ) == true) {
            Log.d("Login", "onIntent: intent?.extras?.keySet()?.containsAny notification")
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("Login", "onNewIntent")

        onIntent(intent)
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
                applicationContext,
                "$welcome $displayName",
                Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun <T> Collection<T>.containsAny(vararg elements: T): Boolean {
    return containsAny(elements.toSet())
}

/**
 * Returns true if the receiving collection contains any of the elements in the specified collection.
 *
 * @param elements the elements to look for in the receiving collection.
 * @return true if any element in [elements] is found in the receiving collection.
 */
fun <T> Collection<T>.containsAny(elements: Collection<T>): Boolean {
    val set = if (elements is Set) elements else elements.toSet()
    return any(set::contains)
}
