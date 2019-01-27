package com.quad.Tile;

/**
 * @author Dillan Spencer
 * This class handles attributes associated with each Tile
 */

import com.quad.core.fx.Image;


public class Tile {
	
	private Image image;
	private int type;
	
	//position
	double x;
	double y;
	
	// tile types
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	public static final int LIGHT = 2;
	
	public Tile(Image image, int type) {
		this.image = image;
		this.type = type;
	}
	
	public Image getImage() { return image; }
	public int getType() { return type; }
	
	public double getX(){return x;}
	public double getY(){return y;}
	
	public void setX(double i){
		x = i;
	}
	
	public void setY(int i){
		y = i;
	}
	
}
