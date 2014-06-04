package com.scyla.entity;

import com.scyla.io.LWJGL;
import com.scyla.utils.Vector3;

public class Player extends Entity {
	
	private boolean flying = true;
	private Vector3 motionSpeed = new Vector3(0,0,0), rotationSpeed = new Vector3(0,0,0);
	private double height = 0, length = 0;

	//Constructors
	public Player(Vector3 position, Vector3 rotation) {
		super(position);
		this.setRotation(rotation);
		this.enableDynamic();
		this.setType(EntityType.PLAYER);
		LWJGL.physicMaster.addEntity(this);
		this.setBounce(1);
		this.setProperties(20, 5);
	}
	
	//Setters
	public void setHeight(double h){
		height = h;
	}
	
	public void setLength(double l){
		length = l;
	}
	
	public void setProperties(double h, double l){
		this.setLength(l);
		this.setHeight(h);
	}
	
	public void setMotionSpeed(Vector3 motionSpeed) {
		this.motionSpeed = motionSpeed;
	}

	public void setRotationSpeed(Vector3 rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}
	//Getters
	public double getHeight(){
		return height;
	}
	
	public double getLength(){
		return length;
	}
	
	public Vector3 getMotionSpeed() {
		return motionSpeed;
	}
	
	public Vector3 getRotationSpeed() {
		return rotationSpeed;
	}
	
	public void translate(Vector3 motion){
		this.setX(this.getX() + motion.x);
		this.setY(this.getY() + motion.y);
		this.setZ(this.getZ() + motion.z);
	}
	
	//Physic methods
	public void flyingOn(){
		flying = true;
	}
	
	public void flyingOff(){
		flying = false;
	}
	
	public boolean flying(){
		return flying;
	}
	
	
	//Graphic methods
	@Override
	public void draw(){
		//We don't want the player to be drawn so... we do nothing...
	}

}
