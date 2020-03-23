package com.common.orbital;

import static java.lang.Math.*;

import com.common.MathUtils;
import com.common.linear.Vector;
import com.common.orbital.OrbitalConstants.UNITS;

public class OrbitalCalculations {
	// algorithm 8 from Fundamentals of Astrodynamics by Vallado
	public static StateVector solveKepler(StateVector initial, double deltaT, UNITS units) {
		Vector pos = initial.getPosition();
		Vector vel = initial.getVelocity();
		double v0mag = vel.magnitude();
		double r0mag = pos.magnitude();
		double mu = OrbitalConstants.getMU(units);
		double xi = (v0mag*v0mag/2) - (mu/r0mag);
		double alpha = (2/r0mag) - (v0mag*v0mag)/mu;
		double sqrtMu = sqrt(mu);
		double chiN = 0.0d;
		if (abs(alpha) > .000001){
			chiN = sqrtMu*deltaT*alpha;
		}
		while (true){
			double psi = chiN*chiN*alpha;
			double c2 = findC2(psi);
			double c3 = findC3(psi);
			
			double r = (chiN*chiN*c2) + (pos.dot(vel)*chiN*(1-psi*c3))/sqrtMu + r0mag*(1-psi*c2);
			
			double chiNext = chiN + (sqrtMu*deltaT - (chiN*chiN*chiN*c3) - (pos.dot(vel)*chiN*chiN*c2)/sqrtMu - r0mag*chiN*(1-psi*c3))/r ;
			if (abs(chiNext - chiN) >= 10E-6){
				chiN = chiNext;
				continue;
			}
			chiN = chiNext;
			double f = (1.0d - chiN*chiN*c2/r0mag);
			double g = (deltaT - chiN*chiN*chiN*c3/sqrtMu);
			double gdot = (1.0d - chiN*chiN*c2/r);
			double fdot = (sqrtMu*chiN*(psi*c3-1)/(r*r0mag));
			Vector initialPosition = initial.getPosition();
			Vector initialVelocity = initial.getVelocity();
			Vector returnR = initialPosition.multiply(f).add(initialVelocity.multiply(g));
			Vector returnV = initialPosition.multiply(fdot).add(initialVelocity.multiply(gdot));
			StateVector returnValue = new StateVector(returnR, returnV);
			return returnValue;
		}
	}
	
	private static double findC2(double psi){
		if (psi > 1E-6){
			return (1-cos(sqrt(psi)))/psi;
		}
		else if (psi < -1E-6){
			return (1-cosh(sqrt(-psi))/psi);
		}
		else{
			return 0.5d;
		}
	}
	
	private static double findC3(double psi){
		if (psi > 1E-6){
			return (sqrt(psi)-sin(sqrt(psi)))/sqrt(psi*psi*psi);
		}
		else if (psi < -1E-6){
			return (sinh(sqrt(-psi) - sqrt(psi))/sqrt(psi*psi*psi));
		}
		else{
			return 1.0d/6.0d;
		}
	}
	
	// algorithm 9 from Fundamentals of Astrodynamics by Vallado
	private static OrbitalElements rv2coe(StateVector rv, UNITS units){
		Vector r = rv.getPosition();
		double rmag = r.magnitude();
		Vector v = rv.getVelocity();
		double vmag = v.magnitude();
		Vector h = r.cross(v);
		double hmag = h.magnitude();
		Vector K = new Vector(3,new double[]{0.0d,0.0d,1.0d});
		Vector n = K.cross(h);
		double nmag = n.magnitude();
		double mu = OrbitalConstants.getMU(units);
		double rdotv = r.dot(v);
		
		Vector temp1 = r.multiply((vmag*vmag) - (mu/rmag));
		Vector temp2 = v.multiply(rdotv);
		Vector e = temp1.subtract(temp2).divide(mu);
		double emag = e.magnitude();
		double a; // semimajor axis
		double p; // semiparameter
		double energy = (vmag*vmag/2) - (mu/rmag); 
		
		if (MathUtils.fpEquals(emag, 1.0d)){
			p = hmag*hmag / mu;
			a = Double.POSITIVE_INFINITY;
		}
		else{
			a = -mu/2*energy;
			p = a*(1-emag*emag);
		}
		double cosi = h.get(2)/hmag;
		double i = acos(cosi);
		if (MathUtils.fpGreaterThan(emag, 0.0d)){
			// non-circular orbits go here
			EllipticOrbitalElements elem = new EllipticOrbitalElements();
			elem.setInclination(i);
			elem.setEccentricity(emag);
			if (MathUtils.fpEquals(i, 0.0d)){
				// equatorial orbits go here
				double coswtrue = e.get(0)/emag;
				double wtrue = acos(coswtrue);
				if (e.get(1) < 0.0d){
					wtrue = 2 * PI - wtrue;
				}
				elem.setTrueLongitudeOfPeriapsis(wtrue);
			}
			else{
				double cosomega = n.get(0)/nmag;
				double node = acos(cosomega);
				if (n.get(1) < 0.0d){
					node = 2 * PI - node;
				}
				elem.setLongitudeOfAscendingNode(node);
				
				double cosw = (n.dot(e))/(nmag*emag);
				double perigee = acos(cosw);
				if (n.get(2) < 0.0d){
					perigee = 2 * PI - perigee;
				}
				elem.setArgumentOfPerigee(perigee);
				
				double cosnu = (e.dot(r))/(emag*rmag);
				double nu = acos(cosnu);
				if (rdotv < 0.0d){
					nu = 2 * PI - nu;
				}
				elem.setTrueAnomaly(nu);
			}
		}
		
		
		
		
		return null;
		
		
		
	}

}
