package com.quad.core.fx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image
{
	public int width, height;
	public ShadowType shadowType = ShadowType.NONE;
	public int[] pixels;
	private BufferedImage image;
	
	public Image(String path)
	{
		try
		{
			image = ImageIO.read(Image.class.getResourceAsStream(path));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		width = image.getWidth();
		height = image.getHeight();
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		
		image.flush();
	}
	
	public Image getSubimage(int x, int y, int w, int h){
		BufferedImage i = this.getImage().getSubimage(x, y, w, h);
		return new Image(i.getWidth(), i.getHeight(), image.getRGB(x, y, w, h, null, 0, w));
	}
	
	public Image(int w, int h, int[] p)
	{
		this.width = w;
		this.height = h;
		this.pixels = p;
	}
	
	public BufferedImage getImage(){
		return image;
	}
}
