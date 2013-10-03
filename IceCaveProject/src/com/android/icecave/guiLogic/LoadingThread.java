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
		// Create new stage
		mBoardManager.newStage(mPlayerPosition, mWallWidth, mActivity, mTheme);

		// Notify on completion
		mObservable.notifyObservers(new UpdateDataBundle(Consts.LOADING_LEVEL_FINISHED_UPDATE, SystemClock.currentThreadTimeMillis()));
	}
}
