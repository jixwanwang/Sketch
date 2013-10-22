package sketch.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

public class Compound extends SketchShape {
	private List<SketchShape> shapes;
	
	public Compound(SketchShape... shapes) {
		float _minX = Float.MAX_VALUE;
		float _minY = Float.MAX_VALUE;
		float _maxX = -Float.MAX_VALUE;
		float _maxY = -Float.MAX_VALUE;
		this.shapes = new ArrayList<SketchShape>();
		for (SketchShape s : shapes){
			this.shapes.add(s);
			if (s.location.x < _minX){
				_minX = s.location.x;
			}
			if (s.location.y < _minY){
				_minY = s.location.y;
			}
			if (s.location.x + s.size.x > _maxX){
				_maxX = s.location.x + s.size.x;
			}
			if (s.location.y + s.size.y > _maxY){
				_maxY = s.location.y + s.size.y;
			}
		}
		this.location = new Vec2f(_minX, _minY);
		this.size = new Vec2f(_maxX - _minX, _maxY - _minY);
	}

	@Override
	public void setLocation(Vec2f loc){
		Vec2f diff = loc.minus(location);
		for (SketchShape s : shapes){
			s.setLocation(s.location.plus(diff));
		}
		location = loc;
	}
	
	@Override
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
		
		//scale factor
		Vec2f ratio = new Vec2f((float)size.x/this.size.x, (float)size.y/this.size.y);
		for (SketchShape s : shapes){
			Vec2f dist = s.location.minus(this.location);
			Vec2f shapediff = dist.pmult(ratio.minus(1, 1));
			s.setLocation(s.location.plus(shapediff));
			s.setSize(s.size.pmult(ratio));
		}
		this.size = size;
		this.setLocation(location.minus(offset));
	}
	
	@Override
	public ShapeState getState() {
		return new ShapeState(ShapeType.RECTANGLE, location, size, rotation, Color.black, Color.black, 0);
	}

	@Override
	protected void drawSelf(Graphics2D g) {
		for (SketchShape s : shapes){
			s.drawSelf(g);
		}
	}

	@Override
	protected Shape getShape() {
		return null;
	}
	
	public boolean contains(Point point){
		Vec2f p = transformToShape(point);
		for (SketchShape s : shapes){
			if (s.getShape().contains(p.x, p.y)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Overrides of the color and border settings, you can't set this stuff for all 
	 * subshapes, but you can ungroup, set all, then regroup.
	 */
	public void setColor(Color c){
		//do nothing
	}
	
	public void setBorderColor(Color c){
		//do nothing
	}
	
	public void setBorderWidth(int w){
		//do nothing
	}
	
	public List<SketchShape> getChildren(){
		return shapes;
	}
	
	@Override
	public String toString(){
		StringBuilder string = new StringBuilder();
		for (SketchShape s : shapes){
			string.append(s.toString());
		}
		return string.toString();
	}
}
