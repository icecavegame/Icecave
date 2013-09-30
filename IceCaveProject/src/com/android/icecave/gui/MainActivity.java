package com.android.icecave.gui;

import android.widget.RadioButton;

import com.android.icecave.general.EDifficulty;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.android.icecave.R;
import com.android.icecave.general.Consts;

public class MainActivity extends Activity
{
	SharedPreferences mShared;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		// Hide the Status Bar
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		final int DEFAULT_LEVEL = 0;

		mShared = getSharedPreferences(Consts.PREFS_FILE_TAG, 0);

		Button optionsActivity = (Button) findViewById(R.id.options_button);
		Button gameActivity = (Button) findViewById(R.id.game_button);
		RadioGroup levelSelect = (RadioGroup) findViewById(R.id.levelSelect);
		
		// Load levels dynamically from EDifficulty class
		loadLevelsToRadioGroup(levelSelect);

		// Load level selection from prefs if exists
		levelSelect.check(levelSelect.getChildAt(mShared.getInt(Consts.LEVEL_SELECT_TAG, DEFAULT_LEVEL)).getId());
		
		optionsActivity.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(v.getContext(), OptionsActivity.class));
			}
		});

		gameActivity.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(v.getContext(), GameActivity.class);
				
				// Load selection from prefs if exists
				i.putExtra(Consts.LEVEL_SELECT_TAG, mShared.getInt(Consts.LEVEL_SELECT_TAG, DEFAULT_LEVEL));
				i.putExtra(Consts.SELECT_BOARD_SIZE_X, mShared.getInt(Consts.SELECT_BOARD_SIZE_X, Consts.DEFAULT_BOARD_SIZE_X));
				i.putExtra(Consts.SELECT_BOARD_SIZE_Y, mShared.getInt(Consts.SELECT_BOARD_SIZE_Y, Consts.DEFAULT_BOARD_SIZE_Y));
				startActivity(i);
			}
		});

		levelSelect.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				// Save level to prefs
				mShared.edit().putInt(Consts.LEVEL_SELECT_TAG, group.indexOfChild(group.findViewById(checkedId))).commit();
			}
		});
	}
	
	private void loadLevelsToRadioGroup(RadioGroup levelSelect) {
		int numOfLevels = EDifficulty.values().length;
		
		// Go over levels and add them
		for(int i = 0; i < numOfLevels; i++) {
			RadioButton newButton = new RadioButton(this);
			newButton.setText(EDifficulty.values()[i].name()); // An alternative to this is to set another variable (string id) in the enum 
			levelSelect.addView(newButton);
		}
	}
}
