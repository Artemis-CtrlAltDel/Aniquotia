package com.example.cmo.presentation.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cmo.R
import com.example.cmo.databinding.ActivityMainBinding
import com.example.cmo.other.bookmarkQuote
import com.example.cmo.other.replaceFragment
import com.example.cmo.presentation.ui.adapters.MainAdapter
import com.example.cmo.presentation.ui.adapters.OnItemClick
import com.example.cmo.presentation.ui.fragments.RemoteQuotesFragment
import com.example.cmo.presentation.ui.fragments.SavedQuotesFragment
import com.example.cmo.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()

        replaceFragment(RemoteQuotesFragment())

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId){
                R.id.menu_remote -> replaceFragment(RemoteQuotesFragment())
                R.id.menu_bookmark -> replaceFragment(SavedQuotesFragment())
            }
            true
        }

        setContentView(binding.root)
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
    }
}