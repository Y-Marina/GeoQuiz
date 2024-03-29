package com.example.geoquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_COUNT_CHEAT = "KEY_COUNT_CHEAT";
    private static final int REQUEST_CODE_CHEAT = 0;
    private boolean mIsCheater;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private ImageView mImageView;
    private int countCheat = 3;


    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.q1, false, R.drawable.osminog),
            new Question(R.string.q2, true, R.drawable.bambuka),
            new Question(R.string.q3, true, R.drawable.comp),
            new Question(R.string.q4, true, R.drawable.zebra),
            new Question(R.string.q5, false, R.drawable.beley_bear),
            new Question(R.string.q6, true, R.drawable.muesh),
            new Question(R.string.q7, true, R.drawable.pen),
            new Question(R.string.q8, true, R.drawable.utenok),
            new Question(R.string.q9, false, R.drawable.bogomol)
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mImageView = (ImageView) findViewById(R.id.image_view);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);

        mTrueButton.setOnClickListener(v -> {
           checkAnswer(true);
        });

        mFalseButton.setOnClickListener(v -> {
            checkAnswer(false);
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
               updateQuestion();
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start CheatActivity
                if (countCheat == 0) {
                    Toast.makeText(MainActivity.this, "подсказки закончились ¯\\_(ツ)_/¯", Toast.LENGTH_SHORT).show();
                } else {
                    countCheat = countCheat - 1;
                    boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismAnswerTrue();
                    Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                    startActivityForResult(intent, REQUEST_CODE_CHEAT);
                }
            }
        });

        updateQuestion();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        countCheat = savedInstanceState.getInt(KEY_COUNT_CHEAT, 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(KEY_COUNT_CHEAT, countCheat);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getmNextResId();
        int imege = mQuestionBank[mCurrentIndex].getmImage();
        mQuestionTextView.setText(question);
        mImageView.setImageResource(imege);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismAnswerTrue();
        int messageResId = 0;

        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}