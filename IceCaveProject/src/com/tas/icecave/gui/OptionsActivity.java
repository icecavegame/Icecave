package com.tas.icecave.gui;

import android.graphics.Typeface;

import android.widget.TextView;

import com.android.icecave.error.ExceptionHandler;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;

import com.android.icecave.R;
import com.tas.icecave.general.MusicService;
import com.tas.icecave.general.PlayerThemes;
import com.tas.icecave.general.TileThemes;
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

		Spinner themeSelect = (Spinner) findViewById(R.id.select_theme);
		TextView creditsMain = (TextView) findViewById(R.id.credits_main);
		TextView creditsSecondary = (TextView) findViewById(R.id.credits_secondary);
		TextView gameAndVersion = (TextView) findViewById(R.id.game_and_version);
		TextView selectThemeText = (TextView) findViewById(R.id.select_theme_text);
		final CheckBox muteMusic = (CheckBox) findViewById(R.id.muteMusic);

		final PlayerThemes playerThemes = new PlayerThemes();
		final TileThemes tileThemes = new TileThemes();
		final SharedPreferences shared = getSharedPreferences(Consts.PREFS_FILE_TAG, 0);
		
		// Set styles
		Typeface iceAge = Typeface.createFromAsset(getAssets(), Consts.STYLE_SNOW_TOP);
		Typeface robotoThin = Typeface.createFromAsset(getAssets(), Consts.STYLE_ROBOTO_THIN);
		muteMusic.setTypeface(iceAge);
		selectThemeText.setTypeface(iceAge);
		creditsMain.setTypeface(robotoThin);
		gameAndVersion.setTypeface(robotoThin);
		creditsSecondary.setTypeface(robotoThin);
		
		// Set game and version and make a space after
		gameAndVersion.setText(getString(R.string.app_name) + " " + getString(R.string.version_number) + "\n");

		ArrayAdapter<String> tileAdapter =
				new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		tileAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		tileAdapter.addAll(tileThemes.getThemeNames());
		themeSelect.setAdapter(tileAdapter);

//		ArrayAdapter<String> playerAdapter =
//				new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
//		playerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		playerAdapter.addAll(playerThemes.getThemeNames());
//		playerThemeSelect.setAdapter(playerAdapter);

		// Set initial values in spinners
	themeSelect.setSelection(tileThemes.getTilePositionById(shared.getInt(Consts.THEME_SELECT_TAG,
			tileThemes.getThemeId(0))));
//		playerThemeSelect.setSelection(playerThemes.getTilePositionById(shared.getInt(Consts.PLAYER_SELECT_TAG,
//				playerThemes.getThemeId(0))));

		themeSelect.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			boolean isInitialized = false;

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
			{
				// Avoid automatic selected of first item at creation
				if (isInitialized)
				{
					// Save selected tile theme
					shared.edit().putInt(Consts.THEME_SELECT_TAG, tileThemes.getThemeId(pos)).commit();
				}

				isInitialized = true;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
		
		// Since we are limiting to one player tileset (at least for now) this is preset initially
		// Save selected player theme
		shared.edit().putInt(Consts.PLAYER_SELECT_TAG, playerThemes.getThemeId(0)).commit();

//		playerThemeSelect.setOnItemSelectedListener(new OnItemSelectedListener()
//		{
//			boolean isInitialized = false;
//
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
//			{
//				// Avoid automatic selected of first item at creation
//				if (isInitialized)
//				{
//					// Save selected player theme
//					shared.edit().putInt(Consts.PLAYER_SELECT_TAG, playerThemes.getThemeId(pos)).commit();
//				}
//
//				isInitialized = true;
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0)
//			{
//			}
//		});

		mScon = new ServiceConnection()
		{

			public void onServiceConnected(ComponentName name, IBinder binder)
			{
				mServ = ((MusicService.ServiceBinder) binder).getService();
				
				// Set music mode for first time. Can't count on checkbox because flag may not "change" from its initialized value
				initMusic();

				// Check according to saved data
				muteMusic.setChecked(shared.getBoolean(Consts.MUSIC_MUTE_FLAG, false));
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
					shared.edit().putBoolean(Consts.MUSIC_MUTE_FLAG, isChecked).commit();

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
		if (mServ != null && !getSharedPreferences(Consts.PREFS_FILE_TAG, 0).getBoolean(Consts.MUSIC_MUTE_FLAG, false))
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