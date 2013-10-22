package sketch.shapes;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Ellipse extends SingleSketchShape{
	
	@Override
	protected Shape getShape(){
		return new Ellipse2D.Double(location.x,location.y,size.x,size.y);
	}
	
	public ShapeState getState(){
		return new ShapeState(ShapeType.ELLIPSE, location, size, rotation, color, borderColor, borderWidth);
	}
}
