package com.android.icecave.mapLogic.tiles;

import com.android.icecave.utils.Point;

/**
 * An empty tile in the board.
 * The player slides through this tile.
 * @author Tom
 *
 */
@SuppressWarnings("serial")
public class EmptyTile extends BaseTile
{
	/**
	 * Create a new instance of the EmptyTile object.
	 * @param x - X position for the tiles.
	 * @param y - Y position for the tile.
	 */
	public EmptyTile(int x, int y)
	{
		super(x, y);
	}
	
	/**
	 * Create a new instance of the empty tile object.
	 * @param location - Location of the tile on the board.
	 */
	public EmptyTile(Point location)
	{
		super(location);
	}

	@Override
	public ITile clone()
	{
		return new EmptyTile(mLocation);
	}
}
