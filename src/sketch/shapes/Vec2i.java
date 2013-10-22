package sketch.shapes;

import java.io.Serializable;

/**
 * A class representing a two-dimensional vector of {@code int}s. Useful when working with positions
 * and sizes in a discrete space, such as tiles on a grid.
 * 
 * {@code Vec2i} instances, like {@link String}s, are immutable, and should be treated in a similar
 * way. Specifically, note that all methods will return a new instance rather than modifying the
 * same instance, so the return value must be used or saved.
 * 
 * For performance, this class and all of its methods have been marked {@code final}.
 * 
 * @author zdavis
 */
public final class Vec2i implements Serializable {
	private static final long serialVersionUID = 5659632794862666943L;
	
	/**
	 * Since {@link Vec2i} instances are immutable, their x and y fields may be accessed without getters.
	 */
	public final int x, y;
	
	/**
	 * Creates a new vector with zero components.
	 */
	public Vec2i(){
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * Creates a new vector from an x and y component.
	 * @param x      the x-component of the vector
	 * @param y      the y-component of the vector
	 */
	public Vec2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2i(java.awt.Point p){
		this.x = p.x;
		this.y = p.y;
	}
	
	/*
	 * Vector ops
	 */
	
	/**
	 * Multiplies the vector by a scalar.
	 * @param s      the scalar by which to multiply this vector
	 * @return       a new {@link Vec2i} instance where each component has been multiplied by {@code s}
	 */
	public final Vec2i smult(int s) {
		return new Vec2i(x*s, y*s);
	}
	
	/**
	 * Divides the vector by a scalar. Note that this is integer division.
	 * @param s		the scalar by which to divide this vector
	 * @return		a new {@link Vec2i} instance where each component has been divided by
	 * 				{@code s}
	 */
	public final Vec2i sdiv(int s) {
		return new Vec2i(x/s, y/s);
	}
	
	/**
	 * Multiplies the vector piecewise by another vector. NOT A DOT PRODUCT.
	 * @param v      the vector by which to multiply this vector
	 * @return       a new {@link Vec2i} instance where each component has been multiplied by the corresponding component in {@code v}
	 */
	public final Vec2i pmult(Vec2i v) {
		return new Vec2i(x*v.x, y*v.y);
	}
	
	/**
	 * Primitive version of {@link #pmult(Vec2i)}.
	 */
	public final Vec2i pmult(int x, int y) {
		return new Vec2i(this.x * x, this.y * y);
	}
	
	/**
	 * Divides the vector piecewise by another vector.
	 * @param v      the vector by which to divide this vector
	 * @return       a new {@link Vec2i} instance where each component has been divided by the corresponding component in {@code v}
	 */
	public final Vec2i pdiv(Vec2i v) {
		return new Vec2i(x/v.x, y/v.y);
	}
	
	/**
	 * Primitive version of {@link #pdiv(Vec2i)}.
	 */
	public final Vec2i pdiv(int x, int y) {
		return new Vec2i(this.x/x, this.y/y);
	}
	
	/**
	 * Adds another vector to this vector.
	 * @param v      the vector to add to this vector
	 * @return       a new {@link Vec2i} instance where each component has added the corresponding component in {@code v}
	 */
	public final Vec2i plus(Vec2i v) {
		return new Vec2i(x + v.x, y + v.y);
	}
	
	/**
	 * Primitive version of {@link #plus(Vec2i)}.
	 */
	public final Vec2i plus(int x, int y) {
		return new Vec2i(this.x + x, this.y + y);
	}
	
	/**
	 * Subtracts another vector from this vector.
	 * @param v      the vector to subtract from this vector
	 * @return       a new {@link Vec2i} instance where each component has added the corresponding component in {@code v}
	 */
	public final Vec2i minus(Vec2i v) {
		return new Vec2i(x - v.x, y - v.y);
	}
	
	/**
	 * Primitive version of {@link #minus(Vec2i)}.
	 */
	public final Vec2i minus(int x, int y) {
		return new Vec2i(this.x - x, this.y - y);
	}
	
	/*
	 * Object overrides
	 */

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (obj == null || obj.getClass() != Vec2i.class)
			return false;
		Vec2i other = (Vec2i) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public final String toString() {
		return new StringBuilder("(").append(x).append(", ").append(y).append(")").toString();
	}
}
