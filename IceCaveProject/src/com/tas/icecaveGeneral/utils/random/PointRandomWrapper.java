package com.tas.icecaveGeneral.utils.random;

import com.tas.icecaveGeneral.utils.Point;

public class PointRandomWrapper implements IRandomTypeWrapper<Point>
{
	/**
	 * Used for random services.
	 */
	IRandomServices mRandomServices;
	
	/**
	 * Create a new instance of the IntRandomWrapper.
	 * 
	 * @param randomServices - Object to receive random values.
	 */
	public PointRandomWrapper(IRandomServices randomServices)
	{
		mRandomServices = randomServices;
	}
	
	@Override
	public Point getRandomValue()
	{
		return new Point(mRandomServices.getRandomInt(), mRandomServices.getRandomInt());
	}

	@Override
	public Point getRandomValue(Point ceiling)
	{
		int x = mRandomServices.getRandomInt(ceiling.x);
		int y = mRandomServices.getRandomInt(ceiling.y);
		
		return new Point(x,y);
	}

	@Override
	public Point getRandomValue(Point floor, Point ceiling)
	{
		int x = floor.x + mRandomServices.getRandomInt(ceiling.x - floor.x);
		int y = floor.y + mRandomServices.getRandomInt(ceiling.y - floor.y);
		
		return new Point(x,y);
	}

}
