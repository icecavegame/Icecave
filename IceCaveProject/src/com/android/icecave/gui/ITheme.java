package com.android.icecave.gui;

public interface ITheme
{
	public int getThemeId(int index);
	
	public String getThemeName(int index);
	
	public String[] getThemeNames();
	
	public int getTilePositionById(int id);
}
