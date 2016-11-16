package com.math.orbital;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import com.math.MathUtils;
import com.math.orbital.OrbitalConstants.UNITS;

public class CircularOrbitalElements extends OrbitalElements {
	private double o;  // longitude of the ascending node
	private double u;  // argument of perigee (relative to line of nodes)
	private double trueLongitudeOfPeriapsis;
	private boolean trueLongitudeOfPeriapsisSet;
	private double longitudeOfPeriapsis;
	private boolean longitudeOfPeriapsisSet;
	
	public CircularOrbitalElements(){
		
	}
	
	public double getLongitudeOfPeriapsis() {
		return longitudeOfPeriapsis;
	}

	public void setLongitudeOfPeriapsis(double longitudeOfPeriapsis) {
		this.longitudeOfPeriapsis = longitudeOfPeriapsis;
		longitudeOfPeriapsisSet = true;
	}

	public boolean isLongitudeOfPeriapsisSet() {
		return longitudeOfPeriapsisSet;
	}

	public double getTrueLongitudeOfPeriapsis() {
		return trueLongitudeOfPeriapsis;
	}

	public void setTrueLongitudeOfPeriapsis(double trueLongitudeOfPeriapsis) {
		this.trueLongitudeOfPeriapsis = trueLongitudeOfPeriapsis;
		trueLongitudeOfPeriapsisSet = true;
	}

	public boolean isTrueLongitudeOfPeriapsisSet() {
		return trueLongitudeOfPeriapsisSet;
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

	@Override
	public double getSemiLatusRectum() {
		double a = getSemiMajorAxis();
		double e = getEccentricity();
		return a*(1-e*e);
	}
	
	@Override
	public double getSpecificMechanicalEnergy(UNITS units) {
		double a = getSemiMajorAxis();
		return (-OrbitalConstants.getMU(units))/(2*a);
	}
	
	@Override
	public double getPeriod(UNITS units) {
		double a = getSemiMajorAxis();
		double sqrtMu = sqrt(OrbitalConstants.getMU(units));
		
		return (2*PI/sqrtMu)*(pow(a, 1.5));
	}
	
	@Override
	public double getFlightPathAngle(double E) {
		double e = getEccentricity();
		double sinE = sin(E);
		double cosE = cos(E);
		double sinFPA = (e * sinE)/sqrt(1.0d - e*e*cosE*cosE);
		double cosFPA = sqrt((1.0d - e*e)/(1.0d - e*e*cosE*cosE));
		return MathUtils.findAngle(sinFPA, cosFPA);
	}

}
