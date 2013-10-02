package com.android.icecave.mapLogic.tiles;

/**
 * A wall tile in the game.
 * The player cannot slide through this tile.
 * @author Tom
 *
 */
@SuppressWarnings("serial")
public class WallTile extends BaseTile implements IBlockingTile
{
	/**
	 * Create a new instance of the WallTile object.
	 * @param x - X position for the tiles.
	 * @param y - Y position for the tile.
	 */
	public WallTile(int x, int y)
	{
		super(x, y);
	}
}
