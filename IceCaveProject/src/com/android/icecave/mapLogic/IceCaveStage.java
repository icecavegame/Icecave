package com.android.icecave.mapLogic;

import android.graphics.Point;

import com.android.icecave.general.EDifficulty;
import com.android.icecave.general.EDirection;
import com.android.icecave.mapLogic.tiles.ITile;

public class IceCaveStage
{
	private ITile[][] mTiles;
	private int mMoves;
	private boolean mPlayerMovement;
	
	public void buildBoard(EDifficulty difficulty, int boardSizeX, int boardSizeY, int boulderNum) {
		
	}
	
	public void movePlayerOneTile(Point playerLocation, EDirection direction) {
		
	}
	
	public int getMoves() {
		return mMoves;
	}
	
	public ITile[][] getBoard() {
		return mTiles;
	}
}
