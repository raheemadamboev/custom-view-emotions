package xyz.teamgravity.customviewemotions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xyz.teamgravity.customviewemotions.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        button()
    }

    private fun button() {
        onHappy()
        onSad()
    }

    private fun onHappy() {
        with(binding) {
            happyB.setOnClickListener {
                faceV.happinessState = EmotionalFaceView.HAPPY
            }
        }
    }

    private fun onSad() {
        with(binding) {
            sadB.setOnClickListener {
                faceV.happinessState = EmotionalFaceView.SAD
            }
        }
    }
}