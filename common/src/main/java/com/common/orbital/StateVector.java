package com.common.orbital;

import com.common.linear.Vector;
import com.common.orbital.OrbitalConstants.UNITS;

public class StateVector {
	private Vector r;
	private Vector v;
	
	public StateVector(Vector r,Vector v){
		this.r = r;
		this.v = v;
	}
	
	public Vector getAngularMomentum(){
		return r.cross(v);
	}
	
	public double getSpecificMechanicalEnergy(UNITS units){
		double magv = v.magnitude();
		double magr = r.magnitude();
		return (magv*magv/2) - (OrbitalConstants.getMU(units)/magr);
	}

	public Vector getPosition() {
		return r;
	}

	public void setPosition(Vector r) {
		this.r = r;
	}

	public Vector getVelocity() {
		return v;
	}

	public void setVelocity(Vector v) {
		this.v = v;
	}
	
	public Vector getEccentricityVector(UNITS units) {
		Vector h = getAngularMomentum();
		Vector firstPart = v.cross(h).divide(OrbitalConstants.getMU(units));
		Vector secondPart = r.divide(r.magnitude());
		return firstPart.subtract(secondPart);
	}
	
	public double getEccentricity(UNITS units) {
		double h = getAngularMomentum().magnitude();
		double mu = OrbitalConstants.getMU(units);
		double firstPart = (2*h*h*getSpecificMechanicalEnergy(units))/(mu*mu);
		return Math.pow(1.0d+firstPart, 0.5);
	}
	
	public double getSemiLatusRectum(UNITS units){
		double h = getAngularMomentum().magnitude();
		return h*h/OrbitalConstants.getMU(units);
	}

}
