package com.example.storyapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.Locator
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityRegisterBinding
import com.example.storyapp.ui.login.LoginActivity
import com.example.storyapp.utils.ResultState
import com.example.storyapp.utils.launchAndCollectIn

class RegisterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<RegisterViewModel>(factoryProducer = { Locator.registerViewModelFactory })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btRegister.isEnabled = false

        viewModel.registerViewState.launchAndCollectIn(this) {
            when (it.resultRegisterUser) {
                is ResultState.Success<String> -> {
                    binding.btRegister.setLoading(false)
                    Toast.makeText(
                        this@RegisterActivity,
                        getString(R.string.regist_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(
                        Intent(
                            this@RegisterActivity, LoginActivity::class.java
                        ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    )
                    finish()
                }

                is ResultState.Loading -> binding.btRegister.setLoading(true)
                is ResultState.Error -> {
                    binding.btRegister.setLoading(false)
                    Toast.makeText(
                        this@RegisterActivity, it.resultRegisterUser.message, Toast.LENGTH_SHORT
                    ).show()
                }

                else -> Unit
            }
        }
        binding.btRegister.setOnClickListener {
            viewModel.registerUser(
                name = binding.edRegisterName.text.toString(),
                email = binding.edRegisterEmail.text.toString(),
                password = binding.edRegisterPassword.text.toString()
            )
        }
        binding.tvHaveAccount.setOnClickListener {
            startActivity(
                Intent(
                    this, LoginActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            finish()
        }

        binding.edRegisterName.addTextChangedListener(RegisterTextWatcher)
        binding.edRegisterEmail.addTextChangedListener(RegisterTextWatcher)
        binding.edRegisterPassword.addTextChangedListener(RegisterTextWatcher)
    }

    private val RegisterTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val edRegisterPassword = binding.edRegisterPassword
            val edRegisterEmail = binding.edRegisterEmail
            val edUsername = binding.edRegisterName
            val btRegister = binding.btRegister

            if (edRegisterPassword.error == null && edRegisterEmail.error == null && edUsername.error == null && edRegisterPassword.text.toString()
                    .isNotEmpty() && edRegisterEmail.text.toString()
                    .isNotEmpty() && edUsername.text.toString().isNotEmpty()
            ) {
                btRegister.isEnabled = true
            } else {
                btRegister.isEnabled = false
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }
}