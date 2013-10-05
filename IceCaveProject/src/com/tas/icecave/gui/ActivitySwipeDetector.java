package com.tas.icecave.gui;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ActivitySwipeDetector implements View.OnTouchListener
{

	static final String logTag = "ActivitySwipeDetector";
	private ISwipeDetector activity;
	static final int MIN_DISTANCE = 20;
	private float downX, downY, upX, upY;

	public ActivitySwipeDetector(ISwipeDetector activity)
	{
		this.activity = activity;
	}

	public void onRightToLeftSwipe(View v)
	{
		Log.i(logTag, "RightToLeftSwipe!");
		activity.right2left(v);
	}

	public void onLeftToRightSwipe(View v)
	{
		Log.i(logTag, "LeftToRightSwipe!");
		activity.left2right(v);
	}

	public void onTopToBottomSwipe(View v)
	{
		Log.i(logTag, "onTopToBottomSwipe!");
		activity.top2bottom(v);
	}

	public void onBottomToTopSwipe(View v)
	{
		Log.i(logTag, "onBottomToTopSwipe!");
		activity.bottom2top(v);
	}

	public boolean onTouch(View v, MotionEvent event)
	{
		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
		{
			downX = event.getX();// * event.getXPrecision();
			downY = event.getY();// * event.getYPrecision();
			return true;
		}
		case MotionEvent.ACTION_UP:
		{
			upX = event.getX();// * event.getXPrecision();
			upY = event.getY();// * event.getYPrecision();

			float deltaX = downX - upX;
			float deltaY = downY - upY;

			// Check which delta is larger!
			if (Math.abs(deltaX) >= Math.abs(deltaY))
			{
				// swipe horizontal?
				if (Math.abs(deltaX) > MIN_DISTANCE)
				{
					// left or right
					if (deltaX < 0)
					{
						this.onLeftToRightSwipe(v);
						return true;
					}
					if (deltaX > 0)
					{
						this.onRightToLeftSwipe(v);
						return true;
					}
				}
				else
				{
					Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long, need at least " +
							MIN_DISTANCE);
				}
			}
			else
			{
				// swipe vertical?
				if (Math.abs(deltaY) > MIN_DISTANCE)
				{
					// top or down
					if (deltaY < 0)
					{
						this.onTopToBottomSwipe(v);
						return true;
					}
					if (deltaY > 0)
					{
						this.onBottomToTopSwipe(v);
						return true;
					}
				}
				else
				{
					Log.i(logTag, "Swipe was only " + Math.abs(deltaX) + " long, need at least " +
							MIN_DISTANCE);
				}
			}
		}
		}
		return false;
	}

}
