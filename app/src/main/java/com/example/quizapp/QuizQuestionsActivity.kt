package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener{

    private var userName: String? = null
    private var totalQuestions: Int? = null
    private var correctAnswers: Int = 0

    private var questionsList: ArrayList<Question>? = null
    private var currentPosition: Int = 1
    private var selectedOptionPosition: Int = 0

    private var tvQuestion: TextView? = null
    private var ivFlag: ImageView? = null

    private var progressBar: ProgressBar? = null
    private var tvProgress: TextView? = null

    private var tvOption1: TextView? = null
    private var tvOption2: TextView? = null
    private var tvOption3: TextView? = null
    private var tvOption4: TextView? = null

    private var btnSubmit: Button? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        userName = intent.getStringExtra(Constants.USER_NAME)

        tvQuestion = findViewById(R.id.tv_question)
        ivFlag = findViewById(R.id.iv_image)

        progressBar = findViewById(R.id.progress_bar)
        tvProgress = findViewById(R.id.tv_progress)

        tvOption1 = findViewById(R.id.tv_option_one)
        tvOption1?.setOnClickListener(this)

        tvOption2 = findViewById(R.id.tv_option_two)
        tvOption2?.setOnClickListener(this)

        tvOption3 = findViewById(R.id.tv_option_three)
        tvOption3?.setOnClickListener(this)

        tvOption4 = findViewById(R.id.tv_option_four)
        tvOption4?.setOnClickListener(this)

        btnSubmit = findViewById(R.id.btn_submit)
        btnSubmit?.setOnClickListener(this)



        questionsList = Constants.getQuestions()
        totalQuestions = questionsList!!.size

        setQuestion()
    }

    private fun setQuestion() {
        defaultOptionsView()
        Log.i("QuestionsList size: ", "${totalQuestions}")

        // Initialize values
        var question: Question = questionsList!![currentPosition - 1]

        progressBar?.max = totalQuestions!!
        progressBar?.progress = currentPosition
        tvProgress?.text = "$currentPosition/${totalQuestions}"

        tvQuestion?.text = question.questions
        ivFlag?.setImageResource(question.image)

        tvOption1?.text = question.optionOne
        tvOption2?.text = question.optionTwo
        tvOption3?.text = question.optionThree
        tvOption4?.text = question.optionFour

        if(currentPosition == questionsList!!.size) {
            btnSubmit?.text = "FINISH"
        } else {
            btnSubmit?.text = "SUBMIT"
        }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()

        tvOption1?.let{
            options.add(0, it)
        }
        tvOption2?.let{
            options.add(1, it)
        }
        tvOption3?.let{
            options.add(2, it)
        }
        tvOption4?.let{
            options.add(3, it)
        }

        for(option in options) {
            option.setTextColor(Color.parseColor("#7a8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectedOptionView(tv: TextView, selectionOptionNum: Int) {
        defaultOptionsView()

        selectedOptionPosition = selectionOptionNum

        tv.setTextColor(Color.parseColor("#363a43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.tv_option_one -> {
                tvOption1?.let{
                    selectedOptionView(it, 1)
                }
            }
            R.id.tv_option_two -> {
                tvOption2?.let{
                    selectedOptionView(it, 2)
                }
            }
            R.id.tv_option_three -> {
                tvOption3?.let{
                    selectedOptionView(it, 3)
                }
            }
            R.id.tv_option_four -> {
                tvOption4?.let{
                    selectedOptionView(it, 4)
                }
            }

            R.id.btn_submit -> {


                if(selectedOptionPosition == 0) {
                    currentPosition++

                    when{
                        currentPosition <= questionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, userName)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, totalQuestions)
                            intent.putExtra(Constants.CORRECT_ANSWERS, correctAnswers)
                            startActivity(intent)
                            finish()
                            return
                        }
                    }
                } else {
                    val question = questionsList?.get(currentPosition - 1)

                    if(question!!.correctAnswer != selectedOptionPosition) {
                        answereView(selectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        correctAnswers++
                    }


                    answereView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if(currentPosition == questionsList!!.size) {
                        btnSubmit?.text = "FINISH"
                    } else {
                        btnSubmit?.text = "GO TO NEXT QUESTION"
                    }

                    selectedOptionPosition = 0


                }
            }

        }
    }

    private fun answereView(answere: Int, drawableView: Int) {
        when(answere) {
            1 -> {
                tvOption1?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }

            2 -> {
                tvOption2?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }

            3 -> {
                tvOption3?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }

            4 -> {
                tvOption4?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }

        }
    }
}