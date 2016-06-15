package com.math.orbital;

import java.util.Date;
import static java.lang.Math.*;

import com.math.linear.Vector;
import com.math.orbital.OrbitalConstants.UNITS;


public abstract class OrbitalElements {
	private double a;  // semi-major axis
	private double e;  // eccentricity
	private double i;  // inclination
	private double M;  // mean anomaly
	
	public OrbitalElements(double semimajoraxis, double eccentricity, double inclination, double meanAnomaly){
		this.a = semimajoraxis;
		this.e = eccentricity;
		this.i = inclination;
		this.M = meanAnomaly;
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
	
	public double getMeanAnomaly() {
		return M;
	}

	public void setMeanAnomaly(double m) {
		this.M = m;
	}
	
	public double getMeanMotion(UNITS units) {
		return 2*Math.PI/getPeriod(units);
	}
	


	public abstract double getSemiLatusRectum();
	
	public abstract double getPerigeeDistance();
	
	public abstract double getApogeeDistance();
	
	public abstract double getSpecificMechanicalEnergy(UNITS units);
	
	public abstract double getPeriod(UNITS units);
	
	public abstract double convertTrueAnomalyToConicAnomaly(double nu);
	
	public abstract double convertConicAnomalyToTrueAnomaly(double C);
	
	public abstract double convertMeanAnomalyToConicAnomaly();
	
	public abstract double convertConicAnomalyToMeanAnomaly(double C);
	
	public abstract double getFlightPathAngle(double conicAnomaly);

}
