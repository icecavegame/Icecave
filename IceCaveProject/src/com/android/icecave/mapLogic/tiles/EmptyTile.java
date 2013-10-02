package com.android.icecave.mapLogic.tiles;

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
}
