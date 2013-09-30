package com.android.icecave.general;

import com.android.icecave.gui.ITheme;

import com.android.icecave.R;
import com.android.icecave.utils.ThemeHolder;

public class TileThemes implements ITheme
{
	ThemeHolder[] mHolders;
	
	public TileThemes() {
		mHolders = new ThemeHolder[] {new ThemeHolder(R.drawable.default_tiles, "Debug Theme")};
	}

	@Override
	public int getThemeId(int index)
	{
		return mHolders[index].getThemeId();
	}

	@Override
	public String getThemeName(int index)
	{
		return mHolders[index].getThemeName();
	}
	
	@Override
	public String[] getThemeNames()
	{
		String[] result = new String[mHolders.length];
		
		// Get all names
		for (int i = 0; i < mHolders.length; i++) {
			result[i] = mHolders[i].getThemeName();
		}
		
		return result;
	}
	
	@Override
	public int getTilePositionById(int id)
	{
		int result = -1;
		
		for (int i = 0; i < mHolders.length; i++) {
			if (id == mHolders[i].getThemeId()) {
				result = i;
			}
		}
		
		return result;
	}
}