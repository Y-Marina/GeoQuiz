package com.example.geoquiz;

public class Question {
    private int mNextResId;
    private boolean mAnswerTrue;
    private int mImage;

    public Question(int textResId, boolean answerTrue, int image) {
        mNextResId = textResId;
        mAnswerTrue = answerTrue;
        mImage = image;
    }

    public int getmNextResId() {
        return mNextResId;
    }

    public int getmImage() { return mImage; }

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
