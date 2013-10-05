package com.tas.icecave.gui;

import com.tas.icecave.guiLogic.GUIBoardManager;

public interface ILoadable
{
	public long getAnimationDuration();
	
	public GUIBoardManager getGuiBoardManager();
	
	public GameTheme getGameTheme();
	
	public boolean isInitialLoading();
}
