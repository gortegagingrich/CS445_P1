
/** *************************************************************
 *		file: InputReader.java
 *		author: G. Ortega-Gingrich
 *		class: CS 445 - Computer Graphics
 *
 *		assignment: program 1
 *		date last modified: 4/10/2017
 *
 *		purpose: This is a runnable class that allows for keyboard
 *		input checking to be handled in a separate thread.
 * 
 *		Overview of key bindings:
 *		escape:	quit program
 *		1:			cycle line color
 *		2:			cycle ellipse color
 *		3:			cycle circle color
 *************************************************************** */

import org.lwjgl.input.Keyboard;

public class InputReader implements Runnable {

	private boolean line, circle, ellipse;
	private final boolean[] pressed;

	// constructor: InputReader()
	// purpose: initializes instance variables
	public InputReader() {
		line = false;
		circle = false;
		ellipse = false;
		pressed = new boolean[]{false, false, false};
	}

	// method: run
	// purpose: contains loop that keeps track of function key presses and
	// updates static variables accordingly to change colors or close the window.
	public void run() {
		while (!Main.escapePressed) {
			try {
				Main.escapePressed = Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);

				// line colors changed with 1
				if (Keyboard.isKeyDown(Keyboard.KEY_1) && !line && !pressed[0]) {
					line = true;
					pressed[0] = true;
					Shape.colors[0] += 1;
				} else if (line && !Keyboard.isKeyDown(Keyboard.KEY_1)) {
					line = false;
					pressed[0] = false;
				}

				// circle colors changed with 2
				if (Keyboard.isKeyDown(Keyboard.KEY_2) && !circle && !pressed[1]) {
					circle = true;
					pressed[1] = true;
					Shape.colors[1] += 1;
				} else if (circle && !Keyboard.isKeyDown(Keyboard.KEY_2)) {
					circle = false;
					pressed[1] = false;
				}

				// circle colors changed with 3
				if (Keyboard.isKeyDown(Keyboard.KEY_3) && !ellipse && !pressed[2]) {
					ellipse = true;
					pressed[2] = true;
					Shape.colors[2] += 1;
				} else if (ellipse && !Keyboard.isKeyDown(Keyboard.KEY_3)) {
					ellipse = false;
					pressed[2] = false;
				}
			} catch (IllegalStateException e) {
				// thrown after display is destroyed
				return;
			}
		}
	}
}
