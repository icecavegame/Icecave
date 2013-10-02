package com.android.icecave.mapLogic.tiles;

/**
 * A boulder tile.
 * The player cannot move through this tile.
 * @author Tom
 *
 */
@SuppressWarnings("serial")
public class BoulderTile extends BaseTile implements IBlockingTile
{
	/**
	 * Create a new instance of the boulder tile object.
	 * @param x - X position for the tiles.
	 * @param y - Y position for the tile.
	 */
	public BoulderTile(int x, int y)
	{
		super(x, y);
	}
}
