package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)


        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 10)

        val tvName: TextView = findViewById(R.id.tv_name)
        tvName.text = intent.getStringExtra(Constants.USER_NAME)

        val tvScore: TextView = findViewById(R.id.tv_score)
        tvScore.text = "Your Score is $correctAnswers out of $totalQuestions"

        val btnFinish: Button = findViewById(R.id.btn_finish)

        btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}