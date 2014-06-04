package com.scyla.physics;

public class Maths {
	public static double[] resolvQuad(double a, double b, double c){
		double delta = b*b - 4*a*c;

		if (delta == 0 && a != 0){
			double root = -b / (2*a);
			return new double[]{root};
		}
		else if (delta > 0 && a != 0){
			double sqrd = Math.sqrt(delta);
			double r1 = (-b + sqrd) / (2*a);
			double r2 = (-b - sqrd) / (2*a);
			
			return new double[]{r1, r2};
		}
		
		return null;
	}

}
