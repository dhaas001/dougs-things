package com.math.linear;

import com.math.MathUtils;

public class Vector {
	private int dim;
	private double v[];
	
	public Vector(int dimensions, double d){
		dim = dimensions;
		v = new double[dim];
		for (int i = 0; i < dim; ++i){
			v[i] = d;
		}
	}
	
	public Vector(int dimensions){
		dim = dimensions;
		v = new double[dim];
	}
	
	public Vector(int dimensions, double[] d){
		dim = dimensions;
		v = new double[dim];
		for (int i = 0; i < dim; ++i){
			v[i] = d[i];
		}
	}
	
	
	public int getSize(){
		return dim;
	}
	
	public void set(int index, double value){
		v[index] = value;
	}
	
	public Vector add(final Vector v2){
		if (dim != v2.getSize()){
			throw new IllegalArgumentException("dimensions between vectors not equal: " + dim + " and " + v2.getSize());		}
		Vector v1 = new Vector(v2.getSize());
		for (int i = 0; i < dim; ++i){
			v1.set(i, v[i] + v2.get(i));
		}
		return v1;
	}
	
	public Vector subtract(final Vector v2){
		if (dim != v2.getSize()){
			throw new IllegalArgumentException("dimensions between vectors not equal: " + dim + " and " + v2.getSize());		}
		Vector v1 = new Vector(v2.getSize());
		for (int i = 0; i < dim; ++i){
			v1.set(i, v[i] - v2.get(i));
		}
		return v1;
	}
	
	public double magnitude(){
		double mag = 0.0d;
		for (int i = 0; i < dim; ++i){
			mag += v[i] * v[i];
		}
		return Math.pow(mag, 0.5d);
	}
	
	public Vector cross(final Vector v2){
		if (dim != v2.getSize() || dim != 3){
			throw new IllegalArgumentException("dimensions between vectors not equal or not 3: " + dim + " and " + v2.getSize());		}
		Vector v1 = new Vector(v2.getSize());
			v1.set(0, (v[1] * v2.get(2)) - (v[2]*v2.get(1)));
			v1.set(1, (v[2] * v2.get(0)) - (v[0]*v2.get(2)));
			v1.set(2, (v[0] * v2.get(1)) - (v[1]*v2.get(0)));
		return v1;
	}
	
	public double dot(final Vector v2){
		if (dim != v2.getSize()){
			throw new IllegalArgumentException("dimensions between vectors not equal: " + dim + " and " + v2.getSize());		}
		double dot = 0.0;
		for (int i = 0; i < dim; ++i){
			dot += v[i] * v2.get(i);
		}
		return dot;
	}
	
	public Vector multiply(final double d){
		Vector v1 = new Vector(dim);
		for (int i = 0; i < dim; ++i){
			v1.set(i, v[i] * d);
		}
		return v1;
	}
	
	public Vector divide(final double d){
		Vector v1 = new Vector(dim);
		for (int i = 0; i < dim; ++i){
			v1.set(i, v[i] / d);
		}
		return v1;
	}
	
	public double get(int i){
		return v[i];
	}
	
    @Override
    public boolean equals(Object o) {
 
        if (o == this) {
            return true;
        }
 
        if (!(o instanceof Vector)) {
            return false;
        }
         
        Vector c = (Vector) o;
        
        if (dim != c.getSize()){
        	return false;
        }
        
        boolean returnValue = true;
        for (int i = 0; i < dim; ++i){
        	returnValue = returnValue && (MathUtils.fpEquals(v[i],c.get(i)));
        }
        return returnValue;
    }

}
