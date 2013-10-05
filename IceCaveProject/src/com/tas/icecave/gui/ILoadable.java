package com.tas.icecave.gui;


public interface ILoadable
{
	public long getAnimationDuration();

	public void onLoad();

	public GameTheme getGameTheme();

	public boolean isInitialLoading();

	public int getMinimalMovesForStage();

	public int getMovesCarriedOutThisStage();
}
