package com.example.tmdb.ui.containeractivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tmdb.R
import com.example.tmdb.ui.homefragment.HomeFragment
import com.facebook.drawee.backends.pipeline.Fresco

class ContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_container)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, HomeFragment.newInstance(), null)
            .commit()
    }
}
