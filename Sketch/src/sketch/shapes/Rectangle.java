package sketch.shapes;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class Rectangle extends SingleSketchShape{

	@Override
	protected Shape getShape() {
		return new Rectangle2D.Double(location.x, location.y, size.x, size.y);
	}

	public ShapeState getState(){
		return new ShapeState(ShapeType.RECTANGLE, location, size, rotation, color, borderColor, borderWidth);
	}
}
