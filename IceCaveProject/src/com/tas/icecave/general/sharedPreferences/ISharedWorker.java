package com.tas.icecave.general.sharedPreferences;

public interface ISharedWorker
{
	public boolean getBoolean(String key);

	public float getFloat(String key);

	public int getInt(String key);

	public long getLong(String key);

	public String getString(String key);
	
	public void putBoolean(String key, boolean value);

	public void putFloat(String key, float value);

	public void putInt(String key, int value);

	public void putLong(String key, long value);

	public void putString(String key, String value);
}
