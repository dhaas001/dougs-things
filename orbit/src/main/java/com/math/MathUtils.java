package com.math;

public class MathUtils {
	public static final double epsilon = .000001;
	
	public static boolean fpEquals(double d1, double d2){
		return Math.abs(d1-d2) < epsilon;
	}

}
