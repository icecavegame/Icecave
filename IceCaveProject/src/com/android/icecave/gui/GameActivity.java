package com.android.icecave.gui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.android.icecave.R;
import com.android.icecave.general.Consts;
import com.android.icecave.general.EDifficulty;
import com.android.icecave.general.EDirection;
import com.android.icecave.guiLogic.DrawablePlayer;
import com.android.icecave.guiLogic.GUIBoardManager;
import com.android.icecave.guiLogic.TileImageView;
import com.android.icecave.mapLogic.IIceCaveGameStatus;

public class GameActivity extends Activity implements ISwipeDetector
{
<<<<<<< HEAD
	private static GUIBoardManager	sGBM;
	private DrawablePlayer			mPlayer;
	private TableLayout				mTilesTable;
	private GameTheme				mGameTheme;
=======
	private static GUIBoardManager sGBM;
	private DrawablePlayer mPlayer;
	private TableLayout mTilesTable;

<<<<<<< HEAD
>>>>>>> 7cae256d21e163f6b6962a330bdf9c3f7c493aeb
=======
>>>>>>> 7cae256d21e163f6b6962a330bdf9c3f7c493aeb
	// private final String POSITION_X = "posX";
	// private final String POSITION_Y = "posY";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.tiles_layout);

		// Hide the Status Bar
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		mTilesTable = (TableLayout) findViewById(R.id.tilesTable);
		mPlayer = (DrawablePlayer) findViewById(R.id.sprite);

		// Register swipe events to the layout
		mTilesTable.setOnTouchListener(new ActivitySwipeDetector(this));

		// Create the first row if none exist
		if (mTilesTable.getChildCount() == 0)
		{
			createRows();
		}

<<<<<<< HEAD
<<<<<<< HEAD
		SharedPreferences mShared = getSharedPreferences(Consts.PREFS_FILE_TAG, 0);

		mGameTheme = 
				new GameTheme(BitmapFactory.decodeResource(getResources(),
														  (mShared.getInt(Consts.THEME_SELECT, Consts.DEFAULT_TILES))),
							  BitmapFactory.decodeResource(getResources(),
									  				      (mShared.getInt(Consts.PLAYER_SELECT_TAG, Consts.DEFAULT_PLAYER))));
	
=======
>>>>>>> 7cae256d21e163f6b6962a330bdf9c3f7c493aeb
=======
>>>>>>> 7cae256d21e163f6b6962a330bdf9c3f7c493aeb
		// // Set up player position
		// if (savedInstanceState != null)
		// {
		// mPlayerPosition =
<<<<<<< HEAD
<<<<<<< HEAD
		// new Point(savedInstanceState.getInt(POSITION_X),
		// savedInstanceState.getInt(POSITION_Y));
=======
		// new Point(savedInstanceState.getInt(POSITION_X), savedInstanceState.getInt(POSITION_Y));
>>>>>>> 7cae256d21e163f6b6962a330bdf9c3f7c493aeb
=======
		// new Point(savedInstanceState.getInt(POSITION_X), savedInstanceState.getInt(POSITION_Y));
>>>>>>> 7cae256d21e163f6b6962a330bdf9c3f7c493aeb
		// } else
		// {
		// mPlayerPosition = new Point(Consts.DEFAULT_START_POS);
		// }
	}

	public int getHeight()
	{
		return mTilesTable.getBottom();
	}
	
	public int getWidth()
	{
		return mTilesTable.getWidth();
	}

	/***
	 * Add the next tile to the table layout
	 * 
	 * @param tile
	 *            Tile to add
	 */
	public void addNextTileToView(TileImageView tile)
	{
		// Add current tile to the row that matches its index
		((TableRow) mTilesTable.findViewById(tile.getCol())).addView(tile);
	}

	private void createRows()
	{
		// Create all rows by the value of board size rows
		// TODO Change const value to an input
<<<<<<< HEAD
<<<<<<< HEAD
		for (int i = 0; i < (Integer) getIntent().getExtras().get(
				Consts.SELECT_BOARD_SIZE_Y); i++)
=======
		for (int i = 0; i < (Integer) getIntent().getExtras().get(Consts.SELECT_BOARD_SIZE_Y); i++)
>>>>>>> 7cae256d21e163f6b6962a330bdf9c3f7c493aeb
=======
		for (int i = 0; i < (Integer) getIntent().getExtras().get(Consts.SELECT_BOARD_SIZE_Y); i++)
>>>>>>> 7cae256d21e163f6b6962a330bdf9c3f7c493aeb
		{
			// Create new row and set its Id as the value of its index
			TableRow newRow = new TableRow(this);
			newRow.setId(i);
			mTilesTable.addView(newRow);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		// // Put position data
		// outState.putInt(POSITION_X, mPlayerPosition.x);
		// outState.putInt(POSITION_Y, mPlayerPosition.y);
	}

	@Override
	public void bottom2top(View v)
	{
		commitSwipe(EDirection.UP);
	}

	@Override
	public void left2right(View v)
	{
		commitSwipe(EDirection.RIGHT);
	}

	@Override
	public void right2left(View v)
	{
		commitSwipe(EDirection.LEFT);
	}

	@Override
	public void top2bottom(View v)
	{
		commitSwipe(EDirection.DOWN);
	}

	/**
	 * 
	 */
	private void commitSwipe(EDirection direction)
	{
		IIceCaveGameStatus iceCaveGameStatus = sGBM.movePlayer(direction);
<<<<<<< HEAD
<<<<<<< HEAD
		// mPGM.getPlayerImage(mPlayerPosition.x, mPlayerPosition.y, direction,
		// true);

		// TODO: Sagie make the animation.
		mPlayer.startDrawImage(direction, iceCaveGameStatus.getPlayerPoint());
=======
		// mPGM.getPlayerImage(mPlayerPosition.x, mPlayerPosition.y, direction, true);

		// TODO: Sagie make the animation.
		mPlayer.movePlayer(direction, iceCaveGameStatus.getPlayerPoint());
>>>>>>> 7cae256d21e163f6b6962a330bdf9c3f7c493aeb
=======
		// mPGM.getPlayerImage(mPlayerPosition.x, mPlayerPosition.y, direction, true);

		// TODO: Sagie make the animation.
		mPlayer.movePlayer(direction, iceCaveGameStatus.getPlayerPoint());
>>>>>>> 7cae256d21e163f6b6962a330bdf9c3f7c493aeb
		if (iceCaveGameStatus.getIsStageEnded())
		{

		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		// Create new stage here to make sure layout is made and active and
		// visible
		if (sGBM == null)
		{
			// Create once
			sGBM = new GUIBoardManager();

			// Initialize the game board & shit
			// TODO Change const value to an input
<<<<<<< HEAD
			sGBM.startNewGame(
					Consts.DEFAULT_BOULDER_NUM,
					(Integer) getIntent().getExtras().get(
							Consts.SELECT_BOARD_SIZE_X),
					(Integer) getIntent().getExtras().get(
							Consts.SELECT_BOARD_SIZE_Y),
					EDifficulty.values()[(Integer) getIntent().getExtras().get(
							Consts.LEVEL_SELECT_TAG)]);

			// Create first stage
			sGBM.newStage(Consts.DEFAULT_START_POS, 
						  Consts.DEFAULT_WALL_WIDTH,
						  this,
						  mGameTheme);
=======
			sGBM.startNewGame(Consts.DEFAULT_BOULDER_NUM,
					(Integer) getIntent().getExtras().get(Consts.SELECT_BOARD_SIZE_X),
					(Integer) getIntent().getExtras().get(Consts.SELECT_BOARD_SIZE_Y),
					EDifficulty.values()[(Integer) getIntent().getExtras().get(Consts.LEVEL_SELECT_TAG)]);

			// Create first stage
			sGBM.newStage(Consts.DEFAULT_START_POS, Consts.DEFAULT_WALL_WIDTH, this);

			// Create player image
			mPlayer.initializePlayer();
<<<<<<< HEAD
>>>>>>> 7cae256d21e163f6b6962a330bdf9c3f7c493aeb
=======
>>>>>>> 7cae256d21e163f6b6962a330bdf9c3f7c493aeb
		}

		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onDestroy()
	{
		// Reset variable
		sGBM = null;
		super.onDestroy();
	}
}
