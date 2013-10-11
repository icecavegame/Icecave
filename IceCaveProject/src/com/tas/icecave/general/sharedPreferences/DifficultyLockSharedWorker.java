package com.tas.icecave.general.sharedPreferences;

import com.tas.icecaveLibrary.general.Consts;

import android.content.SharedPreferences;

public class DifficultyLockSharedWorker extends BaseSharedWorker
{

	public DifficultyLockSharedWorker(SharedPreferences shared)
	{
		super(shared);
		mKey = Consts.LOCK_HARD_DIFFICULTY;
	}

	@Override
	public Object getObject()
	{
		boolean result = mShared.getBoolean(mKey, false);
		
		// No validity check
		return result;
	}

	@Override
	public void putObject(Object value)
	{
		mShared.edit().putBoolean(mKey, (Boolean) value).commit();
	}
}
