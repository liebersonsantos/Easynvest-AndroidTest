package br.com.liebersonsantos.easynvest_androidtest.simulator.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.liebersonsantos.easynvest_androidtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)
    }
}