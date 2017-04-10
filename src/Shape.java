
/** *************************************************************
 *		file: Shape.java
 *		author: G. Ortega-Gingrich
 *		class: CS 445 - Computer Graphics
 *
 *		assignment: program 1
 *		date last modified: 4/10/2017
 *
 *		purpose: This class describes an object that contains the
 *		information needed to draw a given shape pixel by pixel.
 *		The individual pixels are determined on creation using
 *		required algorithms and stored in a list to avoid performing
 *		the same calculations every time the window refreshes.
 *		When it comes time to draw the shape, it iterates through
 *		the list of points and draws each with glVector2f to.
 *************************************************************** */

import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

public class Shape {

	public static int[] colors = {0, 1, 2};

	private static enum Type {
		LINE, CIRCLE, ELLIPSE
	};

	private final Type type;
	ArrayList<int[]> points;

	// constructor: Shape(int, int, int)
	// purpose: creates a Shape instance of type circle with the given center x,
	// center y, and radius
	public Shape(int cX, int cY, int r) {
		points = new ArrayList<>();

		makeEllipse(cX, cY, r, r);

		type = Type.CIRCLE;
	}

	// constructor: Shape(int, int, int, int, boolean)
	// purpose: creates a Shape instance of type ellipse, treating the first two
	// floats as the center and the next two as the width and height, or of type
	// line, treating the first two floats as point A and the next as point B
	public Shape(int float0, int float1, int float2, int float3, boolean line) {
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
				GL11.glColor3f((colors[0] % 3 == 0) ? 1 : 0, (colors[0] % 3 == 1)
						  ? 1 : 0, (colors[0] % 3 == 2) ? 1 : 0);
				break;
			case ELLIPSE:
				GL11.glColor3f((colors[1] % 3 == 0) ? 1 : 0, (colors[1] % 3 == 1)
						  ? 1 : 0, (colors[1] % 3 == 2) ? 1 : 0);
				break;
			case CIRCLE:
				GL11.glColor3f((colors[2] % 3 == 0) ? 1 : 0, (colors[2] % 3 == 1)
						  ? 1 : 0, (colors[2] % 3 == 2) ? 1 : 0);
		}

		for (int[] point : points) {
			GL11.glVertex2f(point[0], point[1]);
		}

		GL11.glEnd();
	}

	// method: makeEllipse
	// purpose: this method adds every required point to create an ellipse with
	// the given center, width, and height to the ArrayList points
	private void makeEllipse(int cx, int cy, int rx, int ry) {
		double theta, dTheta, perimeter;
		int x, y;

		perimeter = 2 * Math.PI * Math.sqrt((rx * rx + ry * ry) / 2.0);
		// I have dTheta at half of the logical value to avoid skipping pixels
		dTheta = Math.PI / perimeter / 2;
		theta = 0;

		// adds corresponding points in all four quadrants at once, so there is
		// no need to go all the way to 2pi		
		while (theta <= Math.PI / 2) {
			x = (int) Math.round(rx * Math.cos(theta));
			y = (int) Math.round(ry * Math.sin(theta));
			ellipseAddPoints(cx, cy, x, y);
			theta += dTheta;
		}
	}

	// method: ellipseAddPoints
	// purpose: this method adds four points centered around cx,cy where
	// abs(xOffset) = x and abs(yOffset) = y.  This reduces the number of trig
	// function calls by 75%.
	private void ellipseAddPoints(int cx, int cy, int x, int y) {
		points.add(new int[]{cx + x, cy + y});
		points.add(new int[]{cx - x, cy + y});
		points.add(new int[]{cx + x, cy - y});
		points.add(new int[]{cx - x, cy - y});
	}

	// method: makeLine
	// purpose: this method adds every required point to create a line with the
	// given endpoints to the ArrayList points
	// The points are determined using a midpoint line algorithm
	private void makeLine(int x0, int y0, int x1, int y1) {
		int x, y, dx, dy, d;

		if (x0 > x1) {
			x = x1;
			y = y1;
			x1 = x0;
		} else {
			x = x0;
			y = y0;
		}

		dx = x1 - x0;
		dy = y1 - y0;

		if (x0 == x1 && y0 > y1) {
			int temp = y0;
			y0 = y1;
			y1 = temp;
			y = y0;
			dy *= -1;
		}

		points.add(new int[]{x, y});
		if (x0 == x1 || 1.0 * dy / dx > 1) {
			d = 2 * dx - dy;

			while (y < y1) {
				y++;

				if (d < 0) {
					d += 2 * dx;
				} else {
					x++;
					d += 2 * (dx - dy);
				}

				points.add(new int[]{x, y});
			}
		} else if (1.0 * dy / dx >= 0) {
			d = 2 * dy - dx;

			while (x < x1) {
				x++;

				if (d < 0) {
					d += 2 * dy;
				} else {
					y++;
					d += 2 * (dy - dx);
				}

				points.add(new int[]{x, y});
			}
		} else if (1.0 * dy / dx >= -1) {
			dy = Math.abs(dy);

			d = 2 * dy - dx;

			while (x < x1) {
				x++;

				if (d < 0) {
					d += 2 * dy;
				} else {
					y--;
					d += 2 * (dy - dx);
				}

				points.add(new int[]{x, y});
			}
		} else {
			dy = Math.abs(dy);

			d = 2 * dx - dy;

			while (y > y1) {
				y--;

				if (d < 0) {
					d += 2 * dx;
				} else {
					x++;
					d += 2 * (dy - dx);
				}

				points.add(new int[]{x, y});
			}
		}
	}
}
