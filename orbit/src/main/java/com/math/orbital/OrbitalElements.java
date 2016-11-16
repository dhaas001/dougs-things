package com.math.orbital;

import java.util.Date;
import static java.lang.Math.*;

import com.math.linear.Vector;
import com.math.orbital.OrbitalConstants.UNITS;


public abstract class OrbitalElements {
	private double a;  // semi-major axis
	private double e;  // eccentricity
	private double i;  // inclination
	
	private double p;  // parameter
	private boolean parameterSet;
	
	public OrbitalElements(){
		
	}

	public boolean isParameterSet() {
		return parameterSet;
	}

	public void setParameterIsSet(boolean parameterSet) {
		this.parameterSet = parameterSet;
	}

	public double getParameter() {
		return p;
	}

	public void setParameter(double p) {
		this.p = p;
		parameterSet = true;
	}


	public double getSemiMajorAxis() {
		return a;
	}

	public void setSemiMajorAxis(double a) {
		this.a = a;
	}

	public double getEccentricity() {
		return e;
	}

	public void setEccentricity(double e) {
		this.e = e;
	}

	public double getInclination() {
		return i;
	}

	public void setInclination(double i) {
		this.i = i;
	}
	
	public double getMeanMotion(UNITS units) {
		return 2*Math.PI/getPeriod(units);
	}
	


	public abstract double getSemiLatusRectum();
	
	public abstract double getSpecificMechanicalEnergy(UNITS units);
	
	public abstract double getPeriod(UNITS units);
	
	public abstract double getFlightPathAngle(double conicAnomaly);

}
