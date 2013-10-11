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
	public Object getObject()
	{
		final int DEFAULT_LEVEL = 0;
		int result = mShared.getInt(mKey, DEFAULT_LEVEL);
		
		// Validity check
		if (result < 0 || result > EDifficulty.values().length) {
			result = DEFAULT_LEVEL;
		}
		
		return result;
	}

	@Override
	public void putObject(Object value)
	{
		mShared.edit().putInt(mKey, (Integer) value).commit();
	}
}
