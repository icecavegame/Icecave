package com.android.icecave.guiLogic;

import android.content.Context;

import android.widget.ImageView;

public class TileImageView extends ImageView
{
	private int mRow;
	private int mCol;
	
	public TileImageView(int row, int col, Context context) {
		this(context);
		mRow = row;
		mCol = col;
	}
	
	public TileImageView(Context context)
	{
		super(context);
		// TODO Complete
	}

}
