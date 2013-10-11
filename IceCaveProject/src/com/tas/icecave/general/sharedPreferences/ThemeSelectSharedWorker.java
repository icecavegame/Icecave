package com.tas.icecave.general.sharedPreferences;

import android.graphics.BitmapFactory;
import com.android.icecave.R;
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
	public Object getObject()
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
	public void putObject(Object value)
	{
		mShared.edit().putInt(mKey, (Integer) value).commit();
	}
}
