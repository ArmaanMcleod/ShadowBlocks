/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the Ice class which handles the pushing 
 * of the ice block in the game map.
 */

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


/**
 * Ice class for the game.
 * Represents a single Ice Block on the screen
 */
public class Ice extends Pushable {
	private static final String ICE_SRC = "assets/ice.png";
	
	// 250 milliseconds per move 
	private static final int MAX_TIME = 250;
	
	// Time stamp to be accumulateds
	private int timeStamp = 0;

	// Destination of ice block in x and y coordinates
	private float destinationX;
	private float destinationY;
	
	public Ice(float x, float y) 
	throws SlickException {
		super(ICE_SRC, x, y);
		
		this.setWidth(x);
		this.setHeight(y);

		this.setDestinationX(this.getWidth());
		this.setDestinationY(this.getHeight());
	}
	
	// Getting the y destination coordinate
	public float getDestinationY() {
		return destinationY;
	}
	
	// Setting the y destination coordinate
	public void setDestinationY(float destinationY) {
		this.destinationY = destinationY;
	}

	// Getting the x destination coordinate
	public float getDestinationX() {
		return destinationX;
	}

	// Setting the x destination coordinate
	public void setDestinationX(float destinationX) {
		this.destinationX = destinationX;
	}
	
	
	/** Overrides render update method in the parent super class.
	 * Handles the moving of the ice block.
	 * @param world the World passed as an arguement
	 * @param input The keyboard state.
     * @param delta Time passed since last frame (milliseconds).
     * @return void
     */
	@Override
	public void update(World world, Input input, int delta) {
		
		// Increase time passed since last frame
		timeStamp += delta;
		
		// Keep track of x and y coordinates
		float iceX = getWidth();
		float iceY = getHeight();
		
		// If the player pushes up
		if (isPlayerPush(world, iceX, iceY, UP)) {
			
			// set the timestamp to max time
			timeStamp = MAX_TIME;
			
			// Count how many legal moves the ice block can move in
			// the up direction
			int moveCount = 0;
			while (isBlockMove(world, iceX, iceY-1)) {
				iceY -= 1;
				moveCount++;
			}
			
			// Calculate the destination of the ice block in 
			// the y direction
			destinationY = getHeight() - moveCount;
			
			// Set previous position to false
			world.blocks[(int) iceY+moveCount][(int) iceX] = false;
		}
		
		// If the player is pushing down
		// Same procedure as above
		if (isPlayerPush(world, iceX, iceY, DOWN)) {
			timeStamp = MAX_TIME;
			
			int moveCount = 0;
			while (isBlockMove(world, iceX, iceY+1)) {
				iceY += 1;
				moveCount++;
			}
			
			destinationY = getHeight() + moveCount;
			
			world.blocks[(int) iceY-moveCount][(int) iceX] = false;
		}
		
		// If the player or rogue is pushing down
		// Same procedure as above
		if (isPlayerPush(world, iceX, iceY, LEFT) || 
			isRoguePush(world, iceX, iceY, LEFT)) {
			timeStamp = MAX_TIME;
			
			int moveCount = 0;
			while (isBlockMove(world, iceX-1, iceY)) {
				iceX -= 1;
				moveCount++;
			}

			destinationX = getWidth() - moveCount;
			
			world.blocks[(int) iceY][(int) iceX+moveCount] = false;
		}
		
		// If the player or rogue is pushing down
		// Same procedure as above
		if (isPlayerPush(world, iceX, iceY, RIGHT) ||
			isRoguePush(world, iceX, iceY, RIGHT)) {
			timeStamp = MAX_TIME;
			
			int moveCount = 0;
			while (isBlockMove(world, iceX+1, iceY)) {
				iceX += 1;
				moveCount++;
			}
			destinationX = getWidth() + moveCount;
					
			world.blocks[(int) iceY][(int) iceX-moveCount] = false;
		}

		// If timestamp is over the time limit
		if (timeStamp >= MAX_TIME) {
			
			// Move the ice block
			moveIceBlock(world);
			
			// Reset timer 
			timeStamp = 0;
		}
	}
	
	
	/** Moves the Ice Block according to the destination coordinates
	 * @param world the World passed as an arguement
     * @return void
     */
	public void moveIceBlock(World world) {
		
		// If y coordinate is less than the destination y coordinate
		// Move down
		if(this.getHeight() < destinationY) {
			moveVertical(getHeight() + 1);
		}
		// If y coordinate is more than the destination y coordinate
		// Move up
		if (this.getHeight() > destinationY) {
			moveVertical(getHeight() - 1);
		}
		// If x coordinate is less than the destination x coordinate
		// Move right
		if (this.getWidth() < destinationX) {
			moveHorizontal(getWidth() + 1);
		}
		// If x coordinate is less than the destination x coordinate
		// Move left
		if (this.getWidth() > destinationX) {
			moveHorizontal(getWidth() - 1);
		}
		
		// If destination is reached in both the x and y direction
		// Set the location to blocked
		if(destinationX == getWidth() && destinationY == getHeight()) {
			world.blocks[(int) destinationY][(int) destinationX] = true;
		}
	}
}