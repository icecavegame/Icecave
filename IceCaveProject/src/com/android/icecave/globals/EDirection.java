package com.android.icecave.globals;

public enum EDirection
{
	RIGHT,
	DOWN,
	LEFT,
	UP;
	
	public EDirection getOpositeDirection()
	{
		switch (this)
		{
		case RIGHT:
			return LEFT;
		case DOWN:
			return UP;
		case LEFT:
			return RIGHT;
		case UP:
			return DOWN;
		default:
			break;
		}
		
		return null;
		
	}
}
