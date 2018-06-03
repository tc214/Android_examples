package org.tc.mainstruct;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean isScroll = true;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {

        if (!isScroll) {
            return false;
        }
        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (!isScroll) {
            return false;
        }
        return super.onTouchEvent(arg0);
    }

    public void setScroll(boolean isScroll) {
        this.isScroll = isScroll;
    }

}
