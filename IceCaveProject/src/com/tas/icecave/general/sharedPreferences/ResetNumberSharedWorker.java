package com.tas.icecave.general.sharedPreferences;

import com.tas.icecaveLibrary.general.Consts;

import android.content.SharedPreferences;

public class ResetNumberSharedWorker extends BaseSharedWorker
{
	public ResetNumberSharedWorker(SharedPreferences shared)
	{
		super(shared);
		mKey = Consts.RESET_SHARED_DATA_TAG;
	}

	@Override
	public Object get()
	{
		// Initial reset number value will always be 0
		final int INITIAL_RESET_NUMBER = 0;
		
		// Get the saved reset number, or initial number if none is saved
		int result = mShared.getInt(mKey, INITIAL_RESET_NUMBER);
		
		// No validity check
		return result;
	}

	@Override
	public void set(Object value)
	{
		mShared.edit().putInt(mKey, (Integer) value).commit();
	}
}
