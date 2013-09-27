package com.android.icecave.guiLogic;

import android.content.Context;
import android.graphics.Point;
import com.android.icecave.general.EDirection;

public class GUIAnimationManager
{
	// private Bitmap mSprite;
	// private Canvas mCanvas;
	//
	// public GUIAnimationManager(Bitmap sprite, Canvas canvas) {
	// mSprite = sprite;
	// mCanvas = canvas;
	// }
	//
	// public void setSprite(Bitmap newSprite) {
	// mSprite = newSprite;
	// }
	//
	// public void draw() {
	// mCanvas.drawBitmap(mSprite, null, null);
	// }

	private Context mContext;
	private TileImageView mSprite;

	public GUIAnimationManager(Context context, TileImageView sprite)
	{
		mContext = context;
		mSprite = sprite;
	}

	public void animate(EDirection direction, Point playerLocation)
	{
		// Should sprite be TileImageView? Or should it be a new view overriding onDraw
	}
}
