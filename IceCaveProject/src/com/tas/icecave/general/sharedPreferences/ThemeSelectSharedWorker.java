package com.tas.icecave.general.sharedPreferences;

import com.tas.icecave.R;

import android.graphics.BitmapFactory;
import com.tas.icecave.gui.MainActivity;

import com.tas.icecaveLibrary.general.Consts;

import android.content.SharedPreferences;

public class ThemeSelectSharedWorker extends BaseSharedWorker
{

	public ThemeSelectSharedWorker(SharedPreferences shared)
	{
		super(shared);
		mKey = Consts.THEME_SELECT_TAG;
	}

	@Override
	public Object get()
	{
		final int DEFAULT_THEME = R.drawable.tileset1;
		
		int result = mShared.getInt(mKey, DEFAULT_THEME);
		
		// Validate id... very costly though
		if (BitmapFactory.decodeResource(MainActivity.getMainContext().getResources(), result) == null) {
			result = DEFAULT_THEME;
		}
		
		return result;
	}

	@Override
	public void set(Object value)
	{
		mShared.edit().putInt(mKey, (Integer) value).commit();
	}
}
