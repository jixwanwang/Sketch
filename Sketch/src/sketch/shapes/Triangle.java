package sketch.shapes;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Path2D;

public class Triangle extends SketchShape {

	@Override
	protected Shape getShape(){
		Path2D.Float _path = new Path2D.Float();
		Vec2f v1 = location.plus(size.x/2, 0);
		Vec2f v2 = location.plus(0, size.y);
		Vec2f v3 = location.plus(size);
		_path.moveTo(v1.x, v1.y);
		_path.lineTo(v2.x, v2.y);
		_path.lineTo(v3.x, v3.y);
		_path.lineTo(v1.x, v1.y);
		return _path;
	}
	
	@Override
	protected void drawSelf(Graphics2D g) {
		Shape s = getShape();
		g.setColor(color);
		g.fill(s);
		
		g.setStroke(new BasicStroke(borderWidth));
		g.setColor(borderColor);
		g.draw(s);
	}
	
	@Override
	public ShapeState getState() {
		return new ShapeState(ShapeType.TRIANGLE, location, size, rotation, color, borderColor, borderWidth);
	}

	@Override
	public String toString() {
		return getState().toString();
	}
}
