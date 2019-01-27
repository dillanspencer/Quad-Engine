package com.quad.entity;

import com.quad.Tile.TileMap;

/**
 * @author Dillan Spencer
 * The Accessor class is used to create smooth movement between an
 * object and the TileMap
 */

import aurelienribon.tweenengine.TweenAccessor;

public class Accessor implements TweenAccessor<TileMap>{
	
	public static final int POSITION_X = 1;
	public static final int POSITION_Y = 2;

	@Override
	public int getValues(TileMap target, int tweenType, float[] returnValues) {
		if(target.getx() <= target.getXmin()) return -1;
		 switch (tweenType) {
	        case POSITION_X: returnValues[0] = (float) target.getx(); return 1;
	        case POSITION_Y: returnValues[0] = (float) target.gety(); return 1;
	        default: assert false; return -1;
	    }
		 
	}

	@Override
	public void setValues(TileMap target, int tweenType, float[] newValues) {
	    switch (tweenType) {
	        case POSITION_X: target.setX(newValues[0]); break;
	        case POSITION_Y: target.setY(newValues[0]); break;
	   
	        default: assert false; break;
	    }
	}

}
