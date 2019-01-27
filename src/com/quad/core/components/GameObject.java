package com.quad.core.components;

/**
 * @author Dillan Spencer
 * This class hold all of the properties of an object within the game
 * Extend this class into all of your objects
 */

import java.awt.Rectangle;
import java.util.ArrayList;

import com.quad.Tile.Tile;
import com.quad.Tile.TileMap;
import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.entity.Animation;


public abstract class GameObject{
	
	// tile stuff
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	// position and vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	protected int rowTile;
	protected int colTile;
	
	// dimensions
	protected int width;
	protected int height;
	
	// collision box
	protected int cwidth;
	protected int cheight;
	protected int coffx;
	protected int coffy;
	
	// collision
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	// animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight;
	
	// movement
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	protected boolean fallDamage;
	
	// movement attributes
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;
	
	//attributes
	protected int health;
	protected int maxHealth;

	protected String tag = "null";
	protected boolean dead = false;
	protected boolean movement = true;
	protected ArrayList<Component> components = new ArrayList<Component>();
	
	public GameObject(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize();
		animation = new Animation();
		facingRight = true;
	}
	

	
	public boolean intersects(GameObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}
	
	public boolean intersects(Rectangle r) {
		return getRectangle().intersects(r);
	}
	
	public boolean contains(GameObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.contains(r2);
	}
	
	public boolean contains(Rectangle r) {
		return getRectangle().contains(r);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(
				(int)((x) - cwidth / 2),
				(int)((y) - cheight / 2)+coffy,
				cwidth,
				cheight
		);
	}
	
	public int getRow() { return rowTile; }
	public int getCol() { return colTile; }
	
	public void calculateCorners(double x, double y) {
		int leftTile = (int) (((x) - cwidth / 2)+coffx) / tileSize;
		int rightTile = (int) (((x) + cwidth / 2 - 1)+coffx) / tileSize;
		int topTile = (int) (((y) - cheight / 2) + coffy) / tileSize;
		int bottomTile = (int) (((y) + cheight / 2 - 1)+coffy) / tileSize;
		
		if (topTile < 0 || bottomTile >= tileMap.getNumRows() || leftTile < 0 || rightTile >= tileMap.getNumCols()) {
			topLeft = topRight = bottomLeft = bottomRight = false;
			return;
		}
		
		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);
		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight = br == Tile.BLOCKED;
	}
	
	
	
	public void checkTileMapCollision() {
		
		rowTile = (int) (y / tileSize);
		colTile = (int) (x / tileSize);
		
		currCol = (int)x / tileSize;
		currRow = (int)y / tileSize;
		
		xdest = x + dx;
		ydest = y + dy;
		
		xtemp = x;
		ytemp = y;
		
		calculateCorners(x, ydest);
		if(dy < 0) {
			if(topLeft || topRight) {
				dy = 0;
				ytemp = currRow * tileSize + cheight / 2;
			}
			else {
				ytemp += dy;
			}
		}
		if(dy > 0) {
			if(bottomLeft || bottomRight) {
				dy = 0;
				falling = false;
				//ytemp = (currRow + 2) * tileSize - cheight / 2 - coffy;
			}
			else {
				ytemp += dy;
			}
		}
		
		calculateCorners(xdest, y);
		if(dx < 0) {
			if(topLeft || bottomLeft) {
				dx = 0;
				//xtemp = currCol * tileSize + cwidth / 2;
			}
			else {
				xtemp += dx;
			}
		}
		if(dx > 0) {
			if(topRight || bottomRight) {
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2;
			}
			else {
				xtemp += dx;
			}
		}
		
		if(!falling) {
			calculateCorners(x, ydest + 1);
			if(!bottomLeft && !bottomRight) {
				falling = true;
			}
		}
		
	}
	
	public int getx() { return (int)x; }
	public int gety() { return (int)y; }
	public int getdx() {return (int)dx; }
	public int getdy() {return (int)dy; } 
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getCWidth() { return cwidth; }
	public int getCHeight() { return cheight; }
	public boolean isFacingRight() { return facingRight; }
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setMapPosition() {
		xmap = tileMap.getx();
		ymap = tileMap.gety();
	}
	
	public void setLeft(boolean b) { left = b; }
	public void setRight(boolean b) { right = b; }
	public void setUp(boolean b) { up = b; }
	public void setDown(boolean b) { down = b; }
	public void setJumping(boolean b) { jumping = b; }
	
	public boolean notOnScreen() {
		return x + xmap + width < 0 ||
			x + xmap - width > GameContainer.width ||
			y + ymap + height < 0 ||
			y + ymap - height > GameContainer.height;
	}
	
	public void updateComponents(GameContainer gc, float dt)
	{
		for(Component c : components)
		{
			c.update(gc, this, dt);
		}
		
	}
	
	public void renderComponents(GameContainer gc, Renderer r)
	{
		for(Component c : components)
		{
			c.render(gc, r);
		}
		
		setMapPosition();			
		
		if(facingRight) {
			r.drawImage(
				animation.getImage(),
				(int)(x + xmap - width / 2),
				(int)(y + ymap - height / 2),
				width,
				height
			);
		}
		else {
			r.drawImageReverted(
				animation.getImage(),
				(int)(x + xmap - width / 2 + width),
				(int)(y + ymap - height / 2),
				width,
				height
			);
		}
		
		Rectangle re = getRectangle();
		re.x += xmap;
		re.y += ymap;
		
		//debug collision size
		//r.drawRect(re.x, re.y,re.width , re.height, 0xffffff, ShadowType.NONE);

		
	}
	
	public abstract void componentEvent(String name, GameObject object);
	public abstract void dispose();
	
	public void addComponent(Component c)
	{
		components.add(c);
	}
	
	public void removeComponent(String tag)
	{
		for(int i = 0; i < components.size(); i++)
		{
			if(components.get(i).getTag().equalsIgnoreCase(tag))
			{
				components.remove(i);
			}
		}
	}
	
	public String getTag()
	{
		return tag;
	}
	public void setTag(String tag)
	{
		this.tag = tag;
	}
	public boolean isDead()
	{
		return dead;
	}
	public void setDead(boolean dead)
	{
		this.dead = dead;
	}
	public boolean getMovement(){
		return movement;
	}
	public void setMovement(boolean b){
		movement = b;
		if(!b) left = right = jumping = falling = false;
	}
	public double getX()
	{
		return x;
	}
	public void setX(double x)
	{
		this.x = x;
	}
	public double getY()
	{
		return y;
	}
	public void setY(double y)
	{
		this.y = y;
	}
	public double getW()
	{
		return width;
	}
	public void setW(int w)
	{
		this.width = w;
	}
	public double getH()
	{
		return height;
	}
	public void setH(int h)
	{
		this.height = h;
	}

	
}
