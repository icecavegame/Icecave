package com.android.icecave.utils;

public class ThemeHolder
{
	int mThemeId;
	String mThemeName;
	
	public ThemeHolder(int themeId, String themeName) {
		mThemeId = themeId;
		mThemeName = new String(themeName);
	}

	public int getThemeId()
	{
		return mThemeId;
	}

	public String getThemeName()
	{
		return mThemeName;
	}
}
