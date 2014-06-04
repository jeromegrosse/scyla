package com.scyla.io;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.scyla.entity.Player;
import com.scyla.physics.PhysicMaster;
import com.scyla.utils.Vector3;

public class LWJGL{

	static Vector3 light0pos = new Vector3(269, 262, 50);
	static FloatBuffer light0position;

	static Vector3 light1pos = new Vector3(264, 181, 50);
	static FloatBuffer light1position;

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	static double speed = 2;
	static double mouseSensibility = 0.5;

	public static PhysicMaster physicMaster = new PhysicMaster();
	static MouseMaster mouseMaster = null;
	static KeyboardMaster keyboardMaster = null;
	static Player player = null;
	static Camera camPersp = null;

	public LWJGL() {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("BLABLABLA");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		initOpenGl();

		player = new Player(new Vector3(0,0,0), new Vector3(0,0,0));
		camPersp = new Camera(player);
		mouseMaster = new MouseMaster(this, physicMaster);
		keyboardMaster = new KeyboardMaster(speed, this, physicMaster);
		

		// Lights stuffs
		glEnable(GL_POLYGON_SMOOTH);
		glShadeModel(GL_SMOOTH);

		// glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
		glEnable(GL_COLOR_MATERIAL);

		FloatBuffer lightAmbient = BufferUtils.createFloatBuffer(4);
		lightAmbient.put(new float[] { 0.5f, 0.5f, 0.5f, 1f });
		lightAmbient.flip();

		FloatBuffer light0diffuse = BufferUtils.createFloatBuffer(4);
		light0diffuse.put(new float[] { 0.3f, 0.3f, 0.6f, 1 });
		light0diffuse.flip();

		light0position = BufferUtils.createFloatBuffer(4);
		light0position.put(new float[] { (float)light0pos.x, (float)light0pos.y, (float)light0pos.z,
				1 });
		light0position.flip();

		FloatBuffer light1diffuse = BufferUtils.createFloatBuffer(4);
		light1diffuse.put(new float[] { 0.6f, 0.3f, 0.3f, 1 });
		light1diffuse.flip();

		light1position = BufferUtils.createFloatBuffer(4);
		light1position.put(new float[] { (float)light1pos.x, (float)light1pos.y,(float) light1pos.z,
				1 });
		light1position.flip();

		glLight(GL_LIGHT1, GL_DIFFUSE, light1diffuse);
		glLight(GL_LIGHT1, GL_POSITION, light1position);

		glLight(GL_LIGHT0, GL_DIFFUSE, light0diffuse);
		glLight(GL_LIGHT0, GL_POSITION, light0position);
		glLightModel(GL_LIGHT_MODEL_AMBIENT, lightAmbient);

		Display.setVSyncEnabled(true);

		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			updateOpenGL();

			Display.update();
			Display.sync(60);
		}

		// GL11.glLoadIdentity();
		Display.destroy();
		System.exit(0);
	}

	public static void initOpenGl() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(70, (float) WIDTH / (float) HEIGHT, 0.1f, -1);
		;
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		// glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);

		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glEnable(GL_LIGHT1);
	}

	public static void updateOpenGL() {
		GL11.glLoadIdentity();

		keyboardMaster.computeKeyboard();
		mouseMaster.computeMouse();

		physicMaster.applyPhysic();

		renderScene();
		drawHUD();

	}

	public static void renderScene() {
		glPushMatrix();
		camPersp.setCamera();
		glLight(GL_LIGHT0, GL_POSITION, light0position);
		glLight(GL_LIGHT1, GL_POSITION, light1position);

		physicMaster.drawEntities();

		glPopMatrix();
	}
	
	public static Player getPlayer(){
		return player;
	}

	public static void drawHUD() {
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();
		glOrtho(0.0, WIDTH, HEIGHT, 0.0, -1.0, 10.0);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		ScreenMaster.disableFeature();

		glClear(GL_DEPTH_BUFFER_BIT);

		glBegin(GL_LINES);
		glColor3f(0.7f, 0.95f, 0.97f);
		glVertex2f(WIDTH / 2 - 10f, HEIGHT / 2f);
		glVertex2f(WIDTH / 2 + 10f, HEIGHT / 2f);
		glVertex2f(WIDTH / 2, HEIGHT / 2 - 10f);
		glVertex2f(WIDTH / 2f, HEIGHT / 2 + 10f);
		glEnd();

		// Making sure we can render 3d again
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);

		ScreenMaster.enableFeatures();
	}
}