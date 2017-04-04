/***************************************************************
 *		file: CS445_P1.java
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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
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
		
		parseFile("coordinates.txt", shapes);
		
		while (!Display.isCloseRequested()) {
			// exit when escape key is pressed
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				break;
			}
			
			// draw all of the shapes
			shapes.forEach((shape) -> {
				shape.draw();
			});
			
			Display.update();
			// a framerate of 60 is low enough to make the program a lot less
			// cpu-intensive but still fast enough to be able to register key
			// presses
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
	
	// method: parseFile
	// purpose: this static methodparse the file with the given relative path and
	// create described Shape instances
	private static void parseFile(String fName, ArrayList<Shape> shapes) {
		String line;
		String[] lineParts, coord0, coord1;
		Scanner scan;
		
		try {
			scan = new Scanner(new File (fName));
			
			while (scan.hasNextLine()) {
				line = scan.nextLine();
				
				if (line != null && line.length() > 0) {
					lineParts = line.split(" ");
					
					try {
						if (lineParts.length == 3) {
							switch (lineParts[0]) {
								case "c":
									coord0 = lineParts[1].split(",");
									shapes.add(new Shape(Float.parseFloat(coord0[0]),
											  Float.parseFloat(coord0[1]),
											  Float.parseFloat(lineParts[2])));
									break;
								case "e":
									coord0 = lineParts[1].split(",");
									coord1 = lineParts[2].split(",");
									shapes.add(new Shape(Float.parseFloat(coord0[0]),
											  Float.parseFloat(coord0[1]),
											  Float.parseFloat(coord1[0]),
											  Float.parseFloat(coord1[1]), false));
									break;
								case "l":
									coord0 = lineParts[1].split(",");
									coord1 = lineParts[2].split(",");
									shapes.add(new Shape(Float.parseFloat(coord0[0]),
											  Float.parseFloat(coord0[1]),
											  Float.parseFloat(coord1[0]),
											  Float.parseFloat(coord1[1]), true));
									break;
								default:
									throw(new Exception());
							}
						} else {
							throw(new Exception());
						}
					} catch (Exception e) {
						System.err.printf("Could not parse line: %s\n", line);
					}
				}
			}
		} catch (FileNotFoundException ex) {
			System.err.printf("File %s does not exist...\n", fName);
		}
	}
}
