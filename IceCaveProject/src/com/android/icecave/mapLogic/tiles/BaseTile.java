package com.android.icecave.mapLogic.tiles;

import com.android.icecave.mapLogic.collision.ICollisionable;

import android.graphics.Point;

public abstract class BaseTile implements ITile, ICollisionable
{
	protected Point mLocation;
}
