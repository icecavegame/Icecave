package com.android.icecave.guiLogic.theme;

public class ThemeMap
{
	EmptyTheme mEmpty;
	BoulderTheme mBoulder;
	FlagTheme mFlag;
	WallTheme mWall;

	public ThemeMap()
	{
		mEmpty = new EmptyTheme();
		mBoulder = new BoulderTheme();
		mFlag = new FlagTheme();
		mWall = new WallTheme();
	}

	public EmptyTheme getEmptyTheme()
	{
		return mEmpty;
	}

	public BoulderTheme getBoulderTheme()
	{
		return mBoulder;
	}

	public FlagTheme getFlagTheme()
	{
		return mFlag;
	}

	public WallTheme getWallTheme()
	{
		return mWall;
	}
}
