package com.scyla.io;

import org.lwjgl.opengl.GL11;

import com.scyla.entity.Player;
import com.scyla.utils.Vector3;

public class Camera {
	Player boundTo = null;
	Vector3 position = null;
	Vector3 rotation = null;
	
	public Camera(Player entity){
		boundTo = entity;
		position = boundTo.getPosition();
		position.y = (float) (boundTo.getPosition().y + boundTo.getHeight());
		rotation = entity.getRotation();
	}
	
	public void update(){
		position = new Vector3(boundTo.getX(), boundTo.getY(), boundTo.getZ());
		position.y += boundTo.getHeight();
		rotation = boundTo.getRotation();
		
		//System.out.println(boundTo.getPosition());
	}
	
	public void setCamera(){
		this.update();
		GL11.glRotatef((float)rotation.x, 1, 0, 0);
		GL11.glRotatef((float)rotation.y, 0, 1, 0);
		GL11.glRotatef((float)rotation.z, 0, 0, 1);
		GL11.glTranslated(position.x, -position.y, position.z);
	}

}
