package sketch.shapes;

import java.awt.Color;

/**
 * Represents the state of a shape, used to restore when doing an action but before applying the action.
 * 
 * @author Jixuan Wang
 *
 */
public class ShapeState {
	public final Vec2f location;
	public final Vec2f size;
	public final double rotation;
	public final Color color;
	public final Color borderColor;
	public final int borderWidth;
	
	public ShapeState(Vec2f l, Vec2f s, double r, Color c, Color bc, int bw) {
		this.location = l;
		this.size = s;
		this.rotation = r;
		this.color = c;
		this.borderColor = bc;
		this.borderWidth = bw;
	}
	
	@Override
	public String toString(){
		return Vec2fString(location) + Vec2fString(size) + rotation + "/" + colorString(color) + colorString(borderColor) + borderWidth + "\n";
	}
	
	private String Vec2fString(Vec2f v){
		return v.x + "/" + v.y + "/";
	}
	
	private String colorString(Color c){
		return c.getRed() + "/" + c.getGreen() + "/" + c.getBlue() + "/";
	}
	
	public static ShapeState read(String line){
		String[] parts = line.split("/");
		if (parts.length != 13)
			throw new IllegalArgumentException();
		try{
			//it's ugly and I know it
			Vec2f location = new Vec2f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
			Vec2f size = new Vec2f(Float.parseFloat(parts[3]), Float.parseFloat(parts[4]));
			double rotation = Double.parseDouble(parts[5]);
			Color color = new Color(Integer.parseInt(parts[6]), Integer.parseInt(parts[7]), Integer.parseInt(parts[8]));
			Color bcolor = new Color(Integer.parseInt(parts[9]), Integer.parseInt(parts[10]), Integer.parseInt(parts[11]));
			int bwidth = Integer.parseInt(parts[12]);
			return new ShapeState(location, size, rotation, color, bcolor, bwidth);
		}
		catch (Exception e){
			throw new IllegalArgumentException();
		}
	}
}
