package com.android.icecave.mapLogic;

import com.android.icecave.mapLogic.tiles.ITile;

import android.graphics.Point;
import com.android.icecave.globals.EDifficulty;
import com.android.icecave.globals.EDirection;

public class IceCaveGame
{
	private int mOverallMoves;
	private EDirection mLastDirectionMoved;
	private IceCaveStage mStage;
	private Point mPlayerLocation;
	private boolean mPlayerMoving;
	private int mBoulderNum;
	private int mBoardSizeX;
	private int mBoardSizeY;
	private EDifficulty mDifficulty;
	
	public IceCaveGame(int boulderNum, int boardSizeX, int boardSizeY, EDifficulty difficulty) {
		mBoulderNum = boulderNum;
		mBoardSizeX = boardSizeX;
		mBoardSizeY = boardSizeY;
		mDifficulty = difficulty;
		
		mStage = new IceCaveStage();
		mPlayerLocation = new Point();
	}
	
	private void endStage() {
		
	}
	
	public int getStageMoves() {
		return mStage.getMoves();
	}
	
	public Point movePlayer(EDirection direction) {
		return null;
	}
	
	public int getOverallMoves() {
		return mOverallMoves;
	}
	
	public void stopPlayer() {
		
	}
	
	public ITile[][] getBoard() {
		return mStage.getBoard();
	}
}
