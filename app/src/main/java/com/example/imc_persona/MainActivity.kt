package com.example.imc_persona

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private var isManSelected: Boolean = true
    private var isWomanSelected: Boolean = false
    private var currentTime: Int = 30

    private lateinit var man: CardView
    private lateinit var woman: CardView
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var btnSubtract: FloatingActionButton
    private lateinit var btnPlus: FloatingActionButton
    private lateinit var tvWeight: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnResult = findViewById<Button>(R.id.btnResult)


        initComponents()
        initListeners()
        initUi()


        btnResult.setOnClickListener() {

            val weight = tvWeight.text.toString().toDoubleOrNull()
            val height = tvHeight.text.toString().toDoubleOrNull()

            if (weight != null && height != null && height != 0.0) {
                val imc = weight / (height * height)

                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("IMC", imc.toString())
                startActivity(intent)
            }
        }
    }

    private fun initComponents() {
        man = findViewById(R.id.man)
        woman = findViewById(R.id.woman)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)
        btnSubtract = findViewById(R.id.btnSubtract)
        btnPlus = findViewById(R.id.btnPlus)
        tvWeight = findViewById(R.id.tvWeight)
    }

    private fun initListeners() {
        man.setOnClickListener {
            changeTempUp()
            setColor()
        }
        woman.setOnClickListener {
            changeTempDown()
            setColor()
        }
        rsHeight.addOnChangeListener { _, value, _ ->
            val df = DecimalFormat("#.##")
            val result = df.format(value)
            tvHeight.text = result
        }

        btnPlus.setOnClickListener {
            currentTime += 1
            setTime()
        }
        btnSubtract.setOnClickListener {
            if (currentTime > 1) {
                currentTime -= 1
                setTime()
            }
        }
    }

    private fun setTime() {
        tvWeight.text = currentTime.toString()
    }

    private fun changeTempUp() {
        isWomanSelected = false
        isManSelected = true
    }

    private fun changeTempDown() {
        isManSelected = false
        isWomanSelected = true
    }

    private fun setColor() {
        val tempUpColor = if (isManSelected) {
            ContextCompat.getColor(this, R.color.background_component_selected)
        } else {
            ContextCompat.getColor(this, R.color.background_component)
        }
        man.setCardBackgroundColor(tempUpColor)

        val tempDownColor = if (isWomanSelected) {
            ContextCompat.getColor(this, R.color.background_component_selected)
        } else {
            ContextCompat.getColor(this, R.color.background_component)
        }
        woman.setCardBackgroundColor(tempDownColor)
    }

    private fun initUi() {
        rsHeight.valueFrom = 0f
        rsHeight.valueTo = 250f
        rsHeight.stepSize = 1f
        setColor()
        setTime()
    }
}
