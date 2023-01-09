package com.example.geoquiz;

public class Question {
    private int mNextResId;
    private boolean mAnswerTrue;

    public Question(int textResId, boolean answerTrue) {
        mNextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getmNextResId() {
        return mNextResId;
    }

    public boolean ismAnswerTrue() {
        return mAnswerTrue;
    }

    public void setmNextResId(int mNextResId) {
        this.mNextResId = mNextResId;
    }

    public void setmAnswerTrue(boolean mAnswerTrue) {
        this.mAnswerTrue = mAnswerTrue;
    }
}
