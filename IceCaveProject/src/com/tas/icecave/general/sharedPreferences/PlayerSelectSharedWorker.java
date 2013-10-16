package com.tas.icecave.general.sharedPreferences;

import com.tas.icecave.R;

import com.tas.icecave.gui.MainActivity;

import android.graphics.BitmapFactory;


import com.tas.icecaveLibrary.general.Consts;

import android.content.SharedPreferences;

public class PlayerSelectSharedWorker extends BaseSharedWorker
{

	public PlayerSelectSharedWorker(SharedPreferences shared)
	{
		super(shared);
		mKey = Consts.PLAYER_SELECT_TAG;
	}

	@Override
	public Object get()
	{
		final int DEFAULT_PLAYER = R.drawable.penguin_player;
		
		int result = mShared.getInt(mKey, DEFAULT_PLAYER);
		
		// Validate id... very costly though
		if (BitmapFactory.decodeResource(MainActivity.getMainActivity().getResources(), result) == null) {
			result = DEFAULT_PLAYER;
		}
		
		return result;
	}

	@Override
	public void set(Object value)
	{
		mShared.edit().putInt(mKey, (Integer) value).commit();
	}
}
