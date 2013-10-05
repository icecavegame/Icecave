package com.android.icecave.gui;

import com.android.icecave.guiLogic.GUIBoardManager;

public interface ILoadable
{
	public long getAnimationDuration();
	
	public GUIBoardManager getGuiBoardManager();
	
	public GameTheme getGameTheme();
	
	public boolean isInitialLoading();
}
