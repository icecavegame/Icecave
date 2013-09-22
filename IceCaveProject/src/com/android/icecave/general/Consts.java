package com.android.icecave.general;

import android.graphics.Point;
import com.android.icecave.R;

public class Consts
{
	public final static String PREFS_FILE_TAG = "prefsFile";
	public final static String LEVEL_SELECT_TAG = "levelSelect";
	public final static String PLAYER_SELECT_TAG = "playerSelect";
	public final static String THEME_SELECT = "themeSelect";
	public final static int DEFAULT_PLAYER = R.drawable.default_player;
	public final static int DEFAULT_TILES = R.drawable.default_tiles;
	public final static Point START_POS = new Point(1, 1);
	public final static int DEFAULT_BOULDER_NUM = 30;
	public final static int DEFAULT_BOARD_SIZE_X = 10;
	public final static int DEFAULT_BOARD_SIZE_Y = 10;

	// TODO PUT REAL POSITIONS
	public final static Point EMPTY_TILE_IN_SPRITE = new Point(4, 0);
	public final static Point FLAG_TILE_IN_SPRITE = new Point(3, 0);
	public final static Point WALL_TILE_IN_SPRITE = new Point(4, 2);
	public final static Point BOULDER_TILE_IN_SPRITE = new Point(3, 1);
}
