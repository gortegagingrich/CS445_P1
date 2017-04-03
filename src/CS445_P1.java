/***************************************************************
 *		file: CS445_P1.java
 *		author: G. Ortega-Gingrich
 *		class: CS 445 - Computer Graphics
 * 
 *		assignment: program 1
 *		date last modified: 4/3/2017
 * 
 *		purpose: This program takes a filename from the command line,
 *		parses its contents, and draws the lines, circles, and ellipses
 *		described.
 ****************************************************************/

import java.util.ArrayList;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class CS445_P1 {

	// method: main
	// purpose: this static method is called when the program is run.
	// It creates the window, initializses opengl, and contains the main loop
	public static void main(String[] args) throws LWJGLException {
		// TODO code application logic here
		Display.setDisplayMode(new DisplayMode(640,480));
		Display.setTitle("Project 1");
		Display.create();
		center();
		
		GL11.glPointSize(1);
		GL11.glClearColor(0f,0f,0f,1f);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 640, 0, 480, 1, -1);
		
		ArrayList<Shape> shapes = new ArrayList<>();
		
		shapes.add(new Shape(200,50,100, 200, true));
		shapes.add(new Shape(50,50,300, 300, true));
		shapes.add(new Shape(500,300,70));
		shapes.add(new Shape(225,370,35, 75, false));
		
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			
			Display.setTitle(String.format("Mouse pos: %d, %d", Mouse.getX(), Mouse.getY()));
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			shapes.forEach((shape) -> {
				shape.draw();
			});
			
			Display.update();
			Display.sync(60);
		}
		
		Display.destroy();
	}
	
	// method: center
	// purpose: this static method centers the window on the screen
	private static void center() {
		int scrWidth = Display.getDesktopDisplayMode().getWidth();
		int scrHeight = Display.getDesktopDisplayMode().getHeight();
		int width = Display.getWidth();
		int height = Display.getHeight();
		
		Display.setLocation(scrWidth/2 - width/2, scrHeight/2 - height/2);
	}
}
