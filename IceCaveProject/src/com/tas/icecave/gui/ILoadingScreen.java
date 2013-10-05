package com.tas.icecave.gui;

public interface ILoadingScreen
{
	/***
	 * Do what is needed to start & show the loading screen. Fade in animation
	 */
	public void preLoad(ILoadable loadableData);
	
	/***
	 * Do what is needed to finish & hide the loading screen. Fade out animation
	 */
	public void postLoad(ILoadable loadableData);
}
