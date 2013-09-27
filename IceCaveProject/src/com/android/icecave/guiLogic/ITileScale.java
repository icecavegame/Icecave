package com.android.icecave.guiLogic;

/**
 * Interface for getting tile sizes.
 * @author Tom
 *
 */
public interface ITileScale
{
	/**
	 * Get the tiles width in pixels.
	 * @return Tile width.
	 */
	public float getTileWidth();
	
	/**
	 * Get the tiles height in pixels.
	 * @return Tile height.
	 */
	public float getTileHeight();
}
