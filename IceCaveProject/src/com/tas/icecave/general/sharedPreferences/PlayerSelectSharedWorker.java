package com.tas.icecave.general.sharedPreferences;

import com.tas.icecave.gui.MainActivity;

import android.graphics.BitmapFactory;

import com.android.icecave.R;

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
	public Object getObject()
	{
		final int DEFAULT_PLAYER = R.drawable.lior_penguin_sprite;
		
		int result = mShared.getInt(mKey, DEFAULT_PLAYER);
		
		// Validate id... very costly though
		if (BitmapFactory.decodeResource(MainActivity.getMainContext().getResources(), result) == null) {
			result = DEFAULT_PLAYER;
		}
		
		return result;
	}

	@Override
	public void putObject(Object value)
	{
		mShared.edit().putInt(mKey, (Integer) value).commit();
	}
}
