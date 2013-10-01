package com.android.icecave.guiLogic;

import com.android.icecave.gui.GameActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public class TilesView extends View
{
	private Bitmap[][] mBoard;
	private GameActivity mContext;
	
	public TilesView(Context context) {
		super(context);
	}
	
	public TilesView(Context context, Bitmap[][] board)
	{
		super(context);
		mBoard = board;
		mContext = (GameActivity) context;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// Draw all tiles
		for (int yAxis = 0; yAxis < mBoard.length; yAxis++) {
			for (int xAxis = 0; xAxis < mBoard[0].length; xAxis++) {
				canvas.drawBitmap(mBoard[yAxis][xAxis], mBoard[yAxis][xAxis].getWidth() * xAxis, mBoard[yAxis][xAxis].getHeight() * yAxis, null);
			}
		}
		
		// Keep views on top of this view
		mContext.drawForeground();
	}
	
	public int getBoardX() {
		return mBoard[0].length;
	}
	
	public int getBoardY() {
		return mBoard.length;
	}
}
