package com.tas.icecave.general;

import com.tas.icecave.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;


public class MusicService extends Service implements MediaPlayer.OnErrorListener
{

	private final IBinder mBinder = new ServiceBinder();
	MediaPlayer mPlayer;
	private int length = 0;

	public MusicService()
	{
	}

	public class ServiceBinder extends Binder
	{
		public MusicService getService()
		{
			return MusicService.this;
		}
	}

	@Override
	public IBinder onBind(Intent arg0)
	{
		return mBinder;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();

		mPlayer = MediaPlayer.create(this, R.raw.card_game_in_progress);
		mPlayer.setOnErrorListener(this);

		if (mPlayer != null)
		{
			mPlayer.setLooping(true);
		}

		mPlayer.setOnErrorListener(new OnErrorListener()
		{

			public boolean onError(MediaPlayer mp, int what, int extra)
			{

				onError(mPlayer, what, extra);
				return true;
			}
		});

		mPlayer.setOnCompletionListener(new OnCompletionListener()
		{

			@Override
			public void onCompletion(MediaPlayer mp)
			{
				try
				{
					// Sleep for 6 seconds
					mp.wait(6000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		return START_STICKY;
	}

	public void startMusic()
	{
		if (!mPlayer.isPlaying())
		{
			mPlayer.start();
		}
	}

	public void pauseMusic()
	{
		if (mPlayer.isPlaying())
		{
			mPlayer.pause();
			length = mPlayer.getCurrentPosition();

		}
	}

	public void resumeMusic()
	{
		if (mPlayer.isPlaying() == false)
		{
			mPlayer.seekTo(length);
			mPlayer.start();
		}
	}

	public void stopMusic()
	{
		mPlayer.stop();
		mPlayer.release();
		mPlayer = null;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if (mPlayer != null)
		{
			try
			{
				mPlayer.stop();
				mPlayer.release();
			} finally
			{
				mPlayer = null;
			}
		}
	}

	public boolean onError(MediaPlayer mp, int what, int extra)
	{

		Toast.makeText(this, "music player failed", Toast.LENGTH_SHORT).show();
		if (mPlayer != null)
		{
			try
			{
				mPlayer.stop();
				mPlayer.release();
			} finally
			{
				mPlayer = null;
			}
		}
		return false;
	}
}
