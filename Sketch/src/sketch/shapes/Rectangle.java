package sketch.shapes;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class Rectangle extends SketchShape{
	
	public Rectangle(){
		super();
	}
	
	public Rectangle(ShapeState state) {
		super(state);
	}

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
	public String getName(){
		return "Rectangle";
	}
	
	@Override
	public SketchShape copy() {
		return new Rectangle(getState());
	}
}
