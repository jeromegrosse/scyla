package com.scyla.shapes;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Sphere;

import com.scyla.utils.ColorRGBb;
import com.scyla.utils.Vector3;

public class ShapeMaster {

	private ColorRGBb index = new ColorRGBb((byte)0, (byte)0, (byte)1);

	static final int COLORMAX = 128;

	public Box createSquare(float width, float height, float length,
			boolean outside) {

		int squareList = glGenLists(1);
		glNewList(squareList, GL_COMPILE);
		glBegin(GL_QUADS);

		//GL11.glColor3b((byte) indexR, (byte) indexG, (byte) indexB);
		glColor3b(index.r, index.g, index.b);

		// width height length

		// front
		if (outside)
			glNormal3f(0, 0, 1);
		else
			glNormal3f(0, 0, -1);
		glVertex3f(0, 0, 0);
		glVertex3f(0, 0 + height, 0);
		glVertex3f(0 + width, 0 + height, 0);
		glVertex3f(0 + width, 0, 0);

		// back
		if (outside)
			glNormal3f(0, 0, -1);
		else
			glNormal3f(0, 0, 1);
		glVertex3f(0, 0, 0 - length);
		glVertex3f(0 + width, 0, 0 - length);
		glVertex3f(0 + width, 0 + height, 0 - length);
		glVertex3f(0, 0 + height, 0 - length);

		// left
		if (outside)
			glNormal3f(-1, 0, 0);
		else
			glNormal3f(1, 0, 0);
		glVertex3f(0, 0, 0);
		glVertex3f(0, 0, 0 - length);
		glVertex3f(0, 0 + height, 0 - length);
		glVertex3f(0, 0 + height, 0);

		// right
		if (outside)
			glNormal3f(1, 0, 0);
		else
			glNormal3f(-1, 0, 0);
		glVertex3f(0 + width, 0, 0);
		glVertex3f(0 + width, 0 + height, 0);
		glVertex3f(0 + width, 0 + height, 0 - length);
		glVertex3f(0 + width, 0, 0 - length);

		// up
		if (outside)
			glNormal3f(0, 1, 0);
		else
			glNormal3f(0, -1, 0);
		glVertex3f(0, 0 + height, 0 - length);
		glVertex3f(0 + width, 0 + height, 0 - length);
		glVertex3f(0 + width, 0 + height, 0);
		glVertex3f(0, 0 + height, 0);

		// down
		if (outside)
			glNormal3f(0, -1, 0);
		else
			glNormal3f(0, 1, 0);
		glVertex3f(0, 0, 0 - length);
		glVertex3f(0, 0, 0);
		glVertex3f(0 + width, 0, 0);
		glVertex3f(0 + width, 0, 0 - length);

		glEnd();
		glEndList();

		Box box = new Box(squareList);
		box.setIndiceRGB(new ColorRGBb(index.r, index.g, index.b));

		// Color index computation:
		index.b++;
		if (index.b == COLORMAX) {
			index.b = 0;
			index.g++;

			if (index.g == COLORMAX) {
				index.g = 0;
				index.r++;
			}

			//if index.r == COLORMAX, well, we're in the shit.
		}

		return box;
	}

	public com.scyla.shapes.Sphere createSphere(float radius, int slice,
			int stack) {
		int sphereList = glGenLists(1);

		glNewList(sphereList, GL_COMPILE);

		glBegin(GL_QUADS);

		glColor3b(index.r, index.g, index.b);

		(new Sphere()).draw(radius, slice, stack);

		glEndList();

		com.scyla.shapes.Sphere sphere = new com.scyla.shapes.Sphere(sphereList);
		sphere.setProperies(radius, slice, stack);
		sphere.setIndiceRGB(new ColorRGBb(index.r, index.g, index.b));

		// Color index computation:
		index.b++;
		if (index.b == COLORMAX) {
			index.b = 0;
			index.g++;

			if (index.g == COLORMAX) {
				index.g = 0;
				index.r++;
			}

			//if index.r == COLORMAX, well, we're in the shit.
		}

		return sphere;
	}

	public Plan createPlan(float width, float length, boolean up) {
		int planList = glGenLists(1);

		glNewList(planList, GL_COMPILE);

		glBegin(GL_QUADS);

		glColor3b(index.r, index.g, index.b);

		glBegin(GL_QUADS);
		if (up)
			glNormal3f(0, 1, 0);
		else
			glNormal3f(0, -1, 0);
		glVertex3f(0, 0, 0);
		glVertex3f(0, 0, length);
		glVertex3f(width, 0, length);
		glVertex3f(width, 0, 0);
		glEnd();

		glEndList();

		Plan plan = new Plan(planList);

		plan.setProperties(width, length, new Vector3(0, 1, 0));
		plan.disableDynamic();
		plan.setIndiceRGB(new ColorRGBb(index.r, index.g, index.b));

		// Color index computation:
		index.b++;
		if (index.b == COLORMAX) {
			index.b = 0;
			index.g++;

			if (index.g == COLORMAX) {
				index.g = 0;
				index.r++;
			}

			//if index.r == COLORMAX, well, we're in the shit.
		}

		return plan;
	}

	public com.scyla.shapes.Cylinder createCylindre(float baseRadius,
			float topRadius, float height, int slices, int stacks) {
		int cylinderList = glGenLists(1);

		glNewList(cylinderList, GL_COMPILE);

		(new Cylinder()).draw(baseRadius, topRadius, height, slices, stacks);

		glEndList();

		com.scyla.shapes.Cylinder cylinder = new com.scyla.shapes.Cylinder(
				cylinderList);
		cylinder.setProperies(baseRadius, topRadius, height, slices, stacks);

		return cylinder;

	}

}
