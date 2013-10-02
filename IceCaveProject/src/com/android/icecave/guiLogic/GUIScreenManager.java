package com.android.icecave.guiLogic;

public class GUIScreenManager implements ITileScale
{
	float mModifiedTileWidth;
	float mModifiedTileHeight;
	
	public GUIScreenManager (int tilesNumX, int tilesNumY, int pixelSizeX, int pixelSizeY) {
		// Calculate the size tiles should be to fill map
		mModifiedTileWidth = (float)pixelSizeX / tilesNumX;
		mModifiedTileHeight = (float)pixelSizeY / tilesNumY;
	}
	
	@Override
	public float getTileWidth() {
		return mModifiedTileWidth;
	}
	
	@Override
	public float getTileHeight() {
		return mModifiedTileHeight;
	}
}
