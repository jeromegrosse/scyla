package com.scyla.utils;

public class Vector3 {
	public double x, y, z;
	
	public Vector3(){
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Vector3(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double length(){
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	public double lengthSquare(){
		return x*x + y*y + z*z;
	}
	
	public Vector3 add(Vector3 a){
		return new Vector3(a.x + this.x, a.y + this.y, a.z + this.z);
	}
	
	public Vector3 sub(Vector3 a){
		return new Vector3(a.x - this.x, a.y - this.y, a.z - this.z);
	}
	
	public double dot(Vector3 a){
		return this.x*a.x + this.y*a.y + this.z*a.z;
	}
	
	public Vector3 scale(double lambda){
		return new Vector3(lambda*this.x, lambda*this.y, lambda*this.z);
	}
	
	public Vector3 normalise(){
		double length = Math.sqrt(x*x + y*y + z*z);
		return new Vector3 (this.x / length, this.y / length, this.z / length);
	}
	
	public String toString(){
		return "[Vector3: " + x + ", " + y + ", " + z +" ]";
	}

}
