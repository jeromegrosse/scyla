package com.scyla.entity;

import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslated;

import com.scyla.physics.Force;
import com.scyla.utils.ColorRGBb;
import com.scyla.utils.Vector3;

public abstract class Entity {

	
	int list = 0;	
	
	//Entity graphic attributes.
	private ColorRGBb color = new ColorRGBb((byte)0, (byte)0, (byte)0); //Unique RGB-index - used during the selection.
	
	//Entity physic attributes.
	private double mass = 2, invMass = 1 / mass;
	
	private double bounce = 1.7d, dumping = 0.5d;
	private EntityType type = EntityType.DYNAMIC_PICKABLE;
	private EntityState state = EntityState.MOVING;
	private boolean isGrabbed = false;
	private Vector3 position = new Vector3(0, 0, 0), rotation = new Vector3(0, 0, 0);
	private Vector3 speed = new Vector3(0, 0, 0);
	private Force gravity = new Force(new Vector3(0,0,0));

	
	//Constructors
	public Entity(int list) {
		this.list = list;
	}

	public Entity(Vector3 position) {
		this.position = position;
	}

	public Entity(int list, Vector3 position) {
		this.list = list;
		this.position = position;
	}

	
	//Setters
	public void setPosition(Vector3 position) {
		this.position = position;
	}
	
	public void setMass(double mass) {
		this.mass = mass;
		this.invMass = 1 / mass;
	}
	
	public void setList(int list) {
		this.list = list;
	}
	
	public void setX(double x) {
		this.position.x = x;
	}

	public void setY(double y) {
		this.position.y = y;
	}

	public void setZ(double z) {
		this.position.z = z;
	}
	
	public void setDX(double dx) {
		speed.x = dx;
	}

	public void setDY(double dy) {
		speed.y = dy;
	}

	public void setDZ(double dz) {
		speed.z = dz;
	}
	
	public void setRX(double x) {
		rotation.x = x;
	}

	public void setRY(double y) {
		rotation.y = y;
	}

	public void setRZ(double z) {
		rotation.z = z;
	}
	
	public void setSpeed(Vector3 speed) {
		this.speed = speed;
	}
	public void setRotation(Vector3 rotation) {
		this.rotation = rotation;
	}
	
	public void setBounce(double bounce) {
		this.bounce = bounce;
	}
	
	public void setIndiceRGB(ColorRGBb color) {
		this.color = color;
	}	

	public void setDumping(double dumping) {
		this.dumping = dumping;
	}

	public void setState(EntityState state) {
		this.state = state;
	}
	
	public void setGravity(Vector3 gravity) {
		this.gravity = new Force(gravity);
	}
	
	public void setType(EntityType type){
		this.type = type;
	}	
	
	
	//Getters
	public Vector3 getPosition() {
		return this.position;
	}

	public double getMass() {
		return mass;
	}

	public double getInvMass() {
		return invMass;
	}

	public int getList() {
		return this.list;
	}

	public double getX() {
		return position.x;
	}

	public double getY() {
		return position.y;
	}

	public double getZ() {
		return position.z;
	}

	public double getDX() {
		return speed.x;
	}

	public double getDY() {
		return speed.y;
	}

	public double getDZ() {
		return speed.z;
	}
	
	public double getRX() {
		return rotation.x;
	}

	public double getRY() {
		return rotation.y;
	}

	public double getRZ() {
		return rotation.z;
	}
	
	public Vector3 getSpeed() {
		return speed;
	}

	public Vector3 getRotation() {
		return rotation;
	}
	
	public ColorRGBb getIndiceRGB() {
		return this.color;
	}

	public double getBounce() {
		return bounce;
	}
	
	public double getDumping() {
		return dumping;
	}
	
	public EntityState getState() {
		return state;
	}
	
	public EntityType getEntityType(){
		return this.type;
	}

	

	//Physics method
	public void enableDynamic() {
		type = EntityType.DYNAMIC_PICKABLE;
	}

	public void disableDynamic() {
		type = EntityType.STATIC;
		speed = new Vector3(0, 0, 0);
	}

	public void applyForces() {
		if (type != EntityType.STATIC) {
			
			if (!this.isGrabbed) {					
				if (gravity != null) {
					this.setDX(gravity.getForce().x + this.getDX());
					this.setDY(gravity.getForce().y + this.getDY());
					this.setDZ(gravity.getForce().z + this.getDZ());
				}

				this.setX(getDX() + this.getX());
				this.setY(getDY() + this.getY());
				this.setZ(getDZ() + this.getZ());
			}
		}
	}

	

	public void stayStill() {
		speed = new Vector3(0, 0, 0);
	}

	public boolean moving() {
		if (speed.length() < 0.05f)
			return true;

		return false;
	}
	
	public boolean isGrabbed(){
		return isGrabbed;
	}
	
	public void impulse(Vector3 impulsionVector) {
		speed.x += impulsionVector.x;
		speed.y += impulsionVector.y;
		speed.z += impulsionVector.z;
	}

	public void relativeTeleport(Vector3 teleport) {
		position.x += teleport.x;
		position.y += teleport.y;
		position.z += teleport.z;
	}
	
	public void translateX(double x){
		this.setX(this.getX() + x);
	}
	
	public void translateY(double y){
		this.setY(this.getY() + y);
	}
	
	public void translateZ(double z){
		this.setZ(this.getZ() + z);
	}
	
	
	public void rotateX(double x){
		this.setRX(this.getRX() + x);
	}
	
	public void rotateY(double y){
		this.setRY(this.getRY() + y);
	}
	
	public void rotateZ(double z){
		this.setRZ(this.getRZ() + z);
	}
	
	public void relativeRotation(Vector3 rotation){
		this.setRX(this.getRX() + rotation.x);
		this.setRY(this.getRY() + rotation.y);
		this.setRZ(this.getRZ() + rotation.z);
	}
	
	public void grab() {
		isGrabbed = true;
		this.setSpeed(new Vector3(0, 0, 0));
		this.gravity = null;
		System.out.println(this + " has been grabbed.");
	}

	public void release() {
		isGrabbed = false;
		System.out.println(this + " has been released.");
	}
	
	
	
	
	//Graphic methods	
	public void draw() {
		glPushMatrix();
		glTranslated(position.x, position.y, position.z);
		glCallList(this.list);
		glPopMatrix();
	}

	

	

	

}
