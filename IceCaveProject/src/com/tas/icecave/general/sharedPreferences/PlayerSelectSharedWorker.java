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
	public boolean getBoolean(String key)
	{
		return false;
	}

	@Override
	public float getFloat(String key)
	{
		return 0;
	}

	@Override
	public int getInt(String key)
	{
		final int DEFAULT_PLAYER = R.drawable.lior_penguin_sprite;
		
		int result = mShared.getInt(key, DEFAULT_PLAYER);
		
		// Validate id... very costly though
		if (BitmapFactory.decodeResource(MainActivity.getMainContext().getResources(), result) == null) {
			result = DEFAULT_PLAYER;
		}
		
		return result;
	}

	@Override
	public long getLong(String key)
	{
		return 0;
	}

	@Override
	public String getString(String key)
	{
		return null;
	}

	@Override
	public void putBoolean(String key, boolean value)
	{
	}

	@Override
	public void putFloat(String key, float value)
	{
	}

	@Override
	public void putInt(String key, int value)
	{
		mShared.edit().putInt(key, value).commit();
	}

	@Override
	public void putLong(String key, long value)
	{
	}

	@Override
	public void putString(String key, String value)
	{	
	}
}
