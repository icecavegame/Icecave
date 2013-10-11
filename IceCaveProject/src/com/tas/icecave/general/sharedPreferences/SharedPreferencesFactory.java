package com.tas.icecave.general.sharedPreferences;

import android.content.SharedPreferences;
import com.tas.icecave.gui.MainActivity;
import com.tas.icecaveLibrary.general.Consts;
import java.util.HashMap;

public class SharedPreferencesFactory implements ISharedWorker
{
	/**
	 * Hashmap for all the workers.
	 */
	private HashMap<Class<?>, ISharedWorker> mSharedWorkers;
	private SharedPreferences mShared;
	private static SharedPreferencesFactory sFactory = new SharedPreferencesFactory();
	
	private SharedPreferencesFactory()
	{
		mShared = MainActivity.getMainContext().getSharedPreferences(Consts.PREFS_FILE_TAG, 0);
		mSharedWorkers = new HashMap<Class<?>, ISharedWorker>();
		mSharedWorkers.put(LevelSelectSharedWorker.class, new LevelSelectSharedWorker(mShared));
		mSharedWorkers.put(MusicSharedWorker.class, new MusicSharedWorker(mShared));
		mSharedWorkers.put(PlayerSelectSharedWorker.class, new PlayerSelectSharedWorker(mShared));
		mSharedWorkers.put(SelectBoardSizeSharedWorker.class, new SelectBoardSizeSharedWorker(mShared));
		mSharedWorkers.put(ThemeSelectSharedWorker.class, new ThemeSelectSharedWorker(mShared));
		mSharedWorkers.put(DifficultyLockSharedWorker.class, new DifficultyLockSharedWorker(mShared));
	}
	
	public static SharedPreferencesFactory getInstance() {
		return sFactory;
	}

	@Override
	public boolean getBoolean(String key)
	{
		for (ISharedWorker worker : mSharedWorkers.values()) {
			if (((BaseSharedWorker)worker).isKeyMatch(key)) {
				return ((BaseSharedWorker)worker).getBoolean(key);
			}
		}
		
		return false;
	}

	@Override
	public float getFloat(String key)
	{
		for (ISharedWorker worker : mSharedWorkers.values()) {
			if (((BaseSharedWorker)worker).isKeyMatch(key)) {
				return ((BaseSharedWorker)worker).getFloat(key);
			}
		}
		
		return 0;
	}

	@Override
	public int getInt(String key)
	{
		for (ISharedWorker worker : mSharedWorkers.values()) {
			if (((BaseSharedWorker)worker).isKeyMatch(key)) {
				return ((BaseSharedWorker)worker).getInt(key);
			}
		}
		
		return 0;
	}

	@Override
	public long getLong(String key)
	{
		for (ISharedWorker worker : mSharedWorkers.values()) {
			if (((BaseSharedWorker)worker).isKeyMatch(key)) {
				return ((BaseSharedWorker)worker).getLong(key);
			}
		}
		
		return 0;
	}

	@Override
	public String getString(String key)
	{
		for (ISharedWorker worker : mSharedWorkers.values()) {
			if (((BaseSharedWorker)worker).isKeyMatch(key)) {
				return ((BaseSharedWorker)worker).getString(key);
			}
		}
		
		return null;
	}

	@Override
	public void putBoolean(String key, boolean value)
	{
		for (ISharedWorker worker : mSharedWorkers.values()) {
			if (((BaseSharedWorker)worker).isKeyMatch(key)) {
				((BaseSharedWorker)worker).putBoolean(key, value);
			}
		}
	}

	@Override
	public void putFloat(String key, float value)
	{
		for (ISharedWorker worker : mSharedWorkers.values()) {
			if (((BaseSharedWorker)worker).isKeyMatch(key)) {
				((BaseSharedWorker)worker).putFloat(key, value);
			}
		}
	}

	@Override
	public void putInt(String key, int value)
	{
		for (ISharedWorker worker : mSharedWorkers.values()) {
			if (((BaseSharedWorker)worker).isKeyMatch(key)) {
				((BaseSharedWorker)worker).putInt(key, value);
			}
		}
	}

	@Override
	public void putLong(String key, long value)
	{
		for (ISharedWorker worker : mSharedWorkers.values()) {
			if (((BaseSharedWorker)worker).isKeyMatch(key)) {
				((BaseSharedWorker)worker).putLong(key, value);
			}
		}
	}

	@Override
	public void putString(String key, String value)
	{
		for (ISharedWorker worker : mSharedWorkers.values()) {
			if (((BaseSharedWorker)worker).isKeyMatch(key)) {
				((BaseSharedWorker)worker).putString(key, value);
			}
		}
	}
}
