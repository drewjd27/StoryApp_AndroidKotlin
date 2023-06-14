package com.example.storyapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.Locator
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityLoginBinding
import com.example.storyapp.ui.register.RegisterActivity
import com.example.storyapp.ui.story.StoryActivity
import com.example.storyapp.utils.ResultState
import com.example.storyapp.utils.launchAndCollectIn

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<LoginViewModel>(factoryProducer = { Locator.loginViewModelFactory })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btLogin.isEnabled = false

        viewModel.loginState.launchAndCollectIn(this) { state ->
            when (state.resultVerifyUser) {
                is ResultState.Loading -> binding.btLogin.setLoading(true)
                is ResultState.Success<String> -> {
                    binding.btLogin.setLoading(false)
                    startActivity(
                        Intent(
                            this@LoginActivity, StoryActivity::class.java
                        )
                    )
                    finish()
                }
                is ResultState.Error -> {
                    binding.btLogin.setLoading(false)
                    Toast.makeText(
                        this@LoginActivity, state.resultVerifyUser.message, Toast.LENGTH_SHORT
                    ).show()
                }
                else -> Unit
            }

        }

        binding.btLogin.setOnClickListener {
            viewModel.doLogin(
                email = binding.edLoginEmail.text.toString(),
                password = binding.edLoginPassword.text.toString()
            )
        }

        binding.tvNoAccount.setOnClickListener {
            startActivity(
                Intent(
                    this, RegisterActivity::class.java
                )
            )
        }
        binding.edLoginEmail.addTextChangedListener(loginTextWatcher)
        binding.edLoginPassword.addTextChangedListener(loginTextWatcher)
    }

    private val loginTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val edLoginEmail = binding.edLoginEmail
            val edPasswordEmail = binding.edLoginPassword
            val btLogin = binding.btLogin

            if (edLoginEmail.error == null && edPasswordEmail.error == null && edLoginEmail.text.toString().isNotEmpty() && edPasswordEmail.text.toString().isNotEmpty()
            ) {
                btLogin.isEnabled = true
            } else {
                btLogin.isEnabled = false
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }
}