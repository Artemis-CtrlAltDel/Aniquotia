package com.example.cmo.presentation.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.cmo.R
import com.example.cmo.databinding.ActivityMainBinding
import com.example.cmo.other.replaceFragment
import com.example.cmo.presentation.ui.fragments.CollectionDetailsFragment
import com.example.cmo.presentation.ui.fragments.RemoteQuotesFragment
import com.example.cmo.presentation.ui.fragments.SavedQuotesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        bindViews()
        replaceFragment(this, RemoteQuotesFragment(), getString(R.string.fragment_remote_title))
        handleActions()

        setContentView(binding.root)
    }

    private fun bindViews() {

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setSupportActionBar(binding.includeToolbar.toolbar)

        binding.bottomNavigation.selectedItemId = R.id.menu_remote
        binding.includeToolbar.goBack.isVisible = false
    }

    private fun handleActions() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_remote -> replaceFragment(
                    this,
                    RemoteQuotesFragment(),
                    getString(R.string.fragment_remote_title),
                    ""
                )
                R.id.menu_bookmark -> replaceFragment(
                    this,
                    SavedQuotesFragment(),
                    getString(R.string.fragment_saved_title),
                    ""
                )
            }
            true
        }
    }

}