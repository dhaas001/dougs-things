package com.math.test;

import com.math.MathUtils;
import com.math.linear.Vector;
import com.math.orbital.EllipticOrbitalElements;
import com.math.orbital.OrbitalCalculations;
import com.math.orbital.OrbitalConstants;
import com.math.orbital.OrbitalElements;
import com.math.orbital.StateVector;

import static java.lang.Math.*;

import junit.framework.TestCase;

public class MathTest extends TestCase{
	
	public MathTest(String name){
		super(name);
	}

	public void testVector() {
		Vector v1 = new Vector(3, 1.0d);
		
		assertEquals(1.0d, v1.get(0));
		assertEquals(1.0d, v1.get(0));
		assertEquals(1.0d, v1.get(0));
		
		v1.set(0, 1.0d);
		v1.set(1, 2.0d);
		v1.set(2, 3.0d);
		Vector v2 = new Vector(3);
		v2.set(0, 4.0d);
		v2.set(1, 5.0d);
		v2.set(2, 6.0d);
		
		assertEquals(32.0d, v1.dot(v2));
		
		Vector cross = v1.cross(v2);
		assertEquals(-3.0d, cross.get(0));
		assertEquals(6.0d, cross.get(1));
		assertEquals(-3.0d, cross.get(2));
		
		Vector m = v1.multiply(2.0d);
		assertEquals(2.0d, m.get(0));
		assertEquals(4.0d, m.get(1));
		assertEquals(6.0d, m.get(2));
		
		Vector d = m.divide(2.0d);
		assertEquals(1.0d, d.get(0));
		assertEquals(2.0d, d.get(1));
		assertEquals(3.0d, d.get(2));
		
		Vector a = v1.add(v2);
		assertEquals(5.0d, a.get(0));
		assertEquals(7.0d, a.get(1));
		assertEquals(9.0d, a.get(2));
		
		Vector s = v2.subtract(v1);
		assertEquals(3.0d, s.get(0));
		assertEquals(3.0d, s.get(1));
		assertEquals(3.0d, s.get(2));
		
		assertEquals(3, v1.getSize());
		
		assertFalse(v1.equals(v2));
		
		v2.set(0, 1.0d);
		v2.set(1, 2.0d);
		v2.set(2, 3.0d);
		assertTrue(v1.equals(v2));
		
		Vector v3 = new Vector(3);
		v3.set(0, 1.0d);
		v3.set(1, 2.0d);
		v3.set(2, 3.0d);
		
		assertTrue(MathUtils.fpEquals(v3.magnitude(), 3.741657387));
	}
	
	public void testOrbit() {
		EllipticOrbitalElements elem = new EllipticOrbitalElements();
		elem.setSemiMajorAxis(1.0d);
		elem.setEccentricity(0.1d);
		elem.setInclination(0.2d);
		elem.setLongitudeOfAscendingNode(Math.PI/2);
		elem.setArgumentOfPerigee(Math.PI/6);
		elem.setMeanAnomaly(0.3d);
		assertTrue(MathUtils.fpEquals(elem.getSemiMajorAxis(), 1.0));
		assertTrue(MathUtils.fpEquals(elem.getEccentricity(), 0.1));
		assertTrue(MathUtils.fpEquals(elem.getInclination(), 0.2));
		assertTrue(MathUtils.fpEquals(elem.getLongitudeOfAscendingNode(), Math.PI/2));
		assertTrue(MathUtils.fpEquals(elem.getArgumentOfPerigee(), Math.PI/6));
		assertTrue(MathUtils.fpEquals(elem.getMeanAnomaly(), 0.3));
		assertTrue(MathUtils.fpEquals(elem.getPeriod(OrbitalConstants.UNITS.canonical), 2*Math.PI));
		elem.setSemiMajorAxis(22176000.0d);
		double ans = 2 * PI*104429889724.0d/118644295.354;
		assertTrue(MathUtils.fpEquals(elem.getPeriod(OrbitalConstants.UNITS.english), ans));
		elem.setSemiMajorAxis(6800.0d);
		ans = 2 * PI*560742.365084d/631.348715054;
		assertTrue(MathUtils.fpEquals(elem.getPeriod(OrbitalConstants.UNITS.metric), ans));
		
		ans = elem.convertTrueAnomalyToConicAnomaly(0.0);
		assertTrue(MathUtils.fpEquals(ans, 0.0d));
		
		ans = elem.convertTrueAnomalyToConicAnomaly(PI);
		assertTrue(MathUtils.fpEquals(ans, PI));
		
		double[] angles = {0,PI/6, PI/4, PI/3, PI/2, 2*PI/3, 3*PI/4, 5*PI/6, PI, 7*PI/6, 5*PI/4, 4*PI/3, 3*PI/2, 5*PI/3, 7*PI/4, 11*PI/6, 2*PI};
		
		for (double angle:angles){
			ans = elem.convertTrueAnomalyToConicAnomaly(angle);
//			System.out.println("for true anomaly " + angle*180/PI + " eccentric anomaly is: " + ans*180/PI);
			double ans2 = elem.convertConicAnomalyToTrueAnomaly(ans);
//			System.out.println("for eccentric anomaly " + ans*180/PI + " true anomaly is: " + ans2*180/PI);
			assertTrue(MathUtils.fpEquals(ans2, angle));
		}

		double savedMeanAnomaly = elem.getMeanAnomaly();
		for (double angle:angles){
			elem.setMeanAnomaly(angle);
			ans = elem.convertMeanAnomalyToConicAnomaly();
			double ans2 = elem.convertConicAnomalyToMeanAnomaly(ans);
			assertTrue(MathUtils.fpEquals(ans2, angle));
//			System.out.println("for mean anomaly " + angle*180/PI + " eccentric anomaly is: " + ans*180/PI);
		}
		elem.setMeanAnomaly(savedMeanAnomaly);
		for (double angle:angles){
			ans = elem.getFlightPathAngle(angle);
			System.out.println("for eccentric anomaly " + angle*180/PI + " flight path angle is: " + ans*180/PI);
		}
		
		Vector rInitial = new Vector(3,new double[]{1131.340d, -2282.343d, 6672.423d});
		Vector vInitial = new Vector(3,new double[]{-5.64305d, 4.30333d, 2.42879d});
		StateVector initial = new StateVector(rInitial, vInitial);
		StateVector response = OrbitalCalculations.solveKepler(initial, 40.0d*60.0d, OrbitalConstants.UNITS.metric);
		Vector rFinal = response.getPosition();
		Vector vFinal = response.getVelocity();
		assertTrue(MathUtils.fpEquals(rFinal.get(0), -4219.712620164972d));
		assertTrue(MathUtils.fpEquals(rFinal.get(1), 4363.0008289291d));
		assertTrue(MathUtils.fpEquals(rFinal.get(2), -3958.7952055898522d));
		
		assertTrue(MathUtils.fpEquals(vFinal.get(0), 3.689914368697828d));
		assertTrue(MathUtils.fpEquals(vFinal.get(1), -1.9167783876515814d));
		assertTrue(MathUtils.fpEquals(vFinal.get(2), -6.112497894613279d));
		
		// now do kepler in english
//		rInitial.set(0, rInitial.get(0) * );
		
		
	}

}
