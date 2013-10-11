package com.tas.icecave.general.sharedPreferences;

import android.content.SharedPreferences;

public abstract class BaseSharedWorker implements ISharedWorker
{
	protected SharedPreferences mShared;
	protected String mKey;
	
	public BaseSharedWorker(SharedPreferences shared) {
		mShared = shared;
	}
	
	public boolean isKeyMatch(String newKey) {
		return mKey.equals(newKey);
	}
	
	public String getKey() {
		return mKey;
	}
}
