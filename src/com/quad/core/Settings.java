package com.quad.core;

/**
 * 
 * @author Dillan Spencer
 * This class holds static variables that 
 * can be accessed from any class
 *
 */

public class Settings {
	
	public static final int WIDTH = 200;
	public static final int HEIGHT = 150;
	public static final int SCALE = 4;	
	
	public static final int FPS = 60;
	
	public static boolean LIGHT = false;
		
	public static void setLight(GameContainer gc, boolean i){
		Settings.LIGHT = i;
		gc.setLightEnable(i);
		gc.setDynamicLights(i);
	}
	
	public static void changeFps(GameContainer gc, int i){
		gc.setFrameCap(i);
	}

}
