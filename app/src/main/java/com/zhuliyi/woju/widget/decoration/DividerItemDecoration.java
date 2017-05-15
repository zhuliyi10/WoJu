package com.zhuliyi.woju.widget.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhuliyi.woju.R;

/**
 * linearLayout decoration
 * Created by Leory on 2017/3/29.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {


    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private int mOrientation;

    public DividerItemDecoration(Context context) {
        mDivider=context.getResources().getDrawable(R.drawable.divider_grey);
        mOrientation=VERTICAL_LIST;
    }

    public DividerItemDecoration(Context context, int orientation) {
        mDivider=context.getResources().getDrawable(R.drawable.divider_grey);
        mOrientation=orientation;
    }

    public void setmDivider(Drawable divider){
        mDivider=divider;
    }
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }

    }


    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
//        if(parent.getAdapter()instanceof MineAdapter) {
//            MineAdapter mineAdapter = (MineAdapter) parent.getAdapter();
//            childCount=mineAdapter.getCount();
//        }
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if(child==null)continue;
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int bottom = child.getTop()-params.topMargin;
            final int top = bottom - mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        int childCount = parent.getChildCount();
//        if(parent.getAdapter()instanceof MineAdapter) {
//            MineAdapter mineAdapter = (MineAdapter) parent.getAdapter();
//            childCount=mineAdapter.getCount();
//        }
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if(child==null)continue;
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int right = child.getLeft()-params.leftMargin;
            final int left = right - mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemPosition = parent.getChildAdapterPosition(view);
        int headerCount = 0,footerCount = 0;
//        if(parent.getAdapter()instanceof MineAdapter){
//            headerCount = ((MineAdapter) parent.getAdapter()).getHeaderCount();
//            footerCount = ((MineAdapter) parent.getAdapter()).getFooterCount();
//        }
        if(itemPosition>headerCount&&itemPosition<parent.getAdapter().getItemCount()-footerCount){
            if (mOrientation == VERTICAL_LIST) {
                outRect.set(0, mDivider.getIntrinsicHeight(), 0,0 );
            } else {
                outRect.set(mDivider.getIntrinsicHeight(), 0,0 , 0);
            }
        }
    }
}
