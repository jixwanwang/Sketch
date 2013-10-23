package sketch.shapes;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Ellipse extends SketchShape{
	
	public Ellipse(){
		super();
	}
	
	public Ellipse(ShapeState s){
		super(s);
	}
	
	@Override
	protected Shape getShape(){
		return new Ellipse2D.Float(location.x,location.y,size.x,size.y);
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
		return "Ellipse";
	}
	
	@Override
	public SketchShape copy() {
		return new Ellipse(getState());
	}
}
