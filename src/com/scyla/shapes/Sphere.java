package com.scyla.shapes;

import com.scyla.utils.Vector3;

import com.scyla.entity.Entity;

public class Sphere extends Entity {
	private float radius;
	private int slice, stack;

	public Sphere(int list) {
		super(list);
	}

	public Sphere(Vector3 position) {
		super(position);
	}

	public Sphere(int list, Vector3 position) {
		super(list, position);
	}

	public void setProperies(float radius, int slice, int stack) {
		this.radius = radius;
		this.slice = slice;
		this.stack = stack;
	}

	public float getRadius() {
		return radius;
	}

}
