/***************************************************************
 *		file: Shape.java
 *		author: G. Ortega-Gingrich
 *		class: CS 445 - Computer Graphics
 * 
 *		assignment: program 1
 *		date last modified: 4/3/2017
 * 
 *		purpose: This program opens a file called "coordinates.txt",
 *		parses its contents, and draws the lines, circles, and ellipses
 *		described.
 ****************************************************************/

import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

public class Shape {
	
	private static enum Type {LINE, CIRCLE, ELLIPSE};
	
	private final Type type;
	ArrayList<float[]> points;
	
	// constructor: 3 floats
	// purpose: creates a Shape instance of type circle with the given center x,
	// center y, and radius
	public Shape(float cX, float cY, float r) {
		points = new ArrayList<>();
		
		makeEllipse(cX, cY, r, r);
		
		type = Type.CIRCLE;
	}
	
	// constructor: 4 floats
	// purpose: creates a Shape instance of type ellipse, treating the first two
	// floats as the center and the next two as the width and height, or of type
	// line, treating the first two floats as point A and the next as point B
	public Shape(float float0, float float1, float float2, float float3, boolean line) {
		points = new ArrayList<>();
		
		if (line) { // makes it a line
			makeLine(float0, float1, float2, float3);
			
			type = Type.LINE;
		} else { // makes it an ellipse
			makeEllipse(float0, float1, float2, float3);
			
			type = Type.ELLIPSE;
		}
	}
	
	// method: draw
	// purpose: This method sets the color to the type's corresponding color,
	// and draws each predetermined pixel
	public void draw() {
		GL11.glBegin(GL11.GL_POINTS);
		
		switch (type) {
			case LINE:
				GL11.glColor3f(1, 0, 0);
				break;
			case ELLIPSE:
				GL11.glColor3f(0, 1, 0);
				break;
			case CIRCLE:
				GL11.glColor3f(0, 0, 1);
		}
		
		for (float[] point: points) {
			if (point != null && point.length == 2) {
				GL11.glVertex2f(point[0], point[1]);
			}
		}
		
		GL11.glEnd();
	}
	
	// method: makeEllipse
	// purpose: this method adds every required point to create an ellipse with
	// the given center, width, and height to the ArrayList points
	private void makeEllipse(float cX, float cY, float width, float height) {
		double circ, dir, dDir;
		
		dir = 0;
		circ = 2 * Math.PI * Math.sqrt((width * width + height * height) / 2);
		dDir = (2 * Math.PI) / circ / 2;
		
		while (dir <= (2 * Math.PI)) {
			points.add(new float[] {cX + (float)(width * Math.cos(dir)),
				cY + (float)(height * Math.sin(dir))});
			dir += dDir;
		}
	}
	
	// method: makeLine
	// purpose: this method adds every required point to create a line with the
	// given endpoints to the ArrayList points
	private void makeLine(float x1, float y1, float x2, float y2) {
		float dX, dY;
		float distance = distance(x1, y1, x2, y2);

		dX = (x2 - x1) / distance;
		dY = (y2 - y1) / distance;

		float distanceMoved = 0;

		while (distanceMoved <= distance) {
			points.add(new float[] {x1, y1});
			x1 += dX;
			y1 += dY;
			distanceMoved += Math.sqrt(dX * dX + dY * dY);
		}
	}
	
	// method: distance
	// purpose: this method calculates the distance between two points
	private float distance(float x1, float y1, float x2, float y2) {
		return (float)Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2,2));
	}
}
