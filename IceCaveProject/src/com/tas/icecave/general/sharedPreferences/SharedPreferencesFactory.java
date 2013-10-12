package com.tas.icecave.general.sharedPreferences;

import android.content.SharedPreferences;
import com.tas.icecave.gui.MainActivity;
import com.tas.icecaveLibrary.general.Consts;
import java.util.HashMap;

/**
 * Class holding all the SharedPreferences workers.
 * @author Sagie
 *
 */
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

	/**
	 * Get any saved object from the shared preferences
	 * @param key The key of the saved object
	 * @return Saved object from the shared preferences
	 */
	public Object get(String key)
	{

		if (mSharedWorkers.containsKey(key))
		{
			return mSharedWorkers.get(key).get();
		}

		return null;
	}

	/**
	 * Save object to the shared preferences
	 * @param key The key of the object to save
	 * @param value The value to save to the shared preferences
	 */
	public void set(String key, Object value)
	{
		if (mSharedWorkers.containsKey(key))
		{
			mSharedWorkers.get(key).set(value);
		}
	}
}
