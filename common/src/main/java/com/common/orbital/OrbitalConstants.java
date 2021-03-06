package com.common.orbital;

public class OrbitalConstants {
	public static enum UNITS{english,metric,canonical};
	
	private static final double MU_ENGLISH = 1.407646882e16;  // gravitational parameter, ft^3/sec^2
	private static final double MU_METRIC = 3.986012e5;  // gravitational parameter, km^3/sec^2
	private static final double MU_CANONICAL = 1.0d;  // gravitational parameter, DU^3/TU^2
	
	public static double getMU(UNITS units){
		if (units.equals(UNITS.english))
			return MU_ENGLISH;
		else if (units.equals(UNITS.metric))
			return MU_METRIC;
		else
			return MU_CANONICAL;
	}

}
