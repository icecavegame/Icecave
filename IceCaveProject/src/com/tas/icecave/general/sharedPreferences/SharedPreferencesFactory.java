package com.tas.icecave.general.sharedPreferences;

import android.content.SharedPreferences;
import com.tas.icecave.gui.MainActivity;
import com.tas.icecaveLibrary.general.Consts;
import java.util.HashMap;

public class SharedPreferencesFactory
{
	/**
	 * Hashmap for all the workers.
	 */
	private HashMap<String, ISharedWorker> mSharedWorkers;
	private SharedPreferences mShared;
	private static SharedPreferencesFactory sFactory = new SharedPreferencesFactory();

	private SharedPreferencesFactory()
	{
		mShared = MainActivity.getMainContext().getSharedPreferences(Consts.PREFS_FILE_TAG, 0);
		mSharedWorkers = new HashMap<String, ISharedWorker>();
		LevelSelectSharedWorker lsWorker = new LevelSelectSharedWorker(mShared);
		MusicSharedWorker mWorker = new MusicSharedWorker(mShared);
		PlayerSelectSharedWorker psWorker = new PlayerSelectSharedWorker(mShared);
		SelectBoardSizeSharedWorker sbsWorker = new SelectBoardSizeSharedWorker(mShared);
		ThemeSelectSharedWorker tsWorker = new ThemeSelectSharedWorker(mShared);
		DifficultyLockSharedWorker dlWorker = new DifficultyLockSharedWorker(mShared);

		mSharedWorkers.put(lsWorker.getKey(), lsWorker);
		mSharedWorkers.put(mWorker.getKey(), mWorker);
		mSharedWorkers.put(psWorker.getKey(), psWorker);
		mSharedWorkers.put(sbsWorker.getKey(), sbsWorker);
		mSharedWorkers.put(tsWorker.getKey(), tsWorker);
		mSharedWorkers.put(dlWorker.getKey(), dlWorker);
	}

	public static SharedPreferencesFactory getInstance()
	{
		return sFactory;
	}

	public Object getObject(String key)
	{

		if (mSharedWorkers.containsKey(key))
		{
			return mSharedWorkers.get(key).getObject();
		}

		return null;
	}

	public void setObject(String key, Object value)
	{
		if (mSharedWorkers.containsKey(key))
		{
			mSharedWorkers.get(key).putObject(value);
		}
	}
}
