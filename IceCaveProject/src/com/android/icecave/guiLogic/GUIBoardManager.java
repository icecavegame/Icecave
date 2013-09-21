package com.android.icecave.guiLogic;

import android.graphics.Point;

import com.android.icecave.general.EDifficulty;
import com.android.icecave.general.EDirection;
import com.android.icecave.mapLogic.IceCaveGame;
import com.android.icecave.mapLogic.tiles.ITile;

/**
 * This class manages all GUI logic.
 * @author Tom
 *
 */
public class GUIBoardManager
{
	private TileImageView[][] mTiles;
	private IceCaveGame mIceCaveGame;
	
	/**
	 * Start a new game.
	 * @param boulderNum - Number of boulders to place on map. 
	 * @param boardSizeX - Row length of the map.
	 * @param boardSizeY - Column length of the map.
	 * @param difficulty - Difficulty level.
	 */
	public void startNewGame( int 		 boulderNum, 
						 	  int 		 boardSizeX,
						 	  int 	     boardSizeY,
						 	  EDifficulty difficulty){
		mIceCaveGame = new IceCaveGame(boulderNum, boardSizeX, boardSizeY, difficulty);
		
		// Get the tiles
		mTiles = new TileImageView[boardSizeY][boardSizeX];
		
		ITile[][] board = mIceCaveGame.getBoard();
		
		// Go through the game board.
		for (int yAxis = 0; yAxis < boardSizeY; yAxis++) {
			for (int xAxis = 0; xAxis < boardSizeX; xAxis++) {
				mTiles[yAxis][xAxis] =
						GUILogicServiceProvider.
							getInstance().
								getTileFactory().
									getTiles(board[yAxis]
												  [xAxis]);
			}
		}
	}
	
	/**
	 * Move the player on the board.
	 * 
	 * @param direction - Direction to move the player in.
	 * 
	 * @return new player point.
	 */
	public Point movePlayer(EDirection direction){
	
		return mIceCaveGame.movePlayer(direction);
	}
	
	/**
	 * Create a new instance of the GUIBoardManager.
	 */
	public GUIBoardManager() {
		
	}
}
