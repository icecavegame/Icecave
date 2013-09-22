package com.android.icecave.general;

import android.graphics.drawable.Drawable;

public class SelectTheme
{
	Drawable mTheme;
	
	public void setThemeId(Drawable theme) {
		mTheme = theme;
	}
	
	public Drawable getTheme() {
		return mTheme;
	}
}
