package com.zhuliyi.woju.widget.list;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ListAdapter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class TurnCardListView extends FrameLayout {
    final static int MAX_CHILD_COUNT = 5;
    final static int MAX_VISIBLE_COUNT = 3;
    final static int NEWER_OFFSET = -1;
    int mTouchSlop;
    private int mPosition = 0;
    private float density = getResources().getDisplayMetrics().density;

    public interface OnTurnListener {
        void onTurned(int position);
    }

    public TurnCardListView(Context context) {
        super(context);
        init(null, 0);
    }

    public TurnCardListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TurnCardListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mTouchSlop = mTouchSlop * mTouchSlop;

        setPadding(dp2px(15), dp2px(50), dp2px(15), dp2px(50));

        setClickable(true);
    }



    int dp2px(int dp) {
        return (int) (dp * density);
    }

    int[] offsetsX = {-dp2px(30), -dp2px(27), -dp2px(13), dp2px(0), dp2px(5)};
    int[] offsetsY = {-dp2px(40), -dp2px(34), -dp2px(17), dp2px(0), 0};


    float mHeight = 1f;
    float mWidth = 1f;
    Queue<View> mRecycler = new ArrayDeque<>(MAX_CHILD_COUNT);
    ListAdapter mAdapter;
    DataSetObserver mDataSetObserver = new DataSetObserver() {

        @Override
        public void onChanged() {
            turnAll();
        }
    };

    public ListAdapter getAdapter() {
        return mAdapter;
    }
    public void setAdapter(ListAdapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }
        mPosition = 0;
        turnAll();
    }
    public int getPosition() {
        return mPosition;
    }
    public void turnTo(int position) {
        if (mAdapter == null || mAdapter.getCount() == 0) {
            return;
        }
        if (position < 0) {
            position = 0;
        } else if (position >= mAdapter.getCount() - 1) {
            position = mAdapter.getCount() - 1;
        }

        if (position - mPosition >= 3) {
            turnAll(true, true, mPosition);
            mPosition = position;
        } else if (position - mPosition <= -3){
            turnAll(false, true, mPosition);
            mPosition = position;
        } else {
            new TurnAnimator().turnBy(position - mPosition);
        }
    }
    public void turnBy(int offset) {
        turnTo(mPosition + offset);
    }

    private OnTurnListener mListener;
    public void setOnTurnListener(OnTurnListener listener) {
        mListener = listener;
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        offsetsY[4] = getMeasuredHeight() - getPaddingTop();
        mHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        mWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        Log.e("ezy", "onMeasure, mWidth = " + mWidth + ", mHeight = " + mHeight);
        if (!mIsAnimating) {
            reset();
        }
    }

    float transY(int from, int to, float ratio) {
        return offsetsY[from] + (offsetsY[to] - offsetsY[from]) * ratio;
    }
    float scaleX(int from, int to, float ratio, float width) {
        float delta = offsetsX[from] + (offsetsX[to] - offsetsX[from]) * ratio;
        return (width + delta) / width;
    }
    int startIndex(int count) {
        return MAX_CHILD_COUNT - count - (mPosition == 0 && count < MAX_CHILD_COUNT ? 1 : 0);
    }


    void reset() {
        int count = getChildCount();
        int start = startIndex(count);
        for (int i = 0; i < count; i++) {
            resetChild(getChildAt(i), start + i, mWidth);
        }
    }

    void resetChild(View child, int n, float width) {
        float scale = (width + offsetsX[n]) / width;
        child.setPivotY(0);
        child.setPivotX(width / 2);
        child.setScaleY(scale);
        child.setScaleX(scale);
        child.setTranslationY(offsetsY[n]);
        child.setAlpha(n == 0 ? 0 : 1);
        ViewCompat.setTranslationZ(child, n - MAX_CHILD_COUNT);
    }

    void updateChild(View child, int from, int to, float ratio) {
        float scale = scaleX(from, to, ratio, mWidth);
        child.setTranslationY(transY(from, to, ratio));
        child.setScaleX(scale);
        child.setScaleY(scale);
        child.setAlpha(to == 0 ? (1 - ratio) : 1);
    }

    void older() {
        mPosition++;
        if (mPosition != 1) {
            recycle(getChildCount() - 1);
        }
        int olderPosition = mPosition + MAX_VISIBLE_COUNT;
        if (olderPosition < mAdapter.getCount()) {
            addView(mAdapter.getView(olderPosition, mRecycler.poll(), this), 0);
        }
        if (mListener != null) {
            mListener.onTurned(mPosition);
        }
    }

    void newer() {
        mPosition--;
        if (getChildCount() == MAX_CHILD_COUNT) {
            recycle(0);
        }
        int newerPosition = mPosition + NEWER_OFFSET;
        if (newerPosition >= 0) {
            addView(mAdapter.getView(newerPosition, mRecycler.poll(), this));
        }
        if (mListener != null) {
            mListener.onTurned(mPosition);
        }
    }

    void recycle(int i) {
        View child = getChildAt(i);
        removeViewInLayout(child);
        mRecycler.offer(child);
    }


    boolean mIsAnimating;


    private float mStartX;
    private float mStartY;
    private float mRatio;
    private boolean mIsTurning;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mIsAnimating || mAdapter == null || mAdapter.getCount() == 0) {
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:
            mStartX = ev.getRawX();
            mStartY = ev.getRawY();
            mIsTurning = false;
            mRatio = 0;
            break;
        case MotionEvent.ACTION_MOVE:
            int dx = (int) (ev.getRawX() - mStartX);
            int dy = (int) (ev.getRawY() - mStartY);
            if ((dx * dx + dy * dy) > mTouchSlop) {
                mRatio = dy * 1f / getMeasuredHeight();
                turning(mRatio);
                mIsTurning = true;
                return true;
            }
            break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
            if (mIsTurning) {
                mIsTurning = false;
                float threshold = 0.1f;
                if (mRatio > threshold && mPosition < mAdapter.getCount() - 1) { // older
                    new TurnAnimator().turn(true, mRatio);
                } else if (-mRatio > threshold && mPosition > 0) { // newer
                    new TurnAnimator().turn(true, mRatio);
                } else {
                    new TurnAnimator().turn(false, mRatio);
                }
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }



    void turning(float ratio) {
        if (mPosition == 0 && ratio <= 0) {
            return;
        }
        int count = getChildCount();
        int start = startIndex(count);
        int offset = ratio > 0 ? 1 : -1;

        ratio = Math.min(1, Math.abs(ratio));

        for (int i = 0; i < count; i++) {
            int n = start + i;
            if (n == 0 && offset <= 0) {
                continue;
            }
            if (n == MAX_CHILD_COUNT - 1 && offset >= 0) {
                continue;
            }
            View child = getChildAt(i);
            float scale = scaleX(n, n + offset, ratio, mWidth);
            child.setTranslationY(transY(n, n + offset, ratio));
            child.setScaleX(scale);
            child.setScaleY(scale);
            if (n == 0 && offset >= 0) {
                child.setAlpha(ratio * 0.7f);
            } else if (n == 1 && offset <= 0) {
                child.setAlpha(1 - ratio * 0.9f);
            }
        }
    }


    void turnAll() {
        if (mAdapter == null || mAdapter.getCount() == 0) {
            return;
        }
        if (mWidth > 1) {
            turnAll(true);
            return;
        }
        addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                turnAll(true);
                removeOnLayoutChangeListener(this);
            }
        });
    }
    void turnAll(final boolean isDown, final boolean clean, int position) {
        int childCount = getChildCount();
        if (childCount == 5) {
            recycle(childCount - 1);
            recycle(0);
        } else if (childCount > 0 && position != 0) {
            recycle(childCount - 1);
        } else if (childCount > 0 && position + MAX_VISIBLE_COUNT < mAdapter.getCount() - 1) {
            recycle(0);
        }
        childCount = getChildCount();
        int initial = isDown ? 4 : 0;
        List<Animator> animators = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {

            View child = getChildAt(i);
            Animator animator = new TurnOneAnimator(child, 3 - childCount + i, initial);
            if (isDown) {
                animators.add(0, animator);
            } else {
                animators.add(animator);
            }
        }
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animators);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mIsAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsAnimating = false;
                while (getChildCount() > 0) {
                    recycle(0);
                }
                if (clean) {
                    turnAll(isDown);
                }
            }
        });
        set.start();
    }
    void turnAll(boolean isDown) {
        while (getChildCount() > 0) {
            recycle(0);
        }
        int initial = isDown ? 0 : 4;
        int count = Math.min(mAdapter.getCount() - mPosition, 4);
        List<Animator> animators = new ArrayList<>();
        for (int i = -1; i < count; i++) {
            if (mPosition + i < 0) {
                continue;
            }
            View child = mAdapter.getView(mPosition + i, mRecycler.poll(), this);
            if (child.getLayoutParams() == null) {
                child.setLayoutParams(generateDefaultLayoutParams());
            }
            addViewInLayout(child, 0, child.getLayoutParams(), true);
            if (i == -1) {
                resetChild(child, 4, mWidth);
            } else if (i == 3) {
                resetChild(child, 0, mWidth);
            } else {
                resetChild(child, initial, mWidth);
                Animator animator = new TurnOneAnimator(child, initial, 3 - i);
                if (isDown) {
                    animators.add(animator);
                } else {
                    animators.add(0, animator);
                }
            }
        }
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animators);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mIsAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsAnimating = false;
                reset();
                if (mListener != null) {
                    mListener.onTurned(mPosition);
                }
            }
        });
        set.start();
    }



    class TurnAnimator extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener, ValueAnimator.AnimatorListener  {
        boolean mIsTurn;
        float mRatio;
        int mOffset;
        public TurnAnimator() {
            addUpdateListener(this);
            addListener(this);
        }

        public void turn(boolean isTurn, float ratio) {
            mIsTurn = isTurn;
            mRatio = ratio;
            mOffset = ratio > 0 ? 1 : -1;
            setFloatValues(ratio, isTurn ? (ratio > 0 ? 1 : -1) : 0);
            start();
        }
        public void turnBy(int offset) {
            if (offset == 0) {
                return;
            }
            mIsTurn = true;
            mRatio = 0;
            mOffset = offset;
            setFloatValues(0, mOffset > 0 ? 1 : -1);
            start();
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            turning((float) animation.getAnimatedValue());
        }

        @Override
        public void onAnimationStart(Animator animation) {
            mIsAnimating = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mIsAnimating = false;
            if (mIsTurn) {
                if (mOffset > 0) {
                    older();
                    reset();
                    turnBy(mOffset - 1);
                } else {
                    newer();
                    reset();
                    turnBy(mOffset + 1);
                }
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }
    class TurnOneAnimator extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener  {
        int from;
        int to;
        View child;
        public TurnOneAnimator(View v, int f, int t) {
            child = v;
            from = f;
            to = t;
            setFloatValues(0, 1);
            setInterpolator(new LinearInterpolator());
            addUpdateListener(this);
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float ratio = (float) animation.getAnimatedValue();
            updateChild(child, from, to, ratio);
        }
    }
}
