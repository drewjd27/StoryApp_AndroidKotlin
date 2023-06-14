package com.example.storyapp.ui.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.storyapp.Locator
import com.example.storyapp.databinding.ActivitySplashScreenBinding
import com.example.storyapp.ui.login.LoginActivity
import com.example.storyapp.ui.story.StoryActivity
import com.example.storyapp.utils.ResultState
import com.example.storyapp.utils.launchAndCollectIn

class SplashScreenActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySplashScreenBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<SplashScreenViewModel>(factoryProducer = { Locator.splashScreenViewModelFactory })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        playAnimation()
        viewModel.splashScreenState.launchAndCollectIn(this) {
            if (it.resultIsLoggedIn is ResultState.Success) {
                if (it.resultIsLoggedIn.data == true) {
                    startActivity(
                        Intent(this@SplashScreenActivity, StoryActivity::class.java).addFlags(
                            Intent.FLAG_ACTIVITY_CLEAR_TOP
                        )
                    )
                    finish()
                } else {
                    startActivity(
                        Intent(this@SplashScreenActivity, LoginActivity::class.java).addFlags(
                            Intent.FLAG_ACTIVITY_CLEAR_TOP
                        )
                    )
                    finish()
                }
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.scText, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        AnimatorSet().apply {
            start()
        }
    }
}