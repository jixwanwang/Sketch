package sketch.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * This is the superclass of all shapes in this program. Subclasses must be able to draw themselves
 * and get their own shapes and return the correct state.
 * 
 * @author Jixuan Wang
 *
 */
public abstract class SketchShape{
	private final double CORNER_RADIUS = 5;
	private final int ROTATE_CIRCLE_DISTANCE = 20;
	protected Vec2f location;
	protected Vec2f size;
	protected double rotation;
	protected Color color;
	protected Color borderColor;
	protected int borderWidth;
	private boolean selected;
	
	public SketchShape() {
		size = new Vec2f();
		selected = false;
		location = new Vec2f();
		rotation = 0;
		borderWidth = 0;
		color = Color.red;
		borderColor = Color.red;
	}

	private int invAndClampColor(int val){
		return Math.min(200,  255 - val);
	}
	
	private Color invertColor(Color c){
		return new Color(invAndClampColor(c.getRed()), invAndClampColor(c.getGreen()), invAndClampColor(c.getBlue()));
	}
	
	public void draw(Graphics2D g){
		AffineTransform _oldTransform = g.getTransform();
		Vec2f _center = getCenter();
		g.translate(_center.x, _center.y);
		g.rotate(-rotation);
		g.translate(-_center.x, -_center.y);
		
		drawSelf(g);
		
		if (selected){
			g.setStroke(new BasicStroke(2));
			Color border;
			//if the selection rect wouldn't cover the border, use inverse of the border's color
			if (borderWidth > 2)
				border = invertColor(borderColor);
			//otherwise use the shape's color
			else
				border = invertColor(color);
			g.setColor(border);
			
			Shape _misc = new Rectangle2D.Double(location.x, location.y, size.x, size.y);
			g.draw(_misc);
			for (int i = 0; i < 3; i++){
				for (int j = 0; j < 3; j++){
					_misc = new Ellipse2D.Double(location.x + (i*size.x)/2 - CORNER_RADIUS, location.y + (j*size.y)/2 - CORNER_RADIUS, CORNER_RADIUS*2, CORNER_RADIUS*2);
					g.fill(_misc);
				}
			}
			g.setColor(Color.GREEN);
			_misc = new Ellipse2D.Double(location.x + size.x/2 - CORNER_RADIUS, location.y - ROTATE_CIRCLE_DISTANCE - CORNER_RADIUS, CORNER_RADIUS*2, CORNER_RADIUS*2);
			g.fill(_misc);
		}
		
		g.setTransform(_oldTransform);
	}
	
	protected abstract void drawSelf(Graphics2D g);
	protected abstract Shape getShape();
	public abstract ShapeState getState();
	
	public void setLocation(Vec2f loc){
		location = loc;
	}
	
	public void setRotation(double angle){
		rotation = angle;
	}
	
	public void setSize(Vec2f size){
		resize(size, new Vec2i(1,1));
	}
	
	public void resize(Vec2f size, Vec2i scaleVector){
		Vec2f offset = new Vec2f();
		Vec2f diff = size.minus(this.size);
		
		//offset only when we're resizing on the top or left
		if (scaleVector.x == -1){
			offset = offset.plus(diff.x, 0);
		}
		if (scaleVector.y == -1){
			offset = offset.plus(0, diff.y);
		}
		this.size = size;
		this.setLocation(location.minus(offset));
	}
	
	public Vec2f getCenter(){
		return location.plus(size.sdiv(2));
	}
	
	protected Vec2f transformToShape(Point point){
		Vec2f _center = getCenter();
		//transform to match shape
		Vec2f p = new Vec2f(new Vec2i(point)).minus(_center);
		p = new Vec2f(p.dot((float)Math.cos(rotation), -(float)Math.sin(rotation)), p.dot((float)Math.sin(rotation), (float)Math.cos(rotation)));
		return p.plus(_center);
	}
	
	public boolean contains(Point point){
		Vec2f p = transformToShape(point);
		
		return getShape().contains(p.x, p.y);
	}
	
	public Vec2i clickedToScale(Point point){
		Vec2f p = transformToShape(point);
		
		//check all the scaling circle things
		if (withinCircle(p, location))
			return new Vec2i(-1,-1);
		if (withinCircle(p, location.plus(size.x,0)))
			return new Vec2i(1,-1);
		if (withinCircle(p, location.plus(size)))
			return new Vec2i(1,1);
		if (withinCircle(p, location.plus(0,size.y)))
			return new Vec2i(-1,1);;
		if (withinCircle(p, location.plus(size.x/2, 0)))
			return new Vec2i(0,-1);
		if (withinCircle(p, location.plus(size.x/2, size.y)))
			return new Vec2i(0,1);
		if (withinCircle(p, location.plus(0, size.y/2)))
			return new Vec2i(-1,0);
		if (withinCircle(p, location.plus(size.x, size.y/2)))
			return new Vec2i(1,0);
		return new Vec2i();
	}
	
	public boolean clickedToRotate(Point point){
		Vec2f p = transformToShape(point);
		
		return withinCircle(p, getCenter().plus(0, -size.y/2 - ROTATE_CIRCLE_DISTANCE));
	}
	
	protected boolean withinCircle(Vec2f p, Vec2f center){
		return p.minus(center).mag2() < CORNER_RADIUS*CORNER_RADIUS;
	}
	
	public void select(){
		selected = true;
	}
	
	public void unselect(){
		selected = false;
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	public void setBorderColor(Color c){
		borderColor = c;
	}
	
	public void setBorderWidth(int w){
		borderWidth = w;
	}
	
	public abstract String toString();
}
