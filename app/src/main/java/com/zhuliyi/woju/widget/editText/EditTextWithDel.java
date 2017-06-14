package com.zhuliyi.woju.widget.editText;


import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.zhuliyi.woju.R;

/**
 * @author sunday
 * 2013-12-04
 */
public class EditTextWithDel extends android.support.v7.widget.AppCompatEditText {
	private final static String TAG = "EditTextWithDel";
	private Drawable imgInable;
	private Drawable imgAble;
	private Context mContext;
	private boolean visible=true;
	public EditTextWithDel(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public EditTextWithDel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	public EditTextWithDel(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}
	
	private void init() {
//		imgInable = mContext.getResources().getDrawable(R.drawable.delete_gray);
		imgAble = mContext.getResources().getDrawable(R.drawable.close_edit);
		addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {
				setDrawable();
			}
		});
		setDrawable();
	}

	private void setDrawable() {
		if(length() < 1||!visible)
			setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		else
			setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null);
	}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (visible&&imgAble != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getX();
            int eventY = (int) event.getY();
            Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
            Rect rect = new Rect();
            getLocalVisibleRect(rect);
            rect.left = rect.right - imgAble.getIntrinsicWidth()- 16;
            if(rect.contains(eventX, eventY))
            	setText("");
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public void setDelVisible(boolean visible){
		this.visible=visible;
	}

}
