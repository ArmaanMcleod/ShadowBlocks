/* SWEN20003 Object Oriented Software Development 2017
 * Project 2
 * Author: Armaan Dhaliwal-McLeod
 * Student Number : 837674
 * Email: dhaliwala@student.unimelb.edu.au
 * 
 * This file contains the Coordinate class which is a generic
 * class for handling x and y coordinates. 
 */

/**
 * Coordinates class for the game.
 * Helpful for storing two coordinates.
 */
public class Coordinates<X, Y> {
    
    private final X x;
    private final Y y;

    public static <X, Y> Coordinates<X, Y> newCoordinates(X x, Y y) {
        return new Coordinates<>(x, y);
    }

    public Coordinates(X element1, Y element2) {
        this.x = element1;
        this.y = element2;
    }

    public X getX() {
    	return x;
    }
    
    public Y getY() {
    	return y;
    }
}