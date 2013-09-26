package com.android.icecave.guiLogic;

public class GUIScreenManager
{
	float mModifiedTileWidth;
	float mModifiedTileHeight;
	
	public GUIScreenManager (int tilesNumX, int tilesNumY, int pixelSizeX, int pixelSizeY) {
		// Calculate the size tiles should be to fill map
		mModifiedTileWidth = (float)pixelSizeX / tilesNumX;
		mModifiedTileHeight = (float)pixelSizeY / tilesNumY;
	}
	
	public float getModifiedTileWidth() {
		return mModifiedTileWidth;
	}
	
	public float getModifiedTileHeight() {
		return mModifiedTileHeight;
	}
}
