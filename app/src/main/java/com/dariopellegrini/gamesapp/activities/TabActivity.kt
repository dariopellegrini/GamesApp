package com.dariopellegrini.gamesapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dariopellegrini.gamesapp.R
import com.dariopellegrini.gamesapp.fragments.FirstFragment
import com.dariopellegrini.gamesapp.fragments.LastFragment
import com.dariopellegrini.gamesapp.fragments.SecondFragment
import com.dariopellegrini.gamesapp.fragments.ThirdFragment
import kotlinx.android.synthetic.main.activity_tab.*

class TabActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

        loadFragment(ThirdFragment())

        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_news -> loadFragment(ThirdFragment())
                R.id.navigation_categories -> loadFragment(LastFragment())
                else -> { }
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameContainer, fragment)
        transaction.commit()
    }
}