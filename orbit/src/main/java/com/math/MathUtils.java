package com.math;

import static java.lang.Math.PI;
import static java.lang.Math.acos;
import static java.lang.Math.asin;

public class MathUtils {
	public static final double epsilon = .000001;
	
	public static boolean fpEquals(double d1, double d2){
		return Math.abs(d1-d2) < epsilon;
	}
	
	public static double findAngle(double sinA, double cosA){
		double asinA = asin(sinA);
		double acosA = acos(cosA);
		// first quadrant
		if (sinA >= 0 && cosA >= 0) return asinA;
		// second quadrant
		if (sinA >= 0 && cosA < 0) return acosA;
		// third quadrant
		if (sinA < 0 && cosA < 0) return PI - asinA;
		// fourth quadrant
		else return 2*PI + asinA;
	}
	
	public static boolean fpGreaterThan(double d1, double d2){
		return d1 > d2 + epsilon;
	}
	
	public static boolean fpLessThan(double d1, double d2){
		return d1 < d2 + epsilon;
	}
	
	public static boolean fpGreaterThanLessThan(double d1, double d2, double d3){
		return fpGreaterThan(d1, d2) && fpLessThan(d1, d3);
	}

}
