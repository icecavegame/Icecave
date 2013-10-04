package com.android.icecave.guiLogic;

public class GUIScreenManager implements ITileScale
{
	float mModifiedTileWidth;
	float mModifiedTileHeight;
	
	public GUIScreenManager (int tilesNumX, int tilesNumY, int pixelSizeX, int pixelSizeY) {
		// Calculate the size tiles should be to fill map
		mModifiedTileWidth = (float)pixelSizeX / tilesNumX;
		mModifiedTileHeight = (float)pixelSizeY / tilesNumY;
		
		// Increase remainder up to 0.5 if less than that
		// The reason behind this is that if rounded down, the size of the tiles in total will be less than that of the screen
		// thus revealing the white background of the activity. Instead however, we decide to allow small part of the wall tiles,
		// to be drawn outside the screen.
		mModifiedTileWidth = ceiling(mModifiedTileWidth);
		mModifiedTileHeight = ceiling(mModifiedTileHeight);
	}
	
	@Override
	public float getTileWidth() {
		return mModifiedTileWidth;
	}
	
	@Override
	public float getTileHeight() {
		return mModifiedTileHeight;
	}
	
	private float ceiling(float size) {
		float result = size;
		float afterDecimal = (float)size % 1;
		
		// If remainder is not 0 but would be rounded down when cast to integer
		if (afterDecimal > 0 && afterDecimal < 0.5) {
			// Set remainder to 5 so it would be rounded up in the future
			result = size +  (float)0.5 - (float)afterDecimal;
		}
		
		return result;
	}
}
