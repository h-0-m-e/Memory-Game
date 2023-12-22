package ru.h0me.memorygame.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.h0me.memorygame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
