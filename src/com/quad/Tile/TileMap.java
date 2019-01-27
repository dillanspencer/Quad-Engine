package com.quad.Tile;

/**
 * @author Dillan Spencer
 * This class handles the tilemap in the game
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.fx.Image;
import com.quad.core.fx.ShadowType;
import com.quad.entity.Accessor;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;


public class TileMap {
	
	// position
	private double x;
	private double y;
	
	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	//smooth movement
	private TweenManager tween;
		
	// map
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	// tileset
	private Image tileset;
	private int numTilesAcross;
	private Tile[][] tiles;
	
	private GameContainer gc;
	
	// drawing
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	// effects
	private boolean shaking;
	private int intensity;
	
	public TileMap(GameContainer gc, int tileSize) {
		this.tileSize = tileSize;
		this.gc = gc;
		numRowsToDraw = gc.getHeight() / tileSize + 2;
		numColsToDraw = gc.getWidth() / tileSize + 2;
		//Tween engine
		Tween.registerAccessor(this.getClass(), new Accessor());
		tween = new TweenManager();
	}
	
	public void loadTiles(String s) {
		
		try {

			tileset = new Image(s);
			numTilesAcross = tileset.getImage().getWidth() / tileSize;
			tiles = new Tile[2][numTilesAcross];
			
			
			Image subimage;
			for(int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.getSubimage(
							col * tileSize,
							0,
							tileSize,
							tileSize
						);
				tiles[0][col] = new Tile(subimage, Tile.NORMAL);
				subimage = tileset.getSubimage(
							col * tileSize,
							tileSize,
							tileSize,
							tileSize
						);
				tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
				tiles[1][col].getImage().shadowType = ShadowType.FADE;
			}
			
			//list of all tiles that arnt collidable that need shadows
			for(int i = 5; i <= 16; i++){
				tiles[0][i].getImage().shadowType = ShadowType.FADE;
				
			}
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadMap(String s) {
		
		try { 
			
			
			
				InputStream in = getClass().getResourceAsStream(s);
				BufferedReader br = new BufferedReader(
							new InputStreamReader(in)
						);
			
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			xmin = gc.getWidth() - width;
			xmax = 0;
			ymin = gc.getHeight() - height;
			ymax = 0;
			
			String delims = "\\s+";
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setTile(int row, int col, int index) {
		map[row][col] = index;
	}
	
	public void replace(int i1, int i2) {
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				if(map[row][col] == i1) map[row][col] = i2;
			}
		}
	}
	
	public int getTileSize() { return tileSize; }
	public double getx() { return x; }
	public double gety() { return y; }
	public int getXmin() { return xmin;}
	public int getXmax() { return xmin;}
	public int getYmin() { return xmin;}
	public int getYmax() { return xmin;}
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getNumRows() { return numRows; }
	public int getNumCols() { return numCols; }
	public int getNumColsToDraw(){ return numColsToDraw;}
	public int getNumRowsToDraw(){ return numRowsToDraw;}
	
	public int getIndex(int row, int col) {
		return map[row][col];
	}
	
	public int getType(int row, int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}
	public boolean isShaking() { return shaking; }
	
	public void setShaking(boolean b, int i) { shaking = b; intensity = i; }
	public void setBounds(int i1, int i2, int i3, int i4) {
		xmin = gc.getHeight() - i1;
		ymin = gc.getWidth() - i2;
		xmax = i3;
		ymax = i4;
	}
	
	public void setPosition(double x, double y) {
		
		if(y <= 0 && y > (-height + gc.getHeight())) {
			Tween.to(this, Accessor.POSITION_Y, 0.12f).target((float)y, (float)this.y).start(tween);
			}
			else y = 0;
			if(x < 0 && x > (-width + gc.getWidth())) {
				Tween.to(this, Accessor.POSITION_X, 0.12f).target((float)x, (float)this.x).start(tween);
			}
			else x = 0;
			colOffset = (int)-this.x / tileSize;
			rowOffset = (int)-this.y / tileSize;
		
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void fixBounds() {
		if(x < xmin) x = xmin;
		if(y < ymin) y = ymin;
		if(x > xmax) x = xmax;
		if(y > ymax) y = ymax;
	}
	
	public void update(float dt) {
		if(shaking) {
			this.x += Math.random() * intensity - intensity / 2;
			this.y += Math.random() * intensity - intensity / 2;
		}
		
		tween.update(dt);
		
	}
	
	public void draw(Renderer g) {
		
		
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
		
			if(row >= numRows) break;
			
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {
				
				if(col >= numCols) break;
				if(map[row][col] == 0) continue;
				
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				g.drawImage(
					tiles[r][c].getImage(),
					(int)(x + col * tileSize + tiles[r][c].x),
					(int)(y + row * tileSize + tiles[r][c].y)
				);		
			}
			
		}
		
	}
	
}