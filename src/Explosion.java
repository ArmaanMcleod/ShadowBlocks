/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the Explosion class which creates an explosion
 * in world map. 
 */

/**
 * Explosion class for the game.
 * Represents an explosion in the world.
 */
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Explosion extends Sprite {
	private static final String EXPLOSION_SRC = "assets/explosion.png";
	
	public Explosion(float x, float y) 
	throws SlickException {
		super(EXPLOSION_SRC, x, y);

	}
	
	
	/** Overrides render method in parent super class.
	 * Allows the explosion to be drawn centered on the screen.
	 * @param world the World passed as an arguement
	 * @param g Graphics libraru used for drawing
     * @return void
     */
	@Override
	public void render(World world, Graphics g) {
		float width = getWidthCoord(world);
		float height = getHeightCoord(world);
		
		// Center the image
		// For some reason all the other sprites are drawn on the screen
		// with just image.draw(), but the explosion seems to be off if I use
		// draw(). Used drawCentered() to fix this issue. 
		getImage().drawCentered(width, height);
	}
}