package com.android.icecave.guiLogic;

import com.android.icecave.utils.UpdateDataBundle;

import android.os.SystemClock;
import com.android.icecave.general.Consts;
import com.android.icecave.gui.GameActivity;
import com.android.icecave.gui.GameTheme;
import com.android.icecave.utils.AutoObservable;
import com.android.icecave.utils.Point;

public class LoadingThread extends Thread
{
	private GUIBoardManager mBoardManager;
	private Point mPlayerPosition;
	private int mWallWidth;
	private GameActivity mActivity;
	private GameTheme mTheme;
	private AutoObservable mObservable;

	public LoadingThread(GUIBoardManager boardManager,
			Point playerPosition,
			int wallWidth,
			GameActivity activity,
			GameTheme theme)
	{
		mBoardManager = boardManager;
		mPlayerPosition = playerPosition;
		mWallWidth = wallWidth;
		mActivity = activity;
		mTheme = theme;
		mObservable = new AutoObservable();
		mObservable.addObserver(mActivity);
	}

	@Override
	public void run()
	{
		final long MINIMUM_LOADING_TIME = 3500;
		
		// Create new stage
		mBoardManager.newStage(mPlayerPosition, mWallWidth, mActivity, mTheme);
		
		// Get loading time
		long loadingTime = SystemClock.currentThreadTimeMillis();

		// Sleep
		try
		{
			// If loading time took less than minimum, add a delay to the animation
			if (MINIMUM_LOADING_TIME - loadingTime > 0) {
				Thread.sleep(MINIMUM_LOADING_TIME - loadingTime);
			}
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		// Notify on completion
		mObservable.notifyObservers(new UpdateDataBundle(Consts.LOADING_LEVEL_FINISHED_UPDATE, null));
	}
}
