package com.quad.core.components;

import java.util.ArrayList;

import com.quad.core.GameContainer;
import com.quad.core.Renderer;



public class ObjectManager
{
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	
	public void updateObjects(GameContainer gc, float dt)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			objects.get(i).updateComponents(gc, dt);
			
			if(objects.get(i).isDead())
				objects.remove(i);
		}
	}
	
	public void renderObjects(GameContainer gc, Renderer r)
	{
		for(int i = 0; i < objects.size(); i++)
		{
			objects.get(i).renderComponents(gc, r);
		}
	}
	
	public void diposeObjects()
	{
		for(GameObject go : objects)
		{
			go.dispose();
		}
	}
	
	public void addObject(GameObject object)
	{
		objects.add(object);
	}
	
	public GameObject findObject(String tag)
	{
		for(GameObject go : objects)
		{
			if(go.getTag().equalsIgnoreCase(tag))
			{
				return go;
			}
		}
		
		return null;
	}
	
	
}
