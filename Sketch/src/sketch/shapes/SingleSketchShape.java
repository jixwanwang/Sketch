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
 * This is so Java, this class is a concrete but still abstract implementation of the SketchShape interface.
 * The building block shapes will extend from this.
 * 
 * @author Jixuan Wang
 *
 */
public abstract class SingleSketchShape implements SketchShape{
	private final double CORNER_RADIUS = 5;
	private final int ROTATE_CIRCLE_DISTANCE = 20;
	protected Vec2i location;
	protected Vec2i size;
	protected double rotation;
	protected Color color;
	protected Color borderColor;
	protected int borderWidth;
	private boolean selected;
	
	public SingleSketchShape() {
		size = new Vec2i();
		selected = false;
		location = new Vec2i();
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
		Vec2i _center = getCenter();
		g.translate(_center.x, _center.y);
		g.rotate(-rotation);
		g.translate(-_center.x, -_center.y);
		
		Shape s = getShape();
		g.setColor(color);
		g.fill(s);
		
		g.setStroke(new BasicStroke(borderWidth));
		g.setColor(borderColor);
		g.draw(s);
		
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
	
	protected abstract Shape getShape();
	
	/**
	 * Sets the location of the top right corner of this shape.
	 * @param loc
	 */
	public void setLocation(Vec2i loc){
		location = loc;
	}
	
	public void setRotation(double angle){
		rotation = angle;
	}
	
	public void setSize(Vec2i size){
		this.size = size;
	}
	
	public void resize(Vec2i size, Vec2i scaleVector){
		Vec2i offset = new Vec2i();
		Vec2i diff = size.minus(this.size);
		
		//offset only when we're resizing on the top or left
		if (scaleVector.x == -1){
			offset = offset.plus(diff.x, 0);
		}
		if (scaleVector.y == -1){
			offset = offset.plus(0, diff.y);
		}
		this.size = size;
		location = location.minus(offset);
	}
	
	public Vec2i getCenter(){
		return location.plus(size.sdiv(2));
	}
	
	private Vec2f transformToShape(Point point){
		Vec2i _center = getCenter();
		//transform to match shape
		Vec2f p = new Vec2f(new Vec2i(point).minus(_center));
		p = new Vec2f(p.dot((float)Math.cos(rotation), -(float)Math.sin(rotation)), p.dot((float)Math.sin(rotation), (float)Math.cos(rotation)));
		return p.plus(new Vec2f(_center));
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
	
	private boolean withinCircle(Vec2f p, Vec2i center){
		return p.minus(new Vec2f(center)).mag2() < CORNER_RADIUS*CORNER_RADIUS;
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
}
