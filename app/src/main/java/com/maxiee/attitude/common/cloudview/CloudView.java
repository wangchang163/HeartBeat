package com.maxiee.attitude.common.cloudview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.maxiee.attitude.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by maxiee on 15-6-24.
 */

@RemoteViews.RemoteView
public class CloudView extends ViewGroup {

    private List<Pair<String, Integer>> mLabels;

    private static final int WEIGHT = 10;

    public CloudView(Context context) {
        super(context);
    }

    public CloudView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CloudView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addLabels(List<Pair<String, Integer>> labels) {
        mLabels = labels;

        for (Pair<String, Integer> label: labels) {
            TextView tagView = new TextView(getContext());
            tagView.setText(label.first);
            tagView.setTextSize(label.second * WEIGHT);
            LayoutParams layoutParams = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(5, 5, 5, 5);
            tagView.setLayoutParams(layoutParams);
            addView(tagView);
        }


    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int mViewGroupWidth = getMeasuredWidth();
        int mViewGroupHeight = getMeasuredHeight();

        int heightMax = 0;
        int mPainterPosX = l;
        int mPainterPosY = t;

        int childCount = getChildCount();
        for (int i=0; i<childCount; i++) {
            View childView = getChildAt(i);
            int width = childView.getMeasuredWidth();
            int height = childView.getMeasuredHeight();

            LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();

            if (height > heightMax) {
                heightMax = height;
            }

            if (mPainterPosX + layoutParams.leftMargin + width + layoutParams.rightMargin > mViewGroupWidth) {
                mPainterPosX = l;
                mPainterPosY += heightMax;
                heightMax = 0;
            }

            childView.layout(
                    mPainterPosX + layoutParams.leftMargin,
                    mPainterPosY + layoutParams.topMargin,
                    mPainterPosX + width + layoutParams.rightMargin,
                    mPainterPosY + height + layoutParams.bottomMargin
            );

            mPainterPosX += width + layoutParams.leftMargin + layoutParams.rightMargin;
        }
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ViewGroup.LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }
    }
}