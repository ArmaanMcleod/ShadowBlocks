/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the Player class which handles
 * the movement and drawing of a player on the game map
 */



import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * Player class for the game.
 * Represents the player on the screen
 * Includes graphics and behaviour
 */
public class Player extends Moveable {
	private static final String PLAYER_SRC = "assets/player_left.png";
	
	// Sound files
	private static final String MOVE_SOUND = 
			          "assets/Swoosh_3-SoundBible_com-1573211927.ogg";
	
	private static final String UNDO_SOUND = 
			          "assets/Reverse-SoundBible_com-643522429.ogg";
	
	private static final float PITCH = 0.5f;
	private static final float VOLUME = 0.3f;
	
	// boolean determining if the player is moving
	public boolean makeMove = false;
	
	// Sound variables for moving and undoing
	private Sound move;
	private Sound reverse;
	
	public Player(float x, float y) 
	throws SlickException {
		super(PLAYER_SRC, x, y);
		
		// initialise player tile coordinates
		this.setWidth(x);
		this.setHeight(y);
		this.setDirection(NO_DIRECTION);
		
		history.push(Coordinates.newCoordinates(this.getWidth(), 
				                                this.getHeight()));
		
		move = new Sound(MOVE_SOUND);
		reverse = new Sound(UNDO_SOUND);
	}
	
	/** Overriding method that moves the player
	 * @param world the World passed as an arguement
     * @param input The keyboard state
     * @param delta Time passed since last frame (milliseconds).
     * @return void
	 * @throws SlickException 
     */
	@Override
	public void update(World world, Input input, int delta) {
			// If escape key is pressed, exit out of game
	 		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
	 			System.exit(0);
	 			makeMove = false;
	 		}
	 		
	 		// If R key is pressed, restart the level
	 		if (input.isKeyPressed(Input.KEY_R)) {
	 			world.restartLevel = true;
	 			makeMove = false;
	 		}
	 		
	 		// If the Z key is pressed, undo the game state
	 		if (input.isKeyPressed(Input.KEY_Z)) {
	 			makeMove = false;
	 			
	 			// Reassure that the move count is more than one
	 			// Other wise the player hasn't moved
	 			if (world.MOVE_COUNT > 0) {
	 				reverse.play();
	 				
	 				world.MOVE_COUNT--;
	 				
	 				// First undo the player
	 				undoPlayer();
	 				
	 				// Then undo the pushables
	 				world.undoPushables();
	 				
	 				// Reset the block locations of the pushable objects
	 				world.resetBlocks();
	 			}
	 		}
	 				
	 		// If up is pressed, and the above tile that the player wishes
	 		// to move to is not blocked, then move the player up 
	 		// sone tile(32 pixels)
	 		if (input.isKeyPressed(Input.KEY_UP)) {
	 			
	 			move.play(PITCH, VOLUME);
	 			
	 			makeMove = true;
	 			
	 			world.MOVE_COUNT++;
	 			
	 			// Update the history of the game
	 			world.updateHistory();
	 			
	 			if (!world.isBlocked(getHeight()-1, getWidth()) && 
	 				!world.isCracked(getHeight()-1, getWidth())) {
	 				
	 				// If this passes, dont move the player
	 				if (((world.isBlock(getHeight()-1, getWidth()) &&
	 					  world.isBlock(getHeight()-2, getWidth())) ||
	 				     (world.isBlock(getHeight()-1, getWidth()) &&
	 				      world.isBlocked(getHeight()-2, getWidth())) ||
	 				     (checkEnemyBlocks(world, getHeight(),
	 				    		           getWidth(), UP)))) {
	 					
	 					setDirection(NO_DIRECTION);
	 					
	 				// The player can move in this valid vertical position
	 				} else {
	 					
	 					setDirection(UP);
	 					moveVertical(getHeight()-1);

	 				}
	 			}
	 			
	 		} 
	 		
	 		// If down is pressed, and the tile below tthe player is
	 		// not blocked, then move the player down one tile(32 pixels)
	 		if (input.isKeyPressed(Input.KEY_DOWN)) {
	 			
	 			move.play(PITCH, VOLUME);
	 			
	 			makeMove = true;
	 			
	 			world.MOVE_COUNT++;
	 			
	 			world.updateHistory();

	 			// Same logic as above
	 			if (!world.isBlocked(getHeight()+1, getWidth()) && 
	 				!world.isCracked(getHeight()+1, getWidth())) {
	 				
	 				if (((world.isBlock(getHeight()+1, getWidth()) && 
	 				     (world.isBlock(getHeight()+2, getWidth()) ||
	 					 (world.isBlock(getHeight()+1, getWidth()) &&
	 					  world.isBlocked(getHeight()+2, getWidth())) ||
	 					 (checkEnemyBlocks(world, getHeight(), 
	 							           getWidth(), DOWN)))))) {
	 					
	 					setDirection(NO_DIRECTION);
	 				} else {
	 					
	 					setDirection(DOWN);
	 					moveVertical(getHeight()+1);
	 					
	 				}
	 			}

	 		}
	 				
	 		// If left is pressed, and the tile to the left of the the player
	 		// is not blocked, then move the player left one tile(32 pixels)
	 		if (input.isKeyPressed(Input.KEY_LEFT)) {
	 			
	 			move.play(PITCH, VOLUME);
	 			
	 			makeMove = true;
	 			
	 			world.MOVE_COUNT++;
	 			
	 			world.updateHistory();
	 			
	 			if (!world.isBlocked(getHeight(), getWidth()-1) && 
	 				!world.isCracked(getHeight(), getWidth()-1)) {
	 				
	 				// Same logic as above
	 				if (((world.isBlock(getHeight(), getWidth()-1) && 
	 					  world.isBlock(getHeight(), getWidth()-2)) ||
	 					 (world.isBlock(getHeight(), getWidth()-1) &&
	 					  world.isBlocked(getHeight(), getWidth()-2)) ||
	 					(checkEnemyBlocks(world, getHeight(), 
	 							          getWidth(), LEFT)))) {
	 					
	 					setDirection(NO_DIRECTION);
	 				} else {
	 					
	 					setDirection(LEFT);
	 					moveHorizontal(getWidth()-1);
	 				}
	 			}

	 		}
	 				
	 		// If left is pressed, and the tile to the left of the the player
	 		// is not blocked, then move the player left one tile(32 pixels)
	 		if (input.isKeyPressed(Input.KEY_RIGHT)) {
	 			
	 			move.play(PITCH, VOLUME);
	 			
	 			makeMove = true;
	 			
	 			world.MOVE_COUNT++;
	 			
	 			world.updateHistory();
	 		
	 			// Same logic as aboved
	 			if (!world.isBlocked(getHeight(), getWidth()+1) && 
	 				!world.isCracked(getHeight(), getWidth()+1)) {
	 				
	 				if (((world.isBlock(getHeight(), getWidth()+1) && 
	 					  world.isBlock(getHeight(), getWidth()+2)) ||
	 				     (world.isBlock(getHeight(), getWidth()+1) &&
	 				      world.isBlocked(getHeight(), getWidth()+2)) ||
	 				     (checkEnemyBlocks(world, getHeight(), 
	 				    		           getWidth(), RIGHT)))) {
	 					
	 					setDirection(NO_DIRECTION);
	 				} else {
	 					setDirection(RIGHT);
	 					moveHorizontal(getWidth()+1);
	 				}
	 
	 			}
	 			
	 		}
	}
	
	/** Undoes a player move. Checks if the top element of the
	 * history stack can be popped, then sets the players current location
	 * to the historical location.
	 * @return void
	 */
	public void undoPlayer() {
		
		// Checks that hsitory is not empty
		if (!history.isEmpty()) {
			
			// Pops history
			Coordinates<Float, Float> topPlayerHistory = history.pop();
				
			float playerXHistory = topPlayerHistory.getX();
			float playerYHistory = topPlayerHistory.getY();
			
			// Sets player new location to the history coordinates
			moveHorizontal(playerXHistory);
			moveVertical(playerYHistory);
				
			setDirection(NO_DIRECTION);	
		}
	}
	
	/** Used to determine check if the player is pushing a block 
	 * onto an enemy unit. 
     * @param x The block location in the x direction
     * @param y The block location in the y direction
     * @return boolean
     */
	public boolean checkEnemyBlocks(World world, float x, 
			                        float y, int direction) {
		
		// Loops over enemy objects and checks if they exist after a block
		// If so, return true.
		for (Enemy enemy: world.enemies) {
			if (enemy != null) {
				if (direction == enemy.UP) {
					if (world.isBlock((int)x-1, (int)y) &&
						enemy.checkLocation((int)y, (int)x-2)) {
						return true;
					}
				} else if (direction == enemy.DOWN) {
					if (world.isBlock((int)x+1, (int)y) &&
						enemy.checkLocation((int)y, (int)x+2)) {
						return true;
					}
				} else if (direction == enemy.LEFT) {
					if (world.isBlock((int)x, (int)y-1) &&
						enemy.checkLocation((int)y-2, (int)x)) {
						return true;
					}
				} else if (direction == enemy.RIGHT) {
					if (world.isBlock((int)x, (int)y+1) &&
						enemy.checkLocation((int)y+2, (int)x)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

	/** Overriding method of render for the player. This was added because
	 * as part of my extension, I decided to make the player face the
	 * direction it is moving. This only applies for left and right
	 * movements
	 * @param world the World object passed as an arguement
	 * @param g The Slick graphics object, used for drawing.
	 * @return void
	 */
	@Override
	public void render(World world, Graphics g) {
		float width = getWidthCoord(world);
		float height = getHeightCoord(world);

		// Flip the image depending on direction of the player
		getImage().getFlippedCopy(getDirection() == LEFT 
						          ? false : true, false)
		                          .draw(width, height);
	}
	
}