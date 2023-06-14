package com.example.storyapp.ui.story

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.Locator
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityStoryBinding
import com.example.storyapp.ui.adapter.LoadingStateAdapter
import com.example.storyapp.ui.adapter.StoryAdapter
import com.example.storyapp.ui.addstory.AddStoryActivity
import com.example.storyapp.ui.login.LoginActivity
import com.example.storyapp.ui.maps.MapsActivity
import com.example.storyapp.utils.ResultState
import com.example.storyapp.utils.launchAndCollectIn

class StoryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityStoryBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<StoryViewModel>(factoryProducer = { Locator.storyViewModelFactory })
    private val adapter by lazy { StoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initAdapter()
        viewModel.storyState.launchAndCollectIn(this) {
            adapter.submitData(lifecycle, it.resultStories)
            binding.tvName.text = it.username
        }

        binding.fabAddStory.setOnClickListener {
            startActivity(Intent(this@StoryActivity, AddStoryActivity::class.java))
        }
        binding.actionLogout.setOnClickListener {
            AlertDialog.Builder(this).setTitle(getString(R.string.logout))
                .setMessage(getString(R.string.confirm_logout))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    viewModel.logout()
                    startActivity(Intent(this@StoryActivity, LoginActivity::class.java))
                    finish()
                }.setNegativeButton(getString(R.string.no), null).show()
        }
        binding.actionChangeLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        binding.actionMaps.setOnClickListener {
            startActivity(Intent(this@StoryActivity, MapsActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getStories()
        viewModel.getUser()
    }

    private fun initAdapter() {
        binding.rvStory.adapter = adapter.withLoadStateFooter(footer = LoadingStateAdapter {
            adapter.retry()
        })
        binding.rvStory.layoutManager = LinearLayoutManager(this)
    }
}