package com.android.icecave.mapLogic.tiles;

import com.android.icecave.utils.Point;

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
	
	/**
	 * Create a new instance of the wall tile object.
	 * @param location - Location of the tile on the board.
	 */
	public WallTile(Point location)
	{
		super(location);
	}

	@Override
	public ITile clone()
	{
		return new WallTile(mLocation);
	}
}
