import java.util.ArrayList;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Gabriel
 */
public class CS445_P1 {
	
	public static final float[][] COLORS = {
		{1f, 0f, 0f}, // line
		{0f, 1f, 0f}, // circle
		{0f, 0f, 1f}  // ellipse
	};

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws LWJGLException {
		// TODO code application logic here
		Display.setDisplayMode(new DisplayMode(640,480));
		Display.setTitle("Project 1");
		Display.create();
		
		GL11.glPointSize(1);
		GL11.glClearColor(0f,0f,0f,1f);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 640, 0, 480, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		ArrayList<Shape> shapes = new ArrayList<>();
		
		shapes.add(new Shape(200,50,100, 200, true));
		shapes.add(new Shape(50,50,300, 300, true));
		shapes.add(new Shape(500,300,70));
		
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
}
