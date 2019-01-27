package com.quad.core.fx;

/**
 * 
 * @author Dillan Spencer
 * This Class is for loading images into the game
 * Load images and grab them statically for use in your Game Object classes
 * 
 */

public class Content {

	//load all Content for your animated images 
	public static Image[][] Test = load("testFile.png", 100, 100);
	
	public static Image[][] load(String s, int w, int h) {
		Image[][] ret;
		try {
			Image spritesheet = new Image(s);
			int width = spritesheet.width / w;
			int height = spritesheet.height / h;
			ret = new Image[height][width];
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
				}
			}
			return ret;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading graphics.");
			System.exit(0);
		}
		return null;
	}

}
