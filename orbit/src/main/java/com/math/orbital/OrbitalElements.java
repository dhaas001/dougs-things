package com.math.orbital;

import static java.lang.Math.*;
import com.math.orbital.OrbitalConstants.UNITS;


public class OrbitalElements {
	private double a;  // semi-major axis
	private double e;  // eccentricity
	private double i;  // inclination
	private double o;  // longitude of the ascending node
	private double u;  // argument of perigee (relative to line of nodes)
	private double M;  // mean anomaly
	
	public OrbitalElements(double semimajoraxis, double eccentricity, double inclination, double ascendingNode, double argPerigee, double meanAnomaly){
		this.a = semimajoraxis;
		this.e = eccentricity;
		this.i = inclination;
		this.o = ascendingNode;
		this.u =argPerigee;
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

	public double getLongitudeOfAscendingNode() {
		return o;
	}

	public void setLongitudeOfAscendingNode(double o) {
		this.o = o;
	}

	public double getArgumentOfPerigee() {
		return u;
	}

	public void setArgumentOfPerigee(double u) {
		this.u = u;
	}

	public double getMeanAnomaly() {
		return M;
	}

	public void setMeanAnomaly(double m) {
		this.M = m;
	}
	
	public double getSemiLatusRectum() {
		return a*(1-e*e);
	}
	
	public double getPerigeeDistance() {
		return a*(1-e);
	}
	
	public double getApogeeDistance() {
		return a*(1+e);
	}
	
	public double getSpecificMechanicalEnergy(UNITS units) {
		return (-OrbitalConstants.getMU(units))/(2*a);
	}
	
	public double getPeriod(UNITS units) {
		double sqrtMu = Math.pow(OrbitalConstants.getMU(units), 0.5);
		
		return (2*Math.PI/sqrtMu)*(Math.pow(a, 1.5));
	}
	
	public double convertTrueAnomalyToEccentricAnomaly(double nu){
		double sinE = (sin(nu)*sqrt(1.0d-e*e))/(1.0d + e * cos(nu));
		double cosE = (e + Math.cos(nu))/(1.0d + e * Math.cos(nu));
		double asinE = asin(sinE);
		double acosE = acos(cosE);
		// first quadrant
		if (sinE >= 0 && cosE >= 0) return asinE;
		// second quadrant
		if (sinE >= 0 && cosE < 0) return acosE;
		// third quadrant
		if (sinE < 0 && cosE < 0) return Math.PI - asinE;
		// fourth quadrant
		else return 2*Math.PI - asinE;
	}
	
	// solve kepler's equation, converting mean anomaly to eccentric anomaly
	public double convertMeanAnomalyToEccentricAnomaly(){
		double E = 0.0d;
		if ((M < 0 && M > (-PI)) || M > PI){
			E = M - e;
		}
		else{
			E = M + e;
		}
		double Enext;
		while (true) {
			Enext = E + (M-E + e * sin(E))/(1-e*cos(E));
			if (abs(Enext - E)< .00000001){
				break;
			}
			E = Enext;
		}
		return Enext;
	}

}
