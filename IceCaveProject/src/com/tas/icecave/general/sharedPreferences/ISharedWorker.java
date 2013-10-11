package com.tas.icecave.general.sharedPreferences;

public interface ISharedWorker
{
	/**
	 * Get any saved object from the shared preferences
	 * @param key The key of the saved object
	 * @return Saved object from the shared preferences
	 */
	public Object get();
	
	/**
	 * Save object to the shared preferences
	 * @param key The key of the object to save
	 * @param value The value to save to the shared preferences
	 */
	public void set(Object value);
}
