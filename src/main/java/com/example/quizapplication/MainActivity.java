package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tv,tvq;
    Button ba,bb,bc,bd,bs;

    int score=0;
    int totalQuestion=GKQuesAns.questions.length;
    int currentQuestionIndex=0;
    String selectedAnswer="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=findViewById(R.id.tv);
        tvq=findViewById(R.id.tvq);
        ba=findViewById(R.id.ba);
        bb=findViewById(R.id.bb);
        bc=findViewById(R.id.bc);
        bd=findViewById(R.id.bd);
        bs=findViewById(R.id.bs);

        ba.setOnClickListener(this);
        bb.setOnClickListener(this);
        bc.setOnClickListener(this);
        bd.setOnClickListener(this);
        bs.setOnClickListener(this);

        tv.setText("Total Questions: "+totalQuestion);
        loadNewQuestion();


    }

    @Override
    public void onClick(View view) {
        ba.setBackgroundColor(Color.WHITE);
        bb.setBackgroundColor(Color.WHITE);
        bc.setBackgroundColor(Color.WHITE);
        bd.setBackgroundColor(Color.WHITE);
        Button clickedButton=(Button) view;
        if(clickedButton.getId()==R.id.bs){

            if (selectedAnswer.equals("")){
                Toast.makeText(this, "Try not to skip next time!", Toast.LENGTH_SHORT).show();
            }
            else if (selectedAnswer.equals(GKQuesAns.correct_answers[currentQuestionIndex])){
                score++;
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
            }

            currentQuestionIndex++;
            loadNewQuestion();
            selectedAnswer="";



        }else {
            selectedAnswer=clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.GRAY);
        }

    }
    void loadNewQuestion(){

        if (currentQuestionIndex==totalQuestion){
            finishQuiz();
            return;
        }

        tvq.setText(GKQuesAns.questions[currentQuestionIndex]);
        ba.setText(GKQuesAns.choices[currentQuestionIndex][0]);
        bb.setText(GKQuesAns.choices[currentQuestionIndex][1]);
        bc.setText(GKQuesAns.choices[currentQuestionIndex][2]);
        bd.setText(GKQuesAns.choices[currentQuestionIndex][3]);
    }

    void finishQuiz(){
        String passStatus="";
        if (score>(totalQuestion*0.6)){
            passStatus="Pass";
        }else{
            passStatus="Fail";
        }

        new AlertDialog.Builder(this).setTitle(passStatus).setMessage("Score: "+score+"/"+totalQuestion)
               .setPositiveButton("Restart",(dialogInterface, i)->restartQuiz())
                .setNegativeButton("Home",(dialogInterface, i) -> home())
                .setCancelable(false)
                .show();
    }

    void restartQuiz(){
        score=0;
        currentQuestionIndex=0;
        loadNewQuestion();
    }
    void home(){
        score=0;
        currentQuestionIndex=0;
        startActivity(new Intent(MainActivity.this, Home.class));
    }
}