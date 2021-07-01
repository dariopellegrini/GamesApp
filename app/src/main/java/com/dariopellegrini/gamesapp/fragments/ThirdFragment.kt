package com.dariopellegrini.gamesapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dariopellegrini.gamesapp.R
import com.dariopellegrini.gamesapp.activities.GamesActivity
import kotlinx.android.synthetic.main.fragment_third.*

class ThirdFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val s = "Ciao"
        s.addPrefix("Prefix")

        "10/12/2020".toDate()

        date.string("dd-mm-yyyy")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        thirdButton.setOnClickListener {
            activity?.let {
                val intent = Intent(it, GamesActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

fun String.addPrefix(prefix: String): String {
    return "$prefix: $this"
}