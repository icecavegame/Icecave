package com.tas.icecave.gui;

import com.tas.icecave.general.PlayerThemes;
import com.tas.icecave.general.TileThemes;

import com.tas.icecave.R;

import com.tas.icecave.error.ExceptionHandler;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.tas.icecave.general.MusicService;
import com.tas.icecave.general.sharedPreferences.SharedPreferencesFactory;
import com.tas.icecave.gui.levels.ILevelSelect;
import com.tas.icecave.gui.levels.RadioButtonLevelSelect;
import com.tas.icecaveLibrary.general.Consts;

public class MainActivity extends Activity
{
	private boolean mIsBound = false;
	private MusicService mServ;
	private ServiceConnection mScon;
	private Intent mIntent;
	private ILevelSelect mLevelSelect;
	private static MainActivity sActivity;

	private void doBindService()
	{
		bindService(new Intent(this, MusicService.class), mScon, Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	private void doUnbindService()
	{
		if (mIsBound)
		{
			unbindService(mScon);
			mIsBound = false;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Set an exception handler for this activity first of all
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

		// Set static variable
		sActivity = this;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		// Hide the Status Bar
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		ImageView optionsActivity = (ImageView) findViewById(R.id.options_button);
		ImageView gameActivity = (ImageView) findViewById(R.id.game_starter);
		TextView gameTitle = (TextView) findViewById(R.id.game_title);

		// Set styles
		Typeface tf = Typeface.createFromAsset(getAssets(), Consts.STYLE_SNOW_TOP);
		gameTitle.setTypeface(tf);
		// gameActivity.setTypeface(tf);
		// optionsActivity.setTypeface(tf);

		// Load levels dynamically from EDifficulty class
		mLevelSelect = new RadioButtonLevelSelect();
		
		// Since we are limiting to one theme (at least for now) this is preset initially
		// Save selected player theme, and game theme
		final PlayerThemes playerThemes = new PlayerThemes();
		final TileThemes tileThemes = new TileThemes();
		SharedPreferencesFactory.getInstance().set(Consts.PLAYER_SELECT_TAG, playerThemes.getThemeId(0));
		SharedPreferencesFactory.getInstance().set(Consts.THEME_SELECT_TAG, tileThemes.getThemeId(2));

		optionsActivity.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mIntent = new Intent(v.getContext(), OptionsActivity.class);

				// No animation between activities
				mIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivityForResult(mIntent, 0);

			}
		});

		gameActivity.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (mLevelSelect.isChecked())
				{
					mIntent = new Intent(v.getContext(), GameActivity.class);

					// No animation between activities
					mIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

					// Load selection from prefs if exists
					mIntent.putExtra(Consts.LEVEL_SELECT_TAG,
							(Integer) SharedPreferencesFactory.getInstance().get(Consts.LEVEL_SELECT_TAG));
					mIntent.putExtra(Consts.SELECT_BOARD_SIZE_SIZE,
							(Integer) SharedPreferencesFactory.getInstance()
									.get(Consts.SELECT_BOARD_SIZE_SIZE));
					startActivityForResult(mIntent, 0);
				} else {
					Toast.makeText(v.getContext(), R.string.no_level_select_msg, Toast.LENGTH_SHORT).show();
				}
			}
		});

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

	public static MainActivity getMainActivity()
	{
		return sActivity;
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

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);

		mLevelSelect.refresh();
	}

	@Override
	protected void onDestroy()
	{
		// Stop music
		if (mServ != null && isFinishing())
		{
			mServ.pauseMusic();
		}

		// Unbind activity from service
		doUnbindService();
		super.onDestroy();
	}

	@Override
	protected void onResume()
	{
		// Enable/disable music when resuming this activity
		if (mServ != null)
		{
			initMusic();
		}

		// Load levels dynamically from EDifficulty class
		// TODO: Can this be null on onResume? if not then change calling
		// the CTOR here to calling a load function.
		mLevelSelect.refresh();

		// Reset intent
		mIntent = null;

		super.onResume();
	}

	@Override
	protected void onPause()
	{
		// Stop music but not if user switched activities, therefore, if intent is null, the user didn't switch to another activity
		if (mServ != null && mIntent == null)
		{
			mServ.pauseMusic();
		}

		super.onPause();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// No fucking animation!
		overridePendingTransition(0, 0); // 0 for no animation

		super.onActivityResult(requestCode, resultCode, data);
	}

	// private void lockView(View view, boolean flag) {
	// view.setClickable(flag);
	// }
}
