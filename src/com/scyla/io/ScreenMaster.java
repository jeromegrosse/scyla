package com.scyla.io;

import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_DITHER;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHT1;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_POLYGON_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

public class ScreenMaster {

	public static void enableFeatures() {
		glEnable(GL_POLYGON_SMOOTH);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glEnable(GL_LIGHT1);
		glEnable(GL_DITHER);
	}

	public static void disableFeature() {
		glDisable(GL_POLYGON_SMOOTH);
		glDisable(GL_COLOR_MATERIAL);
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_LIGHTING);
		glDisable(GL_LIGHT0);
		glDisable(GL_LIGHT1);
		glDisable(GL_DITHER);
	}

}
