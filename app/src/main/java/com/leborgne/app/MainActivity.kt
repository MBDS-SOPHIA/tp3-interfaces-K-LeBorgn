package com.leborgne.app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.slider.Slider

class Dice(private val numSides: Int) {

    fun roll(): Int {
        return (1..numSides).random()
    }
}

/**
 * This activity allows the user to roll a dice and view the result
 * on the screen.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val valueSlider = findViewById<Slider>(R.id.slidervalue)
        //val rollButton: Button = findViewById(R.id.button)
        //rollButton.isEnabled = false
        //var valueToFound = valueSlider.value
        valueSlider.addOnSliderTouchListener( object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                if (slider.value.toInt() != 0) {
                    rollDice(slider.value.toInt())
                }
            }

        })
        /*rollButton.setOnClickListener {
            rollDice(valueToFound.toInt())
        }*/
    }

    /**
     * Roll dices and update the screen with the result.
     */
    private fun rollDice(valueToFind : Int) {
        val firstDice = Dice(6)
        val secondDice = Dice(6)
        val firstRoll = firstDice.roll()
        val secondRoll = secondDice.roll()


        // Update the screen with the dice roll
        val firstDiceTextView: TextView = findViewById(R.id.firstDice)
        val secondDiceTextView: TextView = findViewById(R.id.secondDice)
        firstDiceTextView.text = firstRoll.toString()
        secondDiceTextView.text = secondRoll.toString()

        if (firstRoll + secondRoll == valueToFind) {
            Toast.makeText(this, "You win!", Toast.LENGTH_SHORT).show()
            // make dices shake right and left
            firstDiceTextView.animate().translationXBy(20f).setDuration(100).withEndAction {
                firstDiceTextView.animate().translationXBy(-40f).setDuration(200).withEndAction {
                    firstDiceTextView.animate().translationXBy(20f).setDuration(100)
                }
            }
            secondDiceTextView.animate().translationXBy(20f).setDuration(100).withEndAction {
                secondDiceTextView.animate().translationXBy(-40f).setDuration(200).withEndAction {
                    secondDiceTextView.animate().translationXBy(20f).setDuration(100)
                }
            }
        }
    }
}