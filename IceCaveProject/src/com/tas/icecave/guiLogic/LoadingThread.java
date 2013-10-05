package com.tas.icecave.guiLogic;

import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;

import android.content.res.AssetManager;
import android.os.SystemClock;

import com.tas.icecave.general.Consts;
import com.tas.icecave.gui.GameActivity;
import com.tas.icecave.gui.GameTheme;
import com.tas.icecaveGeneral.utils.AutoObservable;
import com.tas.icecaveGeneral.utils.Point;
import com.tas.icecaveGeneral.utils.UpdateDataBundle;

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
		
		// Shuffle the theme.
		mTheme.getThemeMap().shuffle();
		
		// Create new stage
		//mBoardManager.newStage(mPlayerPosition, mWallWidth, mActivity, mTheme);
		try
		{
			// TODO: Map name should be calculated by hash of meta data
			// and index (0 - number of maps).
			
			AssetManager assetManager = mActivity.getResources().getAssets();
			InputStream inputStream = null;

	        inputStream = assetManager.open("0");
            if ( inputStream != null){
    			mBoardManager.newStage(inputStream,
						   mActivity,
						   mTheme);
            }
            else
            {
            	return;
            }
		}
		catch (StreamCorruptedException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();

			// TODO HANDLE ERROR
			return;
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			// TODO HANDLE ERROR
			e1.printStackTrace();
			return;
		}
		catch (ClassNotFoundException e1)
		{
			// TODO Auto-generated catch block
			// TODO HANDLE ERROR
			e1.printStackTrace();
			return;
		}
		
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
