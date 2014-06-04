package com.scyla.shapes;

import com.scyla.utils.Vector3;

import com.scyla.entity.Entity;

public class Cylinder extends Entity {

	private float baseRadius, topRadius, height;
	private int slices, stacks;

	public Cylinder(int list) {
		super(list);
	}

	public Cylinder(Vector3 position) {
		super(position);
	}

	public Cylinder(int list, Vector3 position) {
		super(list, position);
	}

	public void setProperies(float baseRadius, float topRadius, float height,
			int slices, int stacks) {
		this.baseRadius = baseRadius;
		this.topRadius = topRadius;
		this.height = height;
		this.slices = slices;
		this.stacks = stacks;
	}

}
