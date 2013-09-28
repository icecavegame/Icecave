package com.android.icecave.guiLogic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public class TilesView extends View
{
	private Bitmap[][] mBoard;
	
	public TilesView(Context context) {
		super(context);
	}
	
	public TilesView(Context context, Bitmap[][] board)
	{
		super(context);
		mBoard = board;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		for (int yAxis = 0; yAxis < mBoard.length; yAxis++) {
			for (int xAxis = 0; xAxis < mBoard[0].length; xAxis++) {
				canvas.drawBitmap(mBoard[yAxis][xAxis], mBoard[yAxis][xAxis].getWidth() * xAxis, mBoard[yAxis][xAxis].getHeight() * yAxis, null);
			}
		}
	}
	
	public int getBoardX() {
		return mBoard[0].length;
	}
	
	public int getBoardY() {
		return mBoard.length;
	}
}
