package com.revolve44.firstrunsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    var frstrunfrag : FirstrunFragment = FirstrunFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFirstrun()
    }

    private fun showFirstrun() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, frstrunfrag, FirstrunFragment.FRAGMENT_TAG)
            .commit()
    }
}