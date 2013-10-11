package com.tas.icecave.general.sharedPreferences;

import com.tas.icecaveLibrary.general.EDifficulty;

import com.tas.icecaveLibrary.general.Consts;

import android.content.SharedPreferences;

public class LevelSelectSharedWorker extends BaseSharedWorker
{
	public LevelSelectSharedWorker(SharedPreferences shared)
	{
		super(shared);
		mKey = Consts.LEVEL_SELECT_TAG;
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
		final int DEFAULT_LEVEL = 0;
		int result = mShared.getInt(key, DEFAULT_LEVEL);
		
		// Validity check
		if (result < 0 || result > EDifficulty.values().length) {
			result = DEFAULT_LEVEL;
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
