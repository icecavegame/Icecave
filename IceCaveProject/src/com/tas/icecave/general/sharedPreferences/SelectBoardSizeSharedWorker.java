package com.tas.icecave.general.sharedPreferences;

import android.content.SharedPreferences;
import com.tas.icecaveLibrary.general.Consts;

public class SelectBoardSizeSharedWorker extends BaseSharedWorker
{

	public SelectBoardSizeSharedWorker(SharedPreferences shared)
	{
		super(shared);
		
		mKey = Consts.SELECT_BOARD_SIZE_SIZE;
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
		int result = mShared.getInt(key, Consts.DEFAULT_BOARD_SIZE);
		
		// Validity check
		if (result <= 0) {
			result = Consts.DEFAULT_BOARD_SIZE;
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
