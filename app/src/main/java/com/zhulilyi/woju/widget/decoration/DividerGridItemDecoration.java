package com.zhulilyi.woju.widget.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.zhulilyi.woju.utils.ScreenUtil;

/**
 * gridLayout decoration
 * Created by Leory on 2017/3/29.
 */

public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {

    private Context context;
    public DividerGridItemDecoration(Context context) {
        this.context=context;
    }


    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }


    private boolean isLastColum(RecyclerView parent, int pos, int spanCount,
                                int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
            {
                return true;
            }
        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount==0?spanCount:childCount%spanCount;
            if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
                return true;
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition,
                               RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        if (isLastRaw(parent, itemPosition, spanCount, childCount)){// 如果是最后一行，则不需要绘制底部
            outRect.set(0, 0, 0,0 );
        }else {
            outRect.set(0, 0, 0,ScreenUtil.dip2px(context,6) );
        }
        if (isLastColum(parent, itemPosition, spanCount, childCount)){// 如果是最后一列，则不需要绘制右边
            outRect.set(0, 0, 0,0 );
        } else {
            outRect.set(0, 0,ScreenUtil.dip2px(context,6), 0 );
        }
    }
}