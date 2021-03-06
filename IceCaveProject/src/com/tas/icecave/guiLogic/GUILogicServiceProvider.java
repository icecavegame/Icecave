package com.tas.icecave.guiLogic;

import com.tas.icecave.guiLogic.tiles.GUITileFactory;

public class GUILogicServiceProvider
{
	private static GUILogicServiceProvider mProvider = new GUILogicServiceProvider();
	private GUITileFactory mTileFactory = null;

	/**
	 * Get the GUI tile factory class.
	 * @return
	 */
	public GUITileFactory getTileFactory(){
		return mTileFactory;
	}
	
	/**
	 * Create a new instance of the MapLogicServerProvider.
	 */
	private GUILogicServiceProvider(){
		mTileFactory = new GUITileFactory();
	}
	
	/**
	 * Get the static instance of the MapLogicServiceProvider.
	 * @return static instance of the service provider.
	 */
	public static GUILogicServiceProvider getInstance()
	{
		return mProvider;
	}
}
