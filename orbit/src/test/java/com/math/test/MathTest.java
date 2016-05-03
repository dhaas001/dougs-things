package com.math.test;

import com.math.MathUtils;
import com.math.linear.Vector;
import com.math.orbital.OrbitalConstants;
import com.math.orbital.OrbitalElements;

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
		OrbitalElements elem = new OrbitalElements(1.0, 0.1, 0.2, Math.PI/2, Math.PI/6, 0.3);
		
		assertTrue(MathUtils.fpEquals(elem.getSemiMajorAxis(), 1.0));
		assertTrue(MathUtils.fpEquals(elem.getEccentricity(), 0.1));
		assertTrue(MathUtils.fpEquals(elem.getInclination(), 0.2));
		assertTrue(MathUtils.fpEquals(elem.getLongitudeOfAscendingNode(), Math.PI/2));
		assertTrue(MathUtils.fpEquals(elem.getArgumentOfPerigee(), Math.PI/6));
		assertTrue(MathUtils.fpEquals(elem.getMeanAnomaly(), 0.3));
		assertTrue(MathUtils.fpEquals(elem.getPeriod(OrbitalConstants.UNITS.canonical), 2*Math.PI));
		elem.setSemiMajorAxis(22176000.0d);
		double ans = 2 * Math.PI*104429889724.0d/118644295.354;
		assertTrue(MathUtils.fpEquals(elem.getPeriod(OrbitalConstants.UNITS.english), ans));
		elem.setSemiMajorAxis(6800.0d);
		ans = 2 * Math.PI*560742.365084d/631.348715054;
		assertTrue(MathUtils.fpEquals(elem.getPeriod(OrbitalConstants.UNITS.metric), ans));
		
		ans = elem.convertTrueAnomalyToEccentricAnomaly(0.0);
		assertTrue(MathUtils.fpEquals(ans, 0.0d));
		
		ans = elem.convertTrueAnomalyToEccentricAnomaly(Math.PI);
		assertTrue(MathUtils.fpEquals(ans, Math.PI));
		
		ans = elem.convertTrueAnomalyToEccentricAnomaly(Math.PI/2.0d);
		System.out.println("eccentric anomaly is: " + ans);
		
	}

}
