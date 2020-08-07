package com.example.githubusers.feature.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubusers.R
import com.example.githubusers.core.base.BaseActivity

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initToolbar()
    }

    private fun initToolbar() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.settings, MySettingsFragment())
            .commit()

        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}