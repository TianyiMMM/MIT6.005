/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.List;
import java.util.ArrayList;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
    	turtle.forward(sideLength);
    	turtle.turn(90);
    	turtle.forward(sideLength);
    	turtle.turn(90);
    	turtle.forward(sideLength);
    	turtle.turn(90);
    	turtle.forward(sideLength);
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        double sidesDouble = (double)sides;
        return 180.0-360.0/sidesDouble;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
    	double sides = 360.0/(180.0-angle);
        return (int)(Math.round(sides));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double angle = calculateRegularPolygonAngle(sides);
        for (int i = 0; i < sides; i++) {
        	turtle.forward(sideLength);
        	turtle.turn(180.0-angle);
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the heading
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentHeading. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to heading (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY,
                                                 int targetX, int targetY) {
    	double diffX = (double)(targetX-currentX);
    	double diffY = (double)(targetY-currentY);
    	double z = Math.sqrt(diffX * diffX + diffY * diffY);
    	double angle = Math.toDegrees(Math.acos(diffY/z));
    	double sign = Math.toDegrees(Math.asin(diffX/z));
    	
    	double heading = 0;
    	if (sign >= 0) {
    		heading = angle - currentHeading;
    	} else if (sign < 0) {
    		angle = 360.0-angle;
    		heading = angle - currentHeading;
    	}
    	
    	if (heading >= 0) {
    		return heading;
    	} else {
    		return 360.0+heading;
    	}
    }

    /**
     * Given a sequence of points, calculate the heading adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateHeadingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
        int len = xCoords.size();
        int index = 0;
        List<Double> headings = new ArrayList<Double>();
        double currentHeading = 0;
        while (index < len-1) {
        	double nextHeading = calculateHeadingToPoint(currentHeading, xCoords.get(index), yCoords.get(index)
        			, xCoords.get(index+1), yCoords.get(index+1));
        	headings.add(nextHeading);
        	currentHeading += nextHeading;
        	if (currentHeading > 360.0) {
        		currentHeading -= 360.0;
        	}
        	index++;
        }
        return headings;
    }

    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
    	for (int i = 0; i < 10; i++) {
    		drawRegularPolygon(turtle, 3, 50+i*10);
    		turtle.turn(360.0/10.0);
    	}
    	for (int i = 0; i < 20; i++) {
    		drawRegularPolygon(turtle, 6, 30+i*5);
    		turtle.turn(360.0/20.0);
    	}
    }

    
    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        drawPersonalArt(turtle);
        //drawRegularPolygon(turtle, 3, 50);
        
        // draw the window
        turtle.draw();
    }

}
