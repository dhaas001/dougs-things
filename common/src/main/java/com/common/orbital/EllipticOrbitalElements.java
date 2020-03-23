package com.common.orbital;

import static java.lang.Math.*;

import com.common.MathUtils;
import com.common.orbital.OrbitalConstants.UNITS;


public class EllipticOrbitalElements extends OrbitalElements{
	private double o;  // longitude of the ascending node
	private double u;  // argument of perigee (relative to line of nodes)
	private double M;  // mean anomaly
	private double nu; // true anomaly
	private double trueLongitudeOfPeriapsis;
	private boolean trueLongitudeOfPeriapsisSet;
	private double longitudeOfPeriapsis;
	private boolean longitudeOfPeriapsisSet;
	
	public EllipticOrbitalElements(){
		
	}
	
	public double getMeanAnomaly() {
		return M;
	}

	public void setMeanAnomaly(double m) {
		this.M = m;
	}
	
	public double getTrueAnomaly() {
		return nu;
	}

	public void setTrueAnomaly(double nu) {
		this.nu = nu;
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
	
	public double getPerigeeDistance() {
		double a = getSemiMajorAxis();
		double e = getEccentricity();
		return a*(1-e);
	}
	
	public double getApogeeDistance() {
		double a = getSemiMajorAxis();
		double e = getEccentricity();
		return a*(1+e);
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
	
	public double convertTrueAnomalyToConicAnomaly(double nu){
		double a = getSemiMajorAxis();
		double e = getEccentricity();
		double sinNu = sin(nu);
		double cosNu = cos(nu);
		double sinE = (sinNu*sqrt(1.0d-e*e))/(1.0d + e * cosNu);
		double cosE = (e + cosNu)/(1.0d + e * cosNu);
		return MathUtils.findAngle(sinE, cosE);
	}
	
	// solve kepler's equation, converting mean anomaly to eccentric anomaly
	// Algorithm 2
	public double convertMeanAnomalyToConicAnomaly(){
		double E = 0.0d;
		double e = getEccentricity();
		double M = getMeanAnomaly();
		if ((M < 0 && M > (-PI)) || M > PI){
			E = M - e;
		}
		else{
			E = M + e;
		}
		double Enext;
		while (true) {
			Enext = E + (M - E + e * sin(E))/(1-e*cos(E));
			if (abs(Enext - E)< .00000001){
				break;
			}
			E = Enext;
		}
		return Enext;
	}

	// algorithm 5
	public double convertConicAnomalyToTrueAnomaly(double E) {
		double a = getSemiMajorAxis();
		double e = getEccentricity();
		double sinE = sin(E);
		double cosE = cos(E);
		double sinNu = (sinE*sqrt(1.0d-e*e))/(1.0d - e * cosE);
		double cosNu = (cosE - e)/(1.0d - e * cosE);
		return MathUtils.findAngle(sinNu, cosNu);
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

	public double convertConicAnomalyToMeanAnomaly(double E) {
		double e = getEccentricity();
		return E - (e * sin(E));
	}

}
