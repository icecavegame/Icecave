package com.tas.icecaveGeneral.utils;

import java.io.Serializable;

/**
 * Represents a point, and is serializable.
 * @author Tom
 *
 */
@SuppressWarnings("serial")
public class Point implements Serializable
{
	/**
	 * The x value of the point.
	 */
	public int x;
	
	/**
	 * The y value of the point.
	 */
	public int y;
	
	/**
	 * Create a new point.
	 */
	public Point(){}
	
	/**
	 * Creates a new point.
	 * @param x - X value for the point.
	 * @param y - Y value for the point.
	 */
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}

	/**
	 * Creates a new point.
	 * @param other - Other point to use for values.
	 */
	public Point(Point other){
		x = other.x;
		y = other.y;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Point){
			Point other = (Point)o;
			
			return (x == other.x) && 
				   (y == other.y);
			
		}
		
		return false;
	};
	
	/**
	 * Checks if the point is equal to the input parameters.
	 * @param x - X value to compare.
	 * @param y - Y value to compare.
	 * @return true if the point members 
	 * 		   is equal to the input parameters.
	 */
	public boolean equals(int x, int y) {
		return (this.x == x) &&
			   (this.y == y);
	};
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Point(this);
	};
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	};
	
	/**
	 * Offsets the current point.
	 * @param x - X value to offset.
	 * @param y - Y value to offset.
	 */
	public void offset(int x, int y){
		this.x += x;
		this.y += y;
	}
	
	/**
	 * Offsets the current point.
	 * @param other - Point to offset from.
	 */
	public void offset(Point other){
		x += other.x;
		y += other.y;
	}
}
