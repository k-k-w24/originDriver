package com.transportcompany.app

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.transportcompany.app.databinding.ActivityLoginBinding
import com.transportcompany.app.utils.Constants

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            Log.d(TAG, "onCreate started")
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)
            Log.d(TAG, "Layout inflated successfully")

            sharedPreferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE)

            // 既にログインしている場合はメイン画面へ（一時的に無効化してデバッグ）
            val isLoggedIn = sharedPreferences.getBoolean(Constants.KEY_IS_LOGGED_IN, false)
            Log.d(TAG, "Is logged in: $isLoggedIn")
            
            // デバッグ用：自動ログインを無効化
            // if (isLoggedIn) {
            //     navigateToMain()
            //     return
            // }

            binding.loginButton.setOnClickListener {
                performLogin()
            }
            Log.d(TAG, "onCreate completed successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error in onCreate", e)
            Log.e(TAG, "Error message: ${e.message}")
            Log.e(TAG, "Error stack trace: ${e.stackTraceToString()}")
            e.printStackTrace()
            try {
                Toast.makeText(this, "エラーが発生しました: ${e.message}", Toast.LENGTH_LONG).show()
            } catch (toastError: Exception) {
                Log.e(TAG, "Could not show toast", toastError)
            }
            finish()
        }
    }

    private fun performLogin() {
        val username = binding.usernameEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "ユーザー名とパスワードを入力してください", Toast.LENGTH_SHORT).show()
            return
        }

        // 簡易的なログイン処理（実際のアプリではAPI呼び出しに置き換えてください）
        if (validateCredentials(username, password)) {
            // ログイン成功
            sharedPreferences.edit().apply {
                putString(Constants.KEY_USERNAME, username)
                putBoolean(Constants.KEY_IS_LOGGED_IN, true)
                apply()
            }
            navigateToMain()
        } else {
            Toast.makeText(this, "ログインに失敗しました", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateCredentials(username: String, password: String): Boolean {
        // 簡易的な認証（実際のアプリではAPI呼び出しに置き換えてください）
        // デフォルトのログイン情報
        val defaultUsername = "admin"
        val defaultPassword = "admin123"
        
        // デフォルトの認証情報でログイン、またはパスワードが4文字以上でも可（開発用）
        return (username == defaultUsername && password == defaultPassword) ||
               (username.isNotEmpty() && password.length >= 4)
    }

    private fun navigateToMain() {
        try {
            Log.d(TAG, "Navigating to DeliveryListActivity")
            val intent = Intent(this, DeliveryListActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Log.e(TAG, "Error navigating to main", e)
            Log.e(TAG, "Error message: ${e.message}")
            Log.e(TAG, "Error stack trace: ${e.stackTraceToString()}")
            Toast.makeText(this, "画面遷移エラー: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}

