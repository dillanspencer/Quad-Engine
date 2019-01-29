# Quad Engine
> 2D Platformer Game Engine

The __Quad Engine__ was developed to make 2D game programming easy for everyone. The engine is made to be implemented into your project before you start developing.

Using Java 8 internal libraries, it is specifically designed for 2D games (no support for 3D at the moment), and proposes a set of functions for 2D resources management (_images_, _sprites_, _animations_, _tiles_...).
Inputs and outputs are also available, with an easy keys retrieval, mouse movement... Management of music file are also available (_Wav_, _Midi_).
Windowed and full-screen are fully supported, with a complete frame rate control.

In the __Quad Engine's__ current version, it greatly simplifies the development of __Platformer__, and __Top Down__ games.

## General Features
* #### __GameContainer__
>  * Simple initialization, with title control, and screen configuration 
>  * Extrapolation control (_machine speed independent_)
>  * Sequence control (_intro, menu, game, credits..._)
>  * Easy resource management (_relative to resource directory_)
>  * Advanced image usage (_sprite, animation, tile, font, parallax_)

* #### __Renderer__
>  * 2D dynamic lighting
>  * 2D shadow rendering using ShadowTypes (_Full, Half, Total, None_)
>  * Renders images (_sprites, animations, objects, shapes_)

* #### __Components__
>  * GameObject class handles objects in the game (_player, enemies, items_) 
>  * Easy object managing
>  * Object physics, handles collisions between TileMap and other objects

* #### __FX__
>  * Resource managment (_sprites, images)
>  * Font for rendering text
>  * Audio handling (_support for Wav_)
>  * Light handling (_size, color_)

* #### __TileMap__
>  * 2D parallax background support
>  * Tile based implementation from single image
>  * Smooth tile movement using Tween Engine

## Installation

Steps to include the __Quad Engine__ in your project:

1. Install at least the [Java JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
2. Choose your favourite IDE ([Eclipse](http://www.eclipse.org/downloads/), [Netbeans](https://netbeans.org/downloads/)...)
3. Download the latest [Quad Engine]
4. Include all __Quad Engine__ classes you need for your project:
   * __Quad Engine__ _(minimum requirement)_
     * __GameContainer__ _(main game loop)_
     * __Abstract Game__ _(base for game development)_
     * __Renderer__ _(support for rendering)_
     * __GameObject__ _(base for creating objects)_
     * __Background__ _(parralax background support)_
     * __TileMap__ _(support for creating dynamic map)_
5. You are now ready to use the __Quad Engine__ in your project

## Getting Started

Once you installed the __Quad Engine__ in your project, you may would like to know how to prepare a quick sample as a first try:

#### Main class

```java
public class GameManager {
    public static void main(String[] args) {
         GameContainer gc = new GameContainer(new AbstractGame());
         gc.setWidth(Settings.WIDTH);
         gc.setHeight(Settings.HEIGHT);
         gc.setScale(Settings.SCALE);
         gc.setTitle("Quad Engine 1.01");
         gc.setClearScreen(true);
         gc.start();
    }
}
```

#### Minimal State

```java
public class StateTest extends State{

	@Override
	public void init(GameContainer gc) {
		// Initiate state
		
	}

	@Override
	public void update(GameContainer gc, float dt) {
		// Update state
		
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		// Render state
		
	}

	@Override
	public void dipose() {
		// Dispose state
		
	}

}
```

#### AbstractGame (Add this code)

```java
private void loadState(int state) {
	// implement this into your code. TestState() is your state and VALUE is its index.
	if(state == VALUE) states[currentState] = new TestState();
}
```

