package com.tas.icecave.general.sharedPreferences;

import com.tas.icecaveLibrary.general.Consts;

import android.content.SharedPreferences;

public class MusicSharedWorker extends BaseSharedWorker
{

	public MusicSharedWorker(SharedPreferences shared)
	{
		super(shared);
		mKey = Consts.MUSIC_MUTE_FLAG;
	}

	@Override
	public boolean getBoolean(String key)
	{
		boolean result = mShared.getBoolean(key, false);
		
		// No validity check
		return result;
	}

	@Override
	public float getFloat(String key)
	{
		return 0;
	}

	@Override
	public int getInt(String key)
	{
		return 0;
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
		mShared.edit().putBoolean(key, value).commit();
	}

	@Override
	public void putFloat(String key, float value)
	{
	}

	@Override
	public void putInt(String key, int value)
	{
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
