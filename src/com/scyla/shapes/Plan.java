package com.scyla.shapes;

import com.scyla.entity.Entity;
import com.scyla.utils.Vector3;

public class Plan extends Entity {

	private double width, length;
	private Vector3 normal;

	public Plan(int list) {
		super(list);
	}

	public Plan(Vector3 position) {
		super(position);
	}

	public Plan(int list, Vector3 position) {
		super(list, position);
	}

	public void setProperties(double w, double l, Vector3 n) {
		this.width = w;
		this.length = l;
		this.normal = n;
	}

	public Vector3 getNormal() {
		return normal;
	}

}
