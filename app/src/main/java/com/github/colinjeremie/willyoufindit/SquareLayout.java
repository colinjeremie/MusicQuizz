package com.github.colinjeremie.willyoufindit;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * * WillYouFindIt
 * Created by jerem_000 on 4/1/2016.
 */
public class SquareLayout extends RelativeLayout{
    public SquareLayout(Context context) {
        super(context);
    }

    public SquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int max = Math.min(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(max, max);
    }
}
