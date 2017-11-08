/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the Sprite class which handles
 * each sprite rendered onto the game map
 */

import org.newdawn.slick.Input;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Abstract Sprite class for the game.
 * Represents a single sprite on the screen
 * Includes graphics and behaviour
 */

public abstract class Sprite {
	private Image image = null;

	private float x;
	private float y;
	
	public static final int HEIGHT = 0;
	public static final int WIDTH = 1;
	
	public final int NO_DIRECTION = 0;
	public final int UP = 1;
	public final int DOWN = 2;
	public final int LEFT = 3;
	public final int RIGHT = 4;
	
	public Sprite(String image_src, float x, float y) 
    throws SlickException {
		this.x = x;
		this.y = y; 
		
		// Create the image
		try {
			this.setImage(new Image(image_src));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/** Converts tile coordninate to pixel coordinate
     * @param screenSize The size(x or y) of the screen
     * @param mapSize The size(x or y) of the map
     * @param axis The axis(x or y)
     * @return float pixel coordinate
     */
	public static float tileToPixel(int screenSize, int mapSize, float axis) {
		return ((screenSize - mapSize * App.TILE_SIZE) / 2) + axis * App.TILE_SIZE;
	}
	
	//Returns image object
	public Image getImage() {
		return image;
	}

	// Sets image object
	public void setImage(Image image) {
		this.image = image;
	}

	// Returns tile width
	public float getWidth() {
		return this.x;
	}
	
	// Sets tile width
	public void setWidth(float x) {
		this.x = x;
	}
	
	// Returns tile height
	public float getHeight() {
		return this.y;
	}
	
	// Sets tile height
	public void setHeight(float y) {
		this.y = y;
	}
	
	// Returns pixel width in pixels
	public float getWidthCoord(World world) {
		return tileToPixel(App.SCREEN_WIDTH, world.MAP_WIDTH, this.x);
	}
	
	// Returns pixel height in pixels
	public float getHeightCoord(World world) {
		return tileToPixel(App.SCREEN_HEIGHT, world.MAP_HEIGHT, this.y);
	}

	// Updates sprite 
	public void update(World world, Input input, int delta) {
		return;
	}

	/** Render the sprite
     * @param g The Slick graphics object, used for drawing.
     * @return void
     */
	public void render(World world, Graphics g) {
		float width = getWidthCoord(world);
		float height = getHeightCoord(world);
		
		getImage().draw(width, height);
	}
}
