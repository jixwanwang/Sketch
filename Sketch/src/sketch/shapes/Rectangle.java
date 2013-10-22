package sketch.shapes;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class Rectangle extends SketchShape{

	@Override
	protected Shape getShape(){
		return new Rectangle2D.Float(location.x, location.y, size.x, size.y);
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
	public ShapeState getState(){
		return new ShapeState(ShapeType.RECTANGLE, location, size, rotation, color, borderColor, borderWidth);
	}

	@Override
	public String toString() {
		return getState().toString();
	}
}
