
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gabriel
 */
public class Shape {
	
	private static enum Type {LINE, CIRCLE, ELLIPSE};
	
	private Type type;
	ArrayList<float[]> points;
	
	// circles
	public Shape(float cX, float cY, float r) {
		points = new ArrayList<>();
		
		for (float i = 0; i < 640; i++) {
			for (float j = 0; j < 480; j++) {
				if (Math.round(distance(i,j,cX,cY)) == r) {
					points.add(new float[] {i,j});
				}
			}
		}
		
		type = Type.CIRCLE;
	}
	
	// lines and ellipses
	public Shape(float x1, float y1, float x2, float y2, boolean line) {
		points = new ArrayList<>();
		
		if (line) { // makes it a line
			float dX, dY;
			float distance = distance(x1, y1, x2, y2);
			
			dX = (x2 - x1) / distance;
			dY = (y2 - y1) / distance;
			
			float distanceMoved = 0;
			
			while (distanceMoved < distance) {
				points.add(new float[] {x1, y1});
				x1 += dX;
				y1 += dY;
				distanceMoved += Math.sqrt(dX * dX + dY * dY);
			}
			
			System.out.printf("Distance: %f\ndX: %f\ndY: %f\n", distance, dX, dY);
			
			type = Type.LINE;
		} else { // makes it an ellipse
			
			
			type = Type.ELLIPSE;
		}
	}
	
	public void draw() {
		GL11.glBegin(GL11.GL_POINTS);
		
		switch (type) {
			case LINE:
				GL11.glColor3f(1f, 0f, 0f);
				break;
			case ELLIPSE:
				GL11.glColor3f(0f, 1f, 0f);
				break;
			case CIRCLE:
				GL11.glColor3f(0f, 0f, 1f);
		}
		
		for (float[] point: points) {
			if (point != null && point.length == 2) {
				GL11.glVertex2f(point[0], point[1]);
			}
		}
		
		GL11.glEnd();
	}
	
	private float distance(float x1, float y1, float x2, float y2) {
		return (float)Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2,2));
	}
}
