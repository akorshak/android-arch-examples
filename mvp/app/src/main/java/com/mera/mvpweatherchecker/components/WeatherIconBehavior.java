package com.mera.mvpweatherchecker.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.github.pwittchen.weathericonview.WeatherIconView;
import com.mera.mvpweatherchecker.R;

public class WeatherIconBehavior extends CoordinatorLayout.Behavior<WeatherIconView> {

    private final static String LOG_TAG = "WeatherIconBehavior";

    private Context mContext;

    private int mStartXPosition;
    private float mStartToolbarPosition;
    private int mStartYPosition;
    private int mFinalYPosition;
    private int mFinalXPosition;

    private int mIconAreaStartSize;
    private float mIconAreaFinalSize;

    private int mIconStartSize;
    private int mIconFinalSize;

    private float mChangeBehaviorPoint;

    public WeatherIconBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WeatherIconBehavior);

            mIconAreaFinalSize = a.getDimension(R.styleable.WeatherIconBehavior_finalAreaSize, 0);
            mIconStartSize = a.getInt(R.styleable.WeatherIconBehavior_startIconSize, 0);
            mIconFinalSize = a.getInt(R.styleable.WeatherIconBehavior_finalIconSize, 0);
            mChangeBehaviorPoint = a.getFloat(R.styleable.WeatherIconBehavior_changeBehaviorPoint, 0);

            a.recycle();
        }
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, WeatherIconView child, View dependency) {
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, WeatherIconView child, View dependency) {
        maybeInitProperties(child, dependency);

        int maxScrollDistance = (int) (mStartToolbarPosition);
        float expandedPercentageFactor = dependency.getY() / maxScrollDistance;

        if (expandedPercentageFactor < mChangeBehaviorPoint) {
            float heightFactor = (mChangeBehaviorPoint - expandedPercentageFactor) / mChangeBehaviorPoint;

            float distanceXToSubtract = ((mStartXPosition - mFinalXPosition) * heightFactor) + (child.getWidth()/2);
            float distanceYToSubtract = ((mStartYPosition - mFinalYPosition) * (1f - expandedPercentageFactor)) + (child.getHeight()/2);

            child.setX(mStartXPosition - distanceXToSubtract);
            child.setY(mStartYPosition - distanceYToSubtract);

            float sizeToSubtract = (mIconAreaStartSize - mIconAreaFinalSize) * heightFactor;

            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            lp.width = (int) (mIconAreaStartSize - sizeToSubtract);
            lp.height = (int) (mIconAreaStartSize - sizeToSubtract);
            child.setLayoutParams(lp);

            child.setIconSize((int) ((mIconStartSize - mIconFinalSize) / mChangeBehaviorPoint * expandedPercentageFactor + mIconFinalSize));

        } else {

            float distanceYToSubtract = ((mStartYPosition - mFinalYPosition)  * (1f - expandedPercentageFactor)) + (mIconAreaStartSize /2);

            child.setX(mStartXPosition - child.getWidth()/2 );
            child.setY(mStartYPosition - distanceYToSubtract);

            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            lp.width = mIconAreaStartSize;
            lp.height = mIconAreaStartSize;
            child.setLayoutParams(lp);

            child.setIconSize(mIconStartSize);

        }
        return true;
    }

    private void maybeInitProperties(WeatherIconView child, View dependency) {

        if(mIconAreaStartSize == 0) {
            mIconAreaStartSize = child.getHeight() > child.getWidth() ? child.getHeight() : child.getWidth();
        }

        if (mStartYPosition == 0)
            mStartYPosition = (int) (dependency.getY());

        if (mFinalYPosition == 0)
            mFinalYPosition = dependency.getHeight() / 2;

        if (mStartXPosition == 0)
            mStartXPosition = (int) (child.getX() + (child.getWidth() / 2));

        if (mFinalXPosition == 0)
            mFinalXPosition = mContext.getResources().getDimensionPixelOffset(R.dimen.abc_action_bar_content_inset_material) + ((int) mIconAreaFinalSize / 2);

        if (mStartToolbarPosition == 0)
            mStartToolbarPosition = dependency.getY();

    }
}