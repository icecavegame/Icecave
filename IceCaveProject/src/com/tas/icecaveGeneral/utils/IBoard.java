package com.tas.icecaveGeneral.utils;

/**
 * Interface representing a board.
 * @author Tom
 *
 * @param <tile_type> - The type of tiles in the board.
 */
public interface IBoard<tile_type>
{
	/**
	 * Get the board tiles.
	 * @return The board tiles map.
	 */
	tile_type[][] getBoard();
	
	/**
	 * Get the starting point of player.
	 * @return Point of start on the board for the player.
	 */
	Point getStartPoint();
}
