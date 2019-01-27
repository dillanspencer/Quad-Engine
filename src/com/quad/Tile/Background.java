package com.quad.Tile;

/**
 * @author Dillan Spencer
 * This class is used to create a parallax background
 */

import com.quad.core.Renderer;
import com.quad.core.Settings;
import com.quad.core.fx.Image;


public class Background {
	
	private Image image;
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private int width;
	private int height;
	
	private double xscale;
	private double yscale;
	
	public Background(String s) {
		this(s, 0.1);
	}
	
	public Background(String s, double d) {
		this(s, d, d);
		//image.shadowType = ShadowType.FADE;
	}
	
	public Background(String s, double d1, double d2) {
		try {
			image = new Image(s);
			width = image.width;
			height = image.height;
			xscale = d1;
			yscale = d2;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Background(String s, double ms, int x, int y, int w, int h) {
		try {
			image = new Image(s);
			image = image.getSubimage(x, y, w, h);
			width = image.width;
			height = image.height;
			xscale = ms;
			yscale = ms;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPosition(double x, double y) {
		this.x = (x * xscale) % width;
		this.y = (y * yscale) % height;
	}
	
	public void setPosition(double x, double y, int scalex, int scaley){
		this.x = (x * xscale) % (width*scalex);
		this.y = (y * yscale) % (height*scaley);
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setScale(double xscale, double yscale) {
		this.xscale = xscale;
		this.yscale = yscale;
	}
	
	public void setDimensions(int i1, int i2) {
		width = i1;
		height = i2;
	}
	
	public double getx() { return x; }
	public double gety() { return y; }
	
	public void update() {
		x += dx;
		while(x <= -width) x += width;
		while(x >= width) x -= width;
		y += dy;
		while(y <= -height) y += height;
		while(y >= height) y -= height;
		
		if(y == 0)dy = 0;
	}
	
	public void draw(Renderer r) {
		
		r.drawImage(image, (int)x, (int)y);
		
		if(x < 0) {
			r.drawImage(image, (int)x + Settings.WIDTH, (int)y);
		}
		if(x > 0) {
			r.drawImage(image, (int)x - Settings.WIDTH, (int)y);
		}
		/*if(y < 0) {
			r.drawImage(image, (int)x, (int)y + Settings.HEIGHT);
		}
		if(y > 0) {
			r.drawImage(image, (int)x, (int)y - Settings.HEIGHT);
		}*/
	}
	
}







