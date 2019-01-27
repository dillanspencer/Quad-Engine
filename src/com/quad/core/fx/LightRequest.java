package com.quad.core.fx;

public class LightRequest
{
	public Light light;
	public int x, y;
	
	public LightRequest(Light light, int x, int y)
	{
		this.light = light;
		this.x = x;
		this.y = y;
	}
}
