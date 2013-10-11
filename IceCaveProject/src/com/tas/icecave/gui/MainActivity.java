package com.tas.icecave.gui;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.android.icecave.R;
import com.android.icecave.error.ExceptionHandler;
import com.tas.icecave.general.MusicService;
import com.tas.icecave.general.sharedPreferences.SharedPreferencesFactory;
import com.tas.icecaveLibrary.general.Consts;
import com.tas.icecaveLibrary.general.EDifficulty;

public class MainActivity extends Activity
{
	private boolean mIsBound = false;
	private MusicService mServ;
	private ServiceConnection mScon;
	private Intent mIntent;
	private RadioGroup mLevelSelect;
	private static Context sContext;

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
		sContext = this;

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
		mLevelSelect = (RadioGroup) findViewById(R.id.level_select);

		// Set styles
		 Typeface tf = Typeface.createFromAsset(getAssets(), Consts.STYLE_SNOW_TOP);
		 gameTitle.setTypeface(tf);
		// gameActivity.setTypeface(tf);
		// optionsActivity.setTypeface(tf);

		// Load levels dynamically from EDifficulty class
		loadLevelsToRadioGroup();

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
				mIntent = new Intent(v.getContext(), GameActivity.class);

				// No animation between activities
				mIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

				// Load selection from prefs if exists
				mIntent.putExtra(Consts.LEVEL_SELECT_TAG, (Integer) SharedPreferencesFactory.getInstance()
						.getObject(Consts.LEVEL_SELECT_TAG));
				mIntent.putExtra(Consts.SELECT_BOARD_SIZE_SIZE,
						(Integer) SharedPreferencesFactory.getInstance()
								.getObject(Consts.SELECT_BOARD_SIZE_SIZE));
				startActivityForResult(mIntent, 0);
			}
		});

		mLevelSelect.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				// Save level to prefs
				SharedPreferencesFactory.getInstance().setObject(Consts.LEVEL_SELECT_TAG,
						group.indexOfChild(group.findViewById(checkedId)));
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

	public static Context getMainContext()
	{
		return sContext;
	}

	private void initMusic()
	{
		// Initialize music (or pause it) according to saved selection
		if ((Boolean) SharedPreferencesFactory.getInstance().getObject(Consts.MUSIC_MUTE_FLAG))
		{
			mServ.pauseMusic();
		} else
		{
			mServ.startMusic();
		}
	}

	private void loadLevelsToRadioGroup()
	{
		int numOfLevels = EDifficulty.values().length;

		// Clear before creating buttons
		mLevelSelect.removeAllViews();

		// Go over levels and add them
		for (int i = 0; i < numOfLevels; i++)
		{
			RadioButton newButton = new RadioButton(this);
			newButton.setText(EDifficulty.values()[i].name()); // An alternative to this is to set another variable (string id) in the enum
			newButton.setTextColor(getResources().getColor(R.color.white));
			mLevelSelect.addView(newButton);
		}

		// Lock hardest difficulty if user never solved a level before
		lockView(mLevelSelect.getChildAt(mLevelSelect.getChildCount() - 1),
				(Boolean) SharedPreferencesFactory.getInstance().getObject(Consts.LOCK_HARD_DIFFICULTY));
	}

	private void lockView(View view, boolean flag)
	{
		// Do not allow clicking on this view. Not effective with toast called on click...
		// view.setClickable(flag);

		// If true make view it look clickable
		if (flag)
		{
			((RadioButton) view).setTextColor(getResources().getColor(R.color.white));
		} else
		{
			// Make view look unclickable
			((RadioButton) view).setTextColor(getResources().getColor(R.color.dark_red));

			view.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// Cancel check of this view
					Toast.makeText(v.getContext(), R.string.level_lock_warning, Toast.LENGTH_LONG).show();

					// Select medium level.. This is a bad way to do this but in the future when packages are going to be used
					// There won't be any more radio buttons and so this wouldn't matter
					mLevelSelect.check(mLevelSelect.getChildAt(EDifficulty.Medium.ordinal()).getId());
				}
			});
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);

		// Load level selection from prefs if exists
		mLevelSelect.check(mLevelSelect.getChildAt((Integer) SharedPreferencesFactory.getInstance()
				.getObject(Consts.LEVEL_SELECT_TAG)).getId());
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
		loadLevelsToRadioGroup();

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
