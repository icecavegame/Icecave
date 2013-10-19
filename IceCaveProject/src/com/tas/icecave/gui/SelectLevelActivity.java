package com.tas.icecave.gui;

import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.view.Window;
import android.view.WindowManager;
import com.tas.icecave.R;
import com.tas.icecave.error.ExceptionHandler;

import android.widget.RelativeLayout.LayoutParams;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import com.tas.icecave.general.MusicService;
import com.tas.icecave.general.sharedPreferences.SharedPreferencesFactory;
import com.tas.icecaveLibrary.general.Consts;

public class SelectLevelActivity extends Activity
{
	private MapSelectLayout[][] mMapSelectButtons;
	private boolean mIsBound = false;
	private MusicService mServ;
	private ServiceConnection mScon;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Set an exception handler for this activity first of all
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.tiles_layout);
		final int MARGIN = 12;

		// TODO Get data from intent to load stats of all maps of current package. That means: Number of maps, locked/unlocked, score
		// mMapSelectButtons = new MapSelectLayout[][];

		// Set all buttons
		for (int i = 0; i < mMapSelectButtons.length; i++)
		{
			for (int j = 0; j < mMapSelectButtons[i].length; j++)
			{
				// mMapSelectButtons[i][j] = new MapSelectLayout(this, mapIndex, isLocked, scorePercent);
				mMapSelectButtons[i][j].setId(View.generateViewId());
				LayoutParams buttonParams =
						new LayoutParams(new RelativeLayout.LayoutParams(	RelativeLayout.LayoutParams.WRAP_CONTENT,
																			RelativeLayout.LayoutParams.WRAP_CONTENT));
				buttonParams.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);

				// Check corners
				if (i == 0)
				{
					buttonParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				} else
				{
					buttonParams.addRule(RelativeLayout.BELOW, mMapSelectButtons[i - 1][j - 1].getId());
				}
				if (j == 0)
				{
					buttonParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				} else
				{
					buttonParams.addRule(RelativeLayout.RIGHT_OF, mMapSelectButtons[i][j - 1].getId());
				}

				mMapSelectButtons[i][j].setLayoutParams(buttonParams);

				// Set click event for button
				mMapSelectButtons[i][j].setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{

					}
				});
			}
		}

		mScon = new ServiceConnection()
		{

			public void onServiceConnected(ComponentName name, IBinder binder)
			{
				mServ = ((MusicService.ServiceBinder) binder).getService();

				// Initialize music for the first time when starting this activity
				initMusic();
			}

			public void onServiceDisconnected(ComponentName name)
			{
				mServ = null;
			}
		};

		// Bind music service to activity
		doBindService();
		Intent music = new Intent();
		music.setClass(this, MusicService.class);
		startService(music);
	}

	@Override
	protected void onResume()
	{
		// Resume/pause music
		if (mServ != null)
		{
			initMusic();
		}

		super.onResume();
	}

	@Override
	protected void onPause()
	{
		// Stop music
		if (mServ != null)
		{
			mServ.pauseMusic();
		}

		super.onPause();
	}

	@Override
	protected void onDestroy()
	{
		doUnbindService();
		super.onDestroy();
	}

	private void initMusic()
	{
		// Initialize music (or pause it) according to saved selection
		if ((Boolean) SharedPreferencesFactory.getInstance().get(Consts.MUSIC_MUTE_FLAG))
		{
			mServ.pauseMusic();
		} else
		{
			mServ.startMusic();
		}
	}

	private void doBindService()
	{
		bindService(new Intent(this, MusicService.class), mScon, Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	private void doUnbindService()
	{
		// Unbind activity from service
		if (mIsBound)
		{
			unbindService(mScon);
			mIsBound = false;
		}
	}

	/**
	 * Layout displaying the selectable map data - image, previous score, locked/unlocked, etc.
	 * 
	 * @author Sagie
	 * 
	 */
	class MapSelectLayout extends RelativeLayout
	{

		public MapSelectLayout(Context context)
		{
			super(context);
		}

		/**
		 * Initializes map select layout to show all there is to show about the current map
		 * 
		 * @param context
		 *            Context
		 * @param mapIndex
		 *            Index of map
		 * @param isLocked
		 *            Is locked or is playable by user
		 * @param scorePercent
		 *            Best recorded score (in percent) by user if already been played at least once. NO_SCORE if not yet completed
		 */
		public MapSelectLayout(Context context, int mapIndex, boolean isLocked, double scorePercent)
		{
			super(context);
		}
	}
}
