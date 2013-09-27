package com.android.icecave.general;

import android.graphics.Bitmap;

public class SelectTilesTheme
{
	Bitmap mTheme;
	
	public void setThemeId(Bitmap theme) {
		mTheme = theme;
	}
	
	public Bitmap getTheme() {
		return mTheme;
	}
}
