package com.android.icecave.general;

import android.graphics.Point;

public enum EDirection
{
	/**
	 * Right movement.
	 */
	RIGHT(1, 0),
	/**
	 * Down movement.
	 */
	DOWN(0, 1),
	/**
	 * Left movement.
	 */
	LEFT(-1, 0),
	/**
	 * Up movement.
	 */
	UP(0, -1);

	/**
	 * Direction movement point.
	 */
	Point mDirection;

	/**
	 * Create a new instance of the EDirection object.
	 * @param direction - Direction of the object.
	 */
	private EDirection(Point direction) {
		mDirection = new Point(direction);
	}
	
	/**
	 * Create a new instance of the EDirection object.
	 * @param x - x direction of the object.
	 * @param y - y direction of the object.
	 */
	private EDirection(int x, int y) {
		mDirection = new Point(x,y);
	}
	
	/**
	 * Return the direction of the current EDirection.
	 * @return Direction.
	 */
	public Point getDirection(){
		return mDirection;
	}
	
	/**
	 * Get the direction opposite of the current.
	 * @return Opposite direction.
	 */
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
