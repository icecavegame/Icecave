package com.tas.icecave.gui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.tas.icecave.R;
import com.tas.icecave.error.ExceptionHandler;
import com.tas.icecave.general.MusicService;
import com.tas.icecave.general.sharedPreferences.SharedPreferencesFactory;
import com.tas.icecaveLibrary.general.Consts;

public class OptionsActivity extends Activity
{
	private boolean mIsBound = false;
	private MusicService mServ;
	private ServiceConnection mScon;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Set an exception handler for this activity first of all
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_options);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		// Hide the Status Bar
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		TextView creditsMain = (TextView) findViewById(R.id.credits_main);
		TextView creditsSecondary = (TextView) findViewById(R.id.credits_secondary);
		TextView gameAndVersion = (TextView) findViewById(R.id.game_and_version);
		final CheckBox muteMusic = (CheckBox) findViewById(R.id.muteMusic);

		// Set styles
		Typeface snowTop = Typeface.createFromAsset(getAssets(), Consts.STYLE_SNOW_TOP);
		Typeface roboto = Typeface.createFromAsset(getAssets(), Consts.STYLE_ROBOTO_CONDENSED_LIGHT);
		muteMusic.setTypeface(snowTop);
		creditsMain.setTypeface(roboto);
		gameAndVersion.setTypeface(roboto);
		creditsSecondary.setTypeface(roboto);

		String versionName = null;
		try
		{
			versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}

		// Set game and version and make a space after
		gameAndVersion.setText(getString(R.string.app_name) + " " + versionName + "\n");

		mScon = new ServiceConnection()
		{

			public void onServiceConnected(ComponentName name, IBinder binder)
			{
				mServ = ((MusicService.ServiceBinder) binder).getService();

				// Set music mode for first time. Can't count on checkbox because flag may not "change" from its initialized value
				initMusic();

				// Check according to saved data
				muteMusic.setChecked((Boolean) SharedPreferencesFactory.getInstance()
						.get(Consts.MUSIC_MUTE_FLAG));
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

		muteMusic.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				// Save selection
				SharedPreferencesFactory.getInstance().set(Consts.MUSIC_MUTE_FLAG, isChecked);

				// Play/pause music
				initMusic();
			}
		});
	}

	private void initMusic()
	{
		// Initialize music (or pause it) according to saved selection
		if (getSharedPreferences(Consts.PREFS_FILE_TAG, 0).getBoolean(Consts.MUSIC_MUTE_FLAG, false))
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

	@Override
	protected void onResume()
	{
		// Resume music if mute flag is off
		if (mServ != null &&
				!getSharedPreferences(Consts.PREFS_FILE_TAG, 0).getBoolean(Consts.MUSIC_MUTE_FLAG, false))
		{
			mServ.resumeMusic();
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
}