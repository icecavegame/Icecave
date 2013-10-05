package com.android.icecave.guiLogic;

import android.graphics.Bitmap;
import com.android.icecave.general.Consts;
import com.android.icecave.general.EDifficulty;
import com.android.icecave.general.EDirection;
import com.android.icecave.gui.GameActivity;
import com.android.icecave.gui.GameTheme;
import com.android.icecave.mapLogic.IIceCaveGameStatus;
import com.android.icecave.mapLogic.IceCaveGame;
import com.android.icecave.mapLogic.tiles.ITile;
import com.android.icecave.utils.Point;
import java.io.Serializable;

/**
 * This class manages all GUI logic.
 * @author Tom
 *
 */
@SuppressWarnings("serial")
public class GUIBoardManager implements Serializable
{
	private transient Bitmap[][] mTiles;
	private IceCaveGame mIceCaveGame;
	
	/**
	 * Return the minimal moves for the current stage.
	 * @return Minimal moves for stage.
	 */
	public int getMinimalMovesForStage(){
		return mIceCaveGame.getStageMoves();
	}
	
	/**
	 * Resets the player location on the board.
	 * @param startLoc - Starting location of the player to restart to.
	 */
	public void resetPlayer(Point startLoc){
		mIceCaveGame.resetPlayer(startLoc);
	}
	
	/**
	 * Return the overall moves for the current stage.
	 * @return Moves player made in stage.
	 */
	public int getMovesCarriedOutThisStage(){
		return mIceCaveGame.getCurrentStageTakenMoves();
	}
	
	/**
	 * Return the overall moves for the current game.
	 * @return Overall moves in game.
	 */
	public int getOverAllMovesForGame(){
		return mIceCaveGame.getOverallMoves();
	}
	
	/**
	 * Start a new game.
	 * @param boulderNum - Number of boulders to place on map. 
	 * @param boardSizeHeight - Row length of the map.
	 * @param difficulty - Difficulty level.
	 * @param Context - activity creating the game
	 */
	public void startNewGame( 
						 	  int 		 boardSizeHeight,
						 	  GameActivity context,
						 	  EDifficulty difficulty){
		
		int maxSize = Math.max(context.getWidth(), context.getHeight());
		int minSize = Math.min(context.getWidth(), context.getHeight());
		int boardSizeWidth = (int)((maxSize * 1.0 / minSize) * boardSizeHeight);
		
		mIceCaveGame = 
				new IceCaveGame(boardSizeHeight * 
								boardSizeWidth / 
								Consts.DEFAULT_BOULDER_RELATION, 
								boardSizeHeight, 
								boardSizeWidth,
								difficulty);
		
		// Get the tiles
		mTiles = new Bitmap[boardSizeHeight][boardSizeWidth];
	}
	
	/**
	 * Start a new stage.
	 * @param playerStart - Starting location of the player.
	 * @param wallWidth - Width of the wall in tiles.
	 * @param context - Current activity context.
	 */
	public void newStage(Point playerStart, 
	                     int wallWidth,
	                     GameActivity context,
	                     GameTheme gameTheme){
		mIceCaveGame.newStage(playerStart, wallWidth);
		
		ITile[][] board = mIceCaveGame.getBoard();
		GUIScreenManager screenManager = 
				new GUIScreenManager(board[0].length, 
									 board.length, 
									 context.getWidth(), 
									 context.getHeight());
		
		// Go through the game board.
		for (int yAxis = 0; yAxis < board.length; yAxis++) {
			for (int xAxis = 0; xAxis < board[0].length; xAxis++) {
				mTiles[yAxis][xAxis] =
						GUILogicServiceProvider.
							getInstance().
								getTileFactory().
									getTiles(board[yAxis]
												  [xAxis],
											 screenManager,
											 gameTheme);
			}
		}
	}
	
	/**
	 * Start a new stage.
	 * @param playerStart - Starting location of the player.
	 * @param wallWidth - Width of the wall in tiles.
	 * @param context - Current activity context.
	 */
	public void newStage(Point playerStart, 
	                     int wallWidth,
	                     GameActivity context,
	                     GameTheme gameTheme){
		mIceCaveGame.newStage(playerStart, wallWidth);
		
		ITile[][] board = mIceCaveGame.getBoard();
		GUIScreenManager screenManager = 
				new GUIScreenManager(board[0].length, 
						board.length, 
						context.getWidth(), 
						context.getHeight());
		
		// Go through the game board.
		for (int yAxis = 0; yAxis < board.length; yAxis++) {
			for (int xAxis = 0; xAxis < board[0].length; xAxis++) {
				mTiles[yAxis][xAxis] =
						GUILogicServiceProvider.
						getInstance().
						getTileFactory().
						getTiles(board[yAxis]
								[xAxis],
								screenManager,
								gameTheme);
			}
		}
	}
	
	/**
	 * Get the tiles of the current board.
	 * @return Map of the current board tiles.
	 */
	public Bitmap[][] getTiles() {
		return mTiles;
	}
	
	/**
	 * Move the player on the board.
	 * 
	 * @param direction - Direction to move the player in.
	 * 
	 * @return new player point.
	 */
	public IIceCaveGameStatus movePlayer(EDirection direction){
	
		return mIceCaveGame.movePlayer(direction);
	}
}
