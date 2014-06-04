package com.scyla.shapes;

import com.scyla.entity.Entity;
import com.scyla.utils.Vector3;

public class Box extends Entity {

	private float width = 0, height = 0, length = 0;

	public Box(int list) {
		super(list);
	}

	public Box(Vector3 position) {
		super(position);
	}

	public Box(int list, Vector3 position) {
		super(list, position);
	}

	public void setProperties(float width, float height, float length) {
		this.width = width;
		this.height = height;
		this.length = length;
	}

}
