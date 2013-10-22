
package sketch.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public interface SketchShape {
	
	public void draw(Graphics2D g);

	/**
	 * Sets the location of the top right corner of this shape.
	 * @param loc
	 */
	public void setLocation(Vec2i loc);

	/**
	 * Sets the rotation of the shape, rotates mathematically, which means CCW is increasing angle.
	 * @param angle
	 */
	public void setRotation(double angle);
	
	/**
	 * Sets the size of the shape, keeping the top-left the same.
	 * @param size
	 */
	public void setSize(Vec2i size);
	
	/**
	 * Resizes the shape but based on the scaleVector passed in,
	 * keeps different corners/edges the same.
	 * @param size
	 * @param scaleVector
	 */
	public void resize(Vec2i size, Vec2i scaleVector);
	
	/**
	 * @return abstract representation of the shape
	 */
	public ShapeState getState();
	
	/**
	 * @return center of the shape
	 */
	public Vec2i getCenter();
	
	/**
	 * @param point
	 * @return whether the shape contains the point
	 */
	public boolean contains(Point point);
	
	/**
	 * @param point
	 * @return the scaleVector associated with the scaling knob that was clicked on, or (0,0) if no knob was clicked on
	 */
	public Vec2i clickedToScale(Point point);
	
	/**
	 * @param point
	 * @return whether or not the click was on the rotate knob
	 */
	public boolean clickedToRotate(Point point);
	
	/**
	 * Select methods
	 */
	public void select();
	public void unselect();
	public boolean isSelected();
	
	/**
	 * Setters for the color, border color, and border width
	 */
	public void setColor(Color c);
	public void setBorderColor(Color color);
	public void setBorderWidth(int borderWidth);
}
